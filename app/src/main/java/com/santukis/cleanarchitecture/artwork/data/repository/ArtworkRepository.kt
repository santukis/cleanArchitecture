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
        localDataSource.loadArtworkDetail(artworkId)
            .flatMapConcat { artwork ->
                when(artwork.shouldBeUpdated) {
                    true -> loadArtworkDetailFromRemote(artworkId)
                    false -> flowOf(artwork)
                }
            }



    private suspend fun loadArtworkDetailFromRemote(artworkId: String): Flow<Artwork> =
        remoteDataSource.loadArtworkDetail(artworkId)
            .map { artwork -> localDataSource.saveArtwork(artwork) }
}