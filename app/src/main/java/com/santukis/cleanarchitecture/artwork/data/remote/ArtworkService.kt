package com.santukis.cleanarchitecture.artwork.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtworkService {

    @GET("api/{language}/collection")
    suspend fun loadArtworks(@Path("language") language: String = "en"): Call<ArtworkResponse>
}