package com.santukis.cleanarchitecture.artwork.data.remote

import androidx.annotation.VisibleForTesting
import com.santukis.cleanarchitecture.artwork.domain.model.*
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClevelandResponse(
    @Json(name = "data") val data: List<ClevelandArtwork> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ClevelandArtwork(
    @Json(name = "id") val id: Int? = 0,
    @Json(name = "title") val title: String? = "",
    @Json(name = "wall_description") val description: String? = "",
    @Json(name = "creators") val author: List<ClevelandAuthor>? = emptyList(),
    @Json(name = "images") val image: ClevelandImage? = ClevelandImage.EMPTY,
    @Json(name = "creation_date") val dating: String? = "",
    @Json(name = "measurements") val dimensions: String? = "",
    @Json(name = "type") val category: String? = "",
    @Json(name = "technique") val technique: String? = "",
    @Json(name = "creditline") val creditLine: String? = "",
    @Json(name = "url") val url: String? = "",
    @Json(name = "collection") val collection: String? = "",
    @Json(name = "department") val department: String? = "",
    @Json(name = "fun_fact") val funFact: String? = ""
) {
    companion object {
        val EMPTY = ClevelandArtwork()
    }

    fun toArtwork() =
        Artwork(
            id = id?.toString() ?: "",
            title = title ?: "",
            description = description.plus(" ").plus(funFact),
            author = author?.firstOrNull()?.name ?: "",
            dating = Dating(year = dating ?: ""),
            dimensions = extractDimensions(),
            image = image?.takeIf { it != ClevelandImage.EMPTY }?.let { it.webFields?.getOrElse("url", { "" }) } ?: "",
            categories = category?.takeIf { it.isNotEmpty() }?.let { listOf(Category(category = it)) } ?: emptyList(),
            techniques = technique?.takeIf { it.isNotEmpty() }?.let { listOf(Technique(technique = it)) } ?: emptyList(),
            creditLine = creditLine ?: "",
            museum = collection ?: "",
            collection = Collection.Cleveland,
            url = url ?: "",
            department = department ?: "",
            shouldBeUpdated = false
        )

    @VisibleForTesting
    fun extractDimensions(): List<Dimension> {
        val dimensions = mutableListOf<Dimension>()

        this.dimensions?.takeIf { it.isNotEmpty() }?.let {
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
}

@JsonClass(generateAdapter = true)
data class ClevelandAuthor(
    @Json(name = "description") val name: String? = ""
) {
    companion object {
        val EMPTY = ClevelandAuthor()
    }
}

@JsonClass(generateAdapter = true)
data class ClevelandImage(
    @Json(name = "web") val webFields: Map<String, String>? = mutableMapOf()
) {
    companion object {
        val EMPTY = ClevelandImage()
    }
}

