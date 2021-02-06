package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import java.lang.Exception

interface ArtworkDataSource {

    suspend fun loadArtworks(lastItem: Int = 0): Response<List<Artwork>> = Response.Error(Exception("Unable to load artworks from datasource"))
}