package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.data.mappers.*
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.*

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

    override suspend fun loadFavouriteArtworks(): Flow<Response<List<Artwork>>> =
        database.artworkDao().loadFavouriteArtworks()
            .distinctUntilChanged()
            .map { items ->
                when {
                    items.isNullOrEmpty() -> Response.Error(Exception("No favourites"))
                    else -> Response.Success(items.map { artworkDb -> artworkDb.toArtwork() })
                }
            }
}
