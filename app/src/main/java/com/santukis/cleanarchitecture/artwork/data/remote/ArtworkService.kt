package com.santukis.cleanarchitecture.artwork.data.remote

import retrofit2.http.*

interface ArtworkService {

    @GET("api/en/collection")
    suspend fun loadRijksMuseumArtworks(
        @Query("key") apiKey: String,
        @Query("imgonly") withImage: Boolean = true,
        @Query("toppieces") topPieces: Boolean = true,
        @Query("s") sortByType: String = "objecttype",
        @QueryMap fields: Map<String, String>
    ): RijksMuseumResponse

    @GET("api/en/collection/{artworkId}")
    suspend fun loadRijksMuseumArtworkDetail(
        @Path("artworkId") artworkId: String,
        @Query("key") apiKey: String
    ): RijksMuseumResponse

    @GET("public/collection/v1/objects")
    suspend fun loadMetArtworks(
        @Query("departmentIds") departmentId: String,
        @Query("hasImages") hasImages: Boolean = true,
        @Query("isHighlight") isHighlight: Boolean = true
    ): MetResponse

    @GET("public/collection/v1/objects/{artworkId}")
    suspend fun loadMetArtworkDetail(@Path("artworkId") artworkId: String): MetArtwork

    @Headers("User-Agent: funny-art (santamadavid@gmail.com)")
    @GET("api/v1/artworks/search")
    suspend fun loadChicagoArtworks(
        @Query("query[term][is_public_domain]") publicDomain: Boolean = true,
        @Query("fields") fields: String = "id,title,date_display,artist_display,dimensions,credit_line,provenance_text,description,category_titles,material_titles,techniques_titles,department_title,style_title,image_id",
        @Query("page") page: Int,
        @Query("limit") size: Int
    ): ChicagoResponse

    @GET("api/artworks")
    suspend fun loadClevelandArtworks(
        @Query("cc0") publicDomain: Boolean = true,
        @Query("hasImage") hasImage: Boolean = true,
        @Query("currently_on_view") onView: Boolean = true,
        @Query("skip") skip: Int,
        @Query("limit") size: Int
    ): ClevelandResponse

    @GET("object")
    suspend fun loadHardvardArtworks(
        @Query("apikey") apiKey: String,
        @Query("hasimage") hasImage: Int = 1,
        @Query("fields") fields: String = "id,creditline,copyright,description,people,primaryimageurl,dated,dimensions,division,technique,url,colors,provenance,department,title,style,culture",
        @Query("gallery") onView: String = "any",
        @Query("page") page: Int,
        @Query("size") size: Int
    ): HardvardResponse

    @GET("v1/objects")
    suspend fun loadWaltersArtworks(
        @Query("apikey") apiKey: String,
        @Query("page") page: Int,
        @Query("pageSize") size: Int
    ): WaltersResponse
}