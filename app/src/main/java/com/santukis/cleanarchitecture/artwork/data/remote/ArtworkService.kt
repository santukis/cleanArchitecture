package com.santukis.cleanarchitecture.artwork.data.remote

import retrofit2.http.*

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

    @Headers("User-Agent: funny-art (santamadavid@gmail.com)", )
    @GET("api/v1/artworks/search")
    suspend fun loadChicagoArtworks(@Query("query[term][is_public_domain]") publicDomain: Boolean = true,
                                    @Query("fields") fields: String = "id,title,date_display,artist_title,dimensions,credit_line,provenance_text,description,category_titles,material_titles,techniques_titles,image_id",
                                    @Query("page") page: Int,
                                    @Query("limit") size: Int): ChicagoResponse
}