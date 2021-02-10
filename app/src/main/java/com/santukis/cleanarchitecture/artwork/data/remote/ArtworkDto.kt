package com.santukis.cleanarchitecture.artwork.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtworkResponse(
    @Json(name = "artObject") val item: ArtworkDto = ArtworkDto.EMPTY,
    @Json(name = "artObjects") val items: List<ArtworkDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ArtworkDto(
    @Json(name = "objectNumber") val id: String? = "",
    @Json(name = "title") val title: String? = "",
    @Json(name = "plaqueDescriptionEnglish") val description: String? = "",
    @Json(name = "principalOrFirstMaker") val author: String? = "",
    @Json(name = "webImage") val image: ImageDto? = ImageDto(),
    @Json(name = "dating") val dating: DatingDto? = DatingDto.EMPTY,
    @Json(name = "dimensions") val dimensions: List<DimensionDto>? = emptyList(),
    @Json(name = "colorsWithNormalization") val colors: List<ColorDto>? = emptyList(),
    @Json(name = "objectTypes") val categories: List<String>? = emptyList(),
    @Json(name = "materials") val materials: List<String>? = emptyList(),
    @Json(name = "techniques") val techniques: List<String>? = emptyList()
) {
    companion object {
        val EMPTY = ArtworkDto()
    }
}

@JsonClass(generateAdapter = true)
data class DimensionDto(
    @Json(name = "unit") val unit: String? = "",
    @Json(name = "type") val type: String? = "",
    @Json(name = "value") val value: String? = "",
)

@JsonClass(generateAdapter = true)
data class ImageDto(
    @Json(name = "url") val url: String? = ""
)

@JsonClass(generateAdapter = true)
data class DatingDto(
    @Json(name = "sortingDate") val year: Int? = 0,
    @Json(name = "yearEarly") val from: Int? = 0,
    @Json(name = "yearLate") val to: Int? = 0
) {
    companion object {
        val EMPTY = DatingDto()
    }
}

@JsonClass(generateAdapter = true)
data class ColorDto(
    @Json(name = "originalHex") val color: String? = "#000000",
    @Json(name = "normalizedHex") val normalizedColor: String? = "#000000"
)