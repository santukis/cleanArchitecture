package com.santukis.cleanarchitecture.artwork.data.repository

import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import kotlinx.coroutines.flow.*

class ArtworkRepository(
    private val localDataSource: ArtworkDataSource,
    private val remoteDataSource: ArtworkDataSource
) {

    suspend fun loadArtworks(lastItem: Int): Flow<List<Artwork>> =
        when(lastItem) {
            0 -> loadArtworksFromLocal()
            else -> loadArtworksFromRemote(lastItem)
        }

    private suspend fun loadArtworksFromLocal(): Flow<List<Artwork>> =
        localDataSource.loadArtworks()
            .flatMapConcat { artworks ->
                when(artworks.isNullOrEmpty()) {
                    true -> loadArtworksFromRemote(0)
                    false -> flowOf(artworks)
                }
            }

    private suspend fun loadArtworksFromRemote(lastItem: Int = 0): Flow<List<Artwork>> =
        remoteDataSource.loadArtworks(lastItem)
            .map { artworks -> localDataSource.saveArtworks(artworks) }

    suspend fun loadArtworkDetail(artworkId: String): Flow<Artwork> =
        flow {
            localDataSource.loadArtworkDetail(artworkId)
                .collect { artwork ->
                    emit(artwork)
                    artwork.takeIf { it.shouldBeUpdated }?.apply { refreshArtwork(id) }
                }
        }

    private suspend fun refreshArtwork(artworkId: String) {
        remoteDataSource.loadArtworkDetail(artworkId)
            .collect { artwork -> localDataSource.saveArtwork(artwork) }
    }
}