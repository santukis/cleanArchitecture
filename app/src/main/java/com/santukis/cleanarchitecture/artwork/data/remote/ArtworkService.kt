package com.santukis.cleanarchitecture.artwork.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ArtworkService {

    @GET("api/en/collection")
    suspend fun loadArtworks(@Query("key") apiKey: String,
                             @QueryMap fields: Map<String, Any>): Call<ArtworkResponse>
}