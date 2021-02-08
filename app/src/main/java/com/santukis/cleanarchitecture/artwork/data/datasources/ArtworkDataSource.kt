package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface ArtworkDataSource {

    suspend fun loadArtworks(): Flow<List<Artwork>> = emptyFlow()

    suspend fun refreshArtworks(lastItem: Int = 0): Flow<List<Artwork>> = emptyFlow()

    suspend fun saveArtworks(artworks: List<Artwork>) {}
}