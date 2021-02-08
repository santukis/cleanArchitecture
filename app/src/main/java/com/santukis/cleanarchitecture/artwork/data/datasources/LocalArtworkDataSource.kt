package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.data.mappers.toArtwork
import com.santukis.cleanarchitecture.artwork.data.mappers.toArtworkDb
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import kotlinx.coroutines.flow.*

class LocalArtworkDataSource(private val database: AppDatabase): ArtworkDataSource {

    override suspend fun loadArtworks(lastItem: Int): Flow<List<Artwork>> = try {
            database.artworkDao().loadArtworks()
                .map { items -> items.map { artworkDb -> artworkDb.toArtwork() } }

        } catch (exception: Exception) {
            emptyFlow()
        }
    override suspend fun saveArtworks(artworks: List<Artwork>) {
        database.artworkDao().saveItems(artworks.map { it.toArtworkDb() })
    }

}
