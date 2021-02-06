package com.santukis.cleanarchitecture.artwork.data.remote

import com.santukis.cleanarchitecture.artwork.domain.model.*
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
    @Json(name = "normalized32Colors") val colors: List<ColorDto>? = emptyList(),
) {
    companion object {
        val EMPTY = ArtworkDto()
    }

    fun toArtwork() =
        Artwork(
            id = id ?: "",
            title = title ?: "",
            description = description ?: "",
            author = author ?: "",
            image = image?.url ?: "",
            dating = dating?.toDating() ?: Dating.EMPTY,
            dimensions = dimensions?.map { it.toDimension() } ?: emptyList(),
            colors = colors?.map { it.toColor() } ?: emptyList()
        )
}

@JsonClass(generateAdapter = true)
data class DimensionDto(
    @Json(name = "unit") val unit: String? = "",
    @Json(name = "type") val type: String? = "",
    @Json(name = "value") val value: String? = "",
) {

    fun toDimension() = when(type) {
        "height" -> Dimension.Height(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
        "width" -> Dimension.Width(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
        "depth" -> Dimension.Depth(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
        "weight" -> Dimension.Weight(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
        else -> Dimension.Unknown(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
    }
}

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

    fun toDating() =
        Dating(
            year = year ?: 0,
            started = from ?: 0,
            finished = to ?: 0
        )
}

@JsonClass(generateAdapter = true)
data class ColorDto(
    @Json(name = "percentage") val percentage: Int? = 0,
    @Json(name = "hex") val color: String? = "#000000"
) {
    fun toColor() =
        Color(
            percentage = percentage ?: 0,
            color = color ?: "#000000"
        )
}