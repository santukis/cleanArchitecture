package com.santukis.cleanarchitecture.artwork.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ArtworkService {

    @GET("api/en/collection")
    suspend fun loadRijksMuseumArtworks(@Query("key") apiKey: String,
                                        @Query("imgonly") withImage: Boolean = true,
                                        @Query("toppieces") topPieces: Boolean = true,
                                        @Query("s") sortByType: String = "objecttype",
                                        @QueryMap fields: Map<String, String>): RijksMuseumResponse

    @GET("api/en/collection/{artworkId}")
    suspend fun loadRijksMuseumArtworkDetail(@Path("artworkId") artworkId: String,
                                             @Query("key") apiKey: String): RijksMuseumResponse

    @GET("public/collection/v1/objects")
    suspend fun loadMetArtworks(@Query("departmentIds") departmentId: String,
                                @Query("hasImages") hasImages: Boolean = true,
                                @Query("isHighlight") isHighlight: Boolean = true): MetResponse

    @GET("public/collection/v1/objects/{artworkId}")
    suspend fun loadMetArtworkDetail(@Path("artworkId") artworkId: String): MetArtwork
}