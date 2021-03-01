package com.santukis.cleanarchitecture.artwork.data.remote

import com.santukis.cleanarchitecture.artwork.domain.model.*
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HardvardResponse(
    @Json(name = "records") val data: List<HardvardArtwork> = emptyList()
)

@JsonClass(generateAdapter = true)
data class HardvardArtwork(
    @Json(name = "id") val id: Int? = 0,
    @Json(name = "title") val title: String? = "",
    @Json(name = "description") val description: String? = "",
    @Json(name = "people") val author: List<HardvardAuthor>? = emptyList(),
    @Json(name = "primaryimageurl") val image: String? = "",
    @Json(name = "dated") val dating: String? = "",
    @Json(name = "dimensions") val dimensions: String? = "",
    @Json(name = "type") val category: String? = "",
    @Json(name = "technique") val technique: String? = "",
    @Json(name = "creditline") val creditLine: String? = "",
    @Json(name = "url") val url: String? = "",
    @Json(name = "colors") val colors: List<HardvardColor>? = emptyList(),
    @Json(name = "provenance") val collection: String? = "",
    @Json(name = "department") val department: String? = "",
    @Json(name = "provenance") val provenance: String? = "",
    @Json(name = "copyright") val copyright: String? = ""
) {
    companion object {
        val EMPTY = HardvardArtwork()
    }

    fun toArtwork() =
        Artwork(
            id = id?.toString() ?: "",
            title = title ?: "",
            description = description.plus("\n").plus(copyright),
            author = author?.firstOrNull()?.name ?: "",
            dating = Dating(year = dating ?: ""),
            dimensions = dimensions?.extractDimensions() ?: emptyList(),
            image = image ?: "",
            categories = category?.takeIf { it.isNotEmpty() }?.let { listOf(Category(category = it)) } ?: emptyList(),
            techniques = technique?.takeIf { it.isNotEmpty() }?.let { listOf(Technique(technique = it)) } ?: emptyList(),
            creditLine = creditLine ?: "",
            museum = collection ?: "",
            collection = Collection.Cleveland,
            url = url ?: "",
            department = department ?: "",
            shouldBeUpdated = false
        )
}

@JsonClass(generateAdapter = true)
data class HardvardAuthor(
    @Json(name = "role") val role: String? = "",
    @Json(name = "displayname") val name: String? = ""
) {
    companion object {
        val EMPTY = HardvardAuthor()
    }
}

@JsonClass(generateAdapter = true)
data class HardvardColor(
    @Json(name = "color") val color: String? = ""
) {
    companion object {
        val EMPTY = HardvardAuthor()
    }
}

fun String.extractDimensions(): List<Dimension> {
    val dimensions = mutableListOf<Dimension>()

    takeIf { it.isNotEmpty() }?.let {
        val unformattedDimensions = it.substringBefore("(")
        val unit = unformattedDimensions.trim().substringAfterLast(" ")
        val formattedDimension = unformattedDimensions.trim().substringAfter(":").substringBeforeLast(" ")
        formattedDimension.split("x").forEachIndexed { index, field ->
            val dimension = when(index) {
                0 -> Dimension.Width(value = field.trim().toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit))
                1 -> Dimension.Height(value = field.trim().toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit))
                2 -> Dimension.Depth(value = field.trim().toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit))
                else -> Dimension.Unknown(value = field.trim().toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit))
            }

            dimensions.add(dimension)
        }
    }

    return dimensions
}