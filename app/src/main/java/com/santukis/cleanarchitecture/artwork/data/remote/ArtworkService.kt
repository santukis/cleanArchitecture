package com.santukis.cleanarchitecture.artwork.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ArtworkService {

    @GET("api/en/collection")
    suspend fun loadArtworks(@Query("key") apiKey: String,
                             @QueryMap fields: Map<String, String>): ArtworkResponse

    @GET("api/en/collection/{artworkId}")
    suspend fun loadArtworkDetail(@Path("artworkId") artworkId: String,
                                  @Query("key") apiKey: String): ArtworkResponse
}