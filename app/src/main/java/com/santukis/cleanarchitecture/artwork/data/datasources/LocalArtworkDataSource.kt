package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.data.local.*
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.ArtworkCollection
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.*

class LocalArtworkDataSource(private val database: AppDatabase): ArtworkDataSource {

    override suspend fun loadCollections(): Flow<Response<List<ArtworkCollection>>> =
        flow { emit(loadCollectionsFromDatabase()) }

    private fun loadCollectionsFromDatabase(): Response<List<ArtworkCollection>> {
        val items = database.artworkDao().loadCollections()

        return when {
            items.isNullOrEmpty() -> Response.Error(Exception("No Items"))
            else -> Response.Success(items.groupBy { it.collection }.map { ArtworkCollection(it.key, it.value.map { it.toArtwork() }) })
        }
    }

    override suspend fun saveCollections(collections: List<ArtworkCollection>): Response<List<ArtworkCollection>> {
        collections.forEach { collection -> saveArtworks(collection.artworks) }
        return loadCollectionsFromDatabase()
    }

    override suspend fun loadArtworks(collection: Collection, lastItem: Int): Flow<Response<List<Artwork>>> =
        database.artworkDao().loadArtworks(collection)
            .distinctUntilChanged()
            .map { items ->
                when {
                    items.isNullOrEmpty() -> Response.Error(Exception("No Items"))
                    else -> Response.Success(items.map { artworkDb -> artworkDb.toArtwork() })
                }
            }

    override suspend fun saveArtworks(artworks: List<Artwork>): Response<List<Artwork>> {
        val saved = database.artworkDao().saveItems(artworks.map { it.toArtworkDb() }).any { it != -1L }
        artworks.forEach { artwork -> saveArtworkDetail(artwork) }

        return when(saved) {
            true -> Response.Success(artworks)
            false -> super.saveArtworks(artworks)
        }
    }

    override suspend fun loadArtworkDetail(collection: Collection, artworkId: String): Response<Artwork> =
        when(val item = database.artworkDao().loadArtwork(artworkId)) {
            null -> super.loadArtworkDetail(collection, artworkId)
            else -> Response.Success(item.toArtwork())
        }

    override suspend fun saveArtwork(artwork: Artwork): Response<Artwork> {
        database.artworkDao().updateItem(artwork.toArtworkDb())
        saveArtworkDetail(artwork)
        return Response.Success(artwork)
    }

    private fun saveArtworkDetail(artwork: Artwork) {
        database.dimensionsDao().saveItems(artwork.dimensions.map { it.toDimensionDb(artwork.id) })
        database.colorsDao().saveItems(artwork.colors.map { it.toColorDb(artwork.id) })
        database.categoriesDao().saveItems(artwork.categories.map { it.toCategoryDb(artwork.id) })
        database.materialsDao().saveItems(artwork.materials.map { it.toMaterialDb(artwork.id) })
        database.techniquesDao().saveItems(artwork.techniques.map { it.toTechniqueDb(artwork.id) })
    }

    override suspend fun loadFavourites(): Flow<Response<List<Artwork>>> =
        database.artworkDao().loadFavouriteArtworks()
            .distinctUntilChanged()
            .map { items ->
                when {
                    items.isNullOrEmpty() -> Response.Error(Exception("No favourites"))
                    else -> Response.Success(items.map { artworkDb -> artworkDb.toArtwork() })
                }
            }

    override suspend fun toggleFavourite(artworkId: String): Response<Unit> =
        when(database.favouritesDao().toggleFavourite(artworkId) > 0) {
            true -> Response.Success(Unit)
            false -> super.toggleFavourite(artworkId)
        }

    override suspend fun isArtworkFavourite(artworkId: String): Boolean =
        database.favouritesDao().loadFavourite(artworkId) != null

}
