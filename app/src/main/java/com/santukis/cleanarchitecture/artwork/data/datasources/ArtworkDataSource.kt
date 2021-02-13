package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface ArtworkDataSource {

    suspend fun loadArtworks(lastItem: Int = 0): Flow<List<Artwork>> = emptyFlow()

    suspend fun saveArtworks(artworks: List<Artwork>): List<Artwork> = emptyList()

    suspend fun loadArtworkDetail(artworkId: String): Flow<Artwork> = emptyFlow()

    suspend fun saveArtwork(artwork: Artwork): Artwork = Artwork.EMPTY
}