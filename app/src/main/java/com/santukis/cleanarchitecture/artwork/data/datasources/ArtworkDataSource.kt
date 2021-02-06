package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.Exception

interface ArtworkDataSource {

    suspend fun loadArtworks(lastItem: Int = 0): Response<List<Artwork>> = Response.Error(Exception("Unable to load artworks from datasource"))

    suspend fun loadArtworks(): Flow<Response<List<Artwork>>> = flowOf(Response.Error(Exception("Unable to load artworks from datasource")))
}