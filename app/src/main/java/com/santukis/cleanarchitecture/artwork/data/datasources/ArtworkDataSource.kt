package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ArtworkDataSource {

    suspend fun loadArtworks(lastItem: Int = 0): Flow<Response<List<Artwork>>> = flowOf(Response.Error(Exception("Unable to load Artworks")))
}