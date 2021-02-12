package com.santukis.cleanarchitecture.artwork.data.repository

import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import kotlinx.coroutines.flow.*

class ArtworkRepository(
    private val localDataSource: ArtworkDataSource,
    private val remoteDataSource: ArtworkDataSource
) {

    suspend fun refreshArtworks(lastItem: Int = 0) {
        remoteDataSource.refreshArtworks(lastItem)
            .collect { artworks -> localDataSource.saveArtworks(artworks) }
    }

    suspend fun loadArtworks(): Flow<List<Artwork>> =
        flow {
            localDataSource.loadArtworks()
                .collect { artworks ->
                    when(artworks.isEmpty()) {
                        true -> refreshArtworks()
                        false -> emit(artworks)
                    }
                }
        }

    private suspend fun refreshArtwork(artworkId: String) {
        remoteDataSource.loadArtworkDetail(artworkId)
            .collect { artwork -> localDataSource.saveArtwork(artwork) }
    }

    suspend fun loadArtworkDetail(artworkId: String): Flow<Artwork> =
        flow {
            localDataSource.loadArtworkDetail(artworkId)
                .collect { artwork ->
                    emit(artwork)
                    artwork.takeIf { it.shouldBeUpdated }?.apply { refreshArtwork(id) }
                }
        }
}