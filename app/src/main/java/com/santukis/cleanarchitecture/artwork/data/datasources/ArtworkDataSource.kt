package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.fragments.domain.model.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface ArtworkDataSource {

    suspend fun loadArtworks(lastItem: Int = 0): Flow<Response<List<Artwork>>> = emptyFlow()

    suspend fun saveArtworks(artworks: List<Artwork>): Response<List<Artwork>> = Response.Error(Exception("Unable to save Artworks"))

    suspend fun loadArtworkDetail(artworkId: String): Response<Artwork> = Response.Error(Exception("Unable to load Artwork"))

    suspend fun saveArtwork(artwork: Artwork): Response<Artwork> = Response.Error(Exception("Unable to save Artwork"))

    suspend fun loadQuestion(): Response<Question> = Response.Error(Exception("Unable to load Question"))
}