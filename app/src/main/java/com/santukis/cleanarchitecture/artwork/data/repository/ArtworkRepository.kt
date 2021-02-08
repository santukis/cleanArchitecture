package com.santukis.cleanarchitecture.artwork.data.repository

import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import kotlinx.coroutines.flow.*

class ArtworkRepository(
    private val localDataSource: ArtworkDataSource,
    private val remoteDataSource: ArtworkDataSource
) {

    suspend fun checkArtworks(lastItem: Int) {
        loadArtworksFromRemote(lastItem)
    }

    suspend fun loadArtworks(): Flow<List<Artwork>> =
        localDataSource.loadArtworks()
            .onStart { loadArtworksFromRemote() }
            .catch { emit(emptyList()) }

    private suspend fun loadArtworksFromRemote(lastItem: Int = 0) {
        remoteDataSource.loadArtworks(lastItem)
            .collect { artworks -> localDataSource.saveArtworks(artworks) }
    }
}