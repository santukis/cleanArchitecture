package com.santukis.cleanarchitecture.artwork.data.remote

import androidx.annotation.VisibleForTesting
import com.santukis.cleanarchitecture.artwork.domain.model.*
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChicagoResponse(
    @Json(name = "data") val data: List<ChicagoArtwork> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ChicagoArtwork(
    @Json(name = "id") val id: Int? = 0,
    @Json(name = "title") val title: String? = "",
    @Json(name = "description") val description: String? = "",
    @Json(name = "provenance_text") val provenanceText: String? = "",
    @Json(name = "artist_title") val author: String? = "",
    @Json(name = "image_id") val image: String? = "",
    @Json(name = "date_display") val dating: String? = "",
    @Json(name = "dimensions") val dimensions: String? = "",
    @Json(name = "category_titles") val categories: List<String>? = emptyList(),
    @Json(name = "material_titles") val materials: List<String>? = emptyList(),
    @Json(name = "techniques_titles") val techniques: List<String>? = emptyList(),
    @Json(name = "credit_line") val creditLine: String? = "",
    @Json(name = "gallery_title") val department: String? = ""
) {
    companion object {
        val EMPTY = ChicagoArtwork()
    }

    fun toArtwork() =
        Artwork(
            id = id?.toString() ?: "",
            title = title ?: "",
            description = description.plus(" ").plus(provenanceText),
            author = author ?: "",
            dating = Dating(year = dating ?: ""),
            dimensions = extractDimensions(),
            image = image?.takeIf { it.isNotEmpty() }?.let { "https://www.artic.edu/iiif/2/$it/full/843,/0/default.jpg" } ?: "",
            categories = categories?.map { Category(category = it) } ?: emptyList(),
            materials = materials?.map { Material(material = it) } ?: emptyList(),
            creditLine = creditLine ?: "",
            collection = Collection.Chicago,
            url = "https://www.artic.edu/artworks/$id",
            department = department ?: "",
            shouldBeUpdated = false
        )

    @VisibleForTesting
    fun extractDimensions(): List<Dimension> {
        val dimensions = mutableListOf<Dimension>()

        this.dimensions?.takeIf { it.isNotEmpty() }?.let {
            val fields = it.substringBefore("(").split("×")
            val unit = fields.lastOrNull()?.trim()?.substringAfterLast(" ") ?: ""
            fields.forEachIndexed { index, field ->
                val dimension = when(index) {
                    0 -> Dimension.Width(value = field.trim().substringBeforeLast(" ").toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit))
                    1 -> Dimension.Height(value = field.trim().substringBeforeLast(" ").toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit))
                    2 -> Dimension.Depth(value = field.trim().substringBeforeLast(" ").toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit))
                    else -> Dimension.Unknown(value = field.trim().substringBeforeLast(" ").toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit))
                }

                dimensions.add(dimension)
            }
        }

        return dimensions
    }
}

