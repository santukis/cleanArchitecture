package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.data.mappers.*
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.domain.model.Answer
import com.santukis.cleanarchitecture.game.domain.model.Question
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class LocalArtworkDataSource(private val database: AppDatabase): ArtworkDataSource {

    override suspend fun loadArtworks(lastItem: Int): Flow<Response<List<Artwork>>> =
        database.artworkDao().loadArtworks()
            .distinctUntilChanged()
            .map { items ->
                when {
                    items.isNullOrEmpty() -> Response.Error(Exception("No Items"))
                    else -> Response.Success(items.map { artworkDb -> artworkDb.toArtwork() })
                }
            }

    override suspend fun saveArtworks(artworks: List<Artwork>): Response<List<Artwork>> =
        when(database.artworkDao().saveItems(artworks.map { it.toArtworkDb() }).any { it != -1L }) {
            true -> Response.Success(artworks)
            false -> super.saveArtworks(artworks)
        }

    override suspend fun loadArtworkDetail(artworkId: String): Response<Artwork> =
        when(val item = database.artworkDao().loadArtwork(artworkId)) {
            null -> super.loadArtworkDetail(artworkId)
            else -> Response.Success(item.toArtwork())
        }

    override suspend fun saveArtwork(artwork: Artwork): Response<Artwork> {
        database.artworkDao().updateItem(artwork.toArtworkDb().apply { updatedAt = System.currentTimeMillis() })
        database.dimensionsDao().saveItems(artwork.dimensions.map { it.toDimensionDb(artwork.id) })
        database.colorsDao().saveItems(artwork.colors.map { it.toColorDb(artwork.id) })
        database.categoriesDao().saveItems(artwork.categories.map { it.toCategoryDb(artwork.id) })
        database.materialsDao().saveItems(artwork.materials.map { it.toMaterialDb(artwork.id) })
        database.techniquesDao().saveItems(artwork.techniques.map { it.toTechniqueDb(artwork.id) })
        return Response.Success(artwork)
    }

    override suspend fun loadQuestion(): Response<Question> {
        val type = Random.nextInt(0, 3)

        val items = when(type) {
            0 -> database.artworkDao().loadTitleQuestion()
            1 -> database.artworkDao().loadAuthorQuestion()
            else -> database.artworkDao().loadDatingQuestion()
        }

        return when {
            items.isNullOrEmpty() -> super.loadQuestion()
            type == 0 -> Response.Success(Question.TitleQuestion(answers = items.map { Answer(it.artworkDb.image, it.artworkDb.title, it.artworkDb.description) }))
            type == 1 -> Response.Success(Question.AuthorQuestion(answers = items.map { Answer(it.artworkDb.image, it.artworkDb.author, it.artworkDb.description) }))
            type == 2 -> Response.Success(Question.DatingQuestion(answers = items.map { Answer(it.artworkDb.image, it.artworkDb.dating.toString(), it.artworkDb.description) }))
            else -> Response.Error(Exception("Unknown Question"))
        }
    }
}
