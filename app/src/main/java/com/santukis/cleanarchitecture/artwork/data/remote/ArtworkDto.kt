package com.santukis.cleanarchitecture.artwork.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonQualifier

data class ArtworkDto(
    @Json(name = "objectNumber") val id: String,
    @Json(name = "title") val title: String? = "",
    @Json(name = "plaqueDescriptionEnglish") val description: String? = "",
    @Json(name = "principalMaker") val author: String? = "",
    @Json(name = "subTitle") val dimensions: String? = "",
    @Json(name = "normalized32Colors") val colors: List<ColorDto> = emptyList(),
) {
}

data class ColorDto(
    @Json(name = "percentage") val percentage: Int? = 0,
    @Json(name = "hex") val color: String = "#000000"
)