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
        localDataSource.loadArtworks()
            .onStart { refreshArtworks() }
            .catch { emit(emptyList()) }

    suspend fun refreshArtwork(artworkId: String) {
        remoteDataSource.loadArtworkDetail(artworkId)
            .collect { artwork -> localDataSource.saveArtwork(artwork) }
    }

    suspend fun loadArtworkDetail(artworkId: String): Flow<Artwork> =
        localDataSource.loadArtworkDetail(artworkId)
            .onStart { refreshArtwork(artworkId) }
            .catch {  }
}