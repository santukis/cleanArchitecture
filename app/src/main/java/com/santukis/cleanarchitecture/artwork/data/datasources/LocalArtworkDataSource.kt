package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.data.mappers.*
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import kotlinx.coroutines.flow.*

class LocalArtworkDataSource(private val database: AppDatabase): ArtworkDataSource {

    override suspend fun loadArtworks(): Flow<List<Artwork>> = try {
            database.artworkDao().loadArtworks()
                .map { items -> items.map { artworkDb -> artworkDb.toArtwork() } }

        } catch (exception: Exception) {
            emptyFlow()
        }

    override suspend fun saveArtworks(artworks: List<Artwork>) {
        database.artworkDao().saveItems(artworks.map { it.toArtworkDb() })
    }

    override suspend fun loadArtworkDetail(artworkId: String): Flow<Artwork> = try {
        database.artworkDao().loadArtwork(artworkId)?.map { it.toArtwork() } ?: emptyFlow()

    } catch (exception: Exception) {
        emptyFlow()
    }

    override suspend fun saveArtwork(artwork: Artwork) {
        database.artworkDao().saveItem(artwork.toArtworkDb().apply { updatedAt = System.currentTimeMillis() })
        database.dimensionsDao().saveItems(artwork.dimensions.map { it.toDimensionDb(artwork.id) })
        database.colorsDao().saveItems(artwork.colors.map { it.toColorDb(artwork.id) })
        database.categoriesDao().saveItems(artwork.categories.map { it.toCategoryDb(artwork.id) })
        database.materialsDao().saveItems(artwork.materials.map { it.toMaterialDb(artwork.id) })
        database.techniquesDao().saveItems(artwork.techniques.map { it.toTechniqueDb(artwork.id) })
    }
}
