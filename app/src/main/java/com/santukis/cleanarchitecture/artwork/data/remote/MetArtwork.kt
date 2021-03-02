package com.santukis.cleanarchitecture.artwork.data.remote

import com.santukis.cleanarchitecture.artwork.domain.model.*
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetResponse(
    @Json(name = "total") val total: Int = 0,
    @Json(name = "objectIDs") val ids: List<Int> = emptyList()
)

@JsonClass(generateAdapter = true)
data class MetArtwork(
    @Json(name = "objectID") val id: Int? = 0,
    @Json(name = "title") val title: String? = "",
    @Json(name = "artistDisplayName") val author: String? = "",
    @Json(name = "primaryImage") val image: String? = "",
    @Json(name = "objectDate") val dating: String? = "",
    @Json(name = "measurements") val dimensions: List<MetDimension>? = emptyList(),
    @Json(name = "objectName") val category: String? = "",
    @Json(name = "medium") val material: String? = "",
    @Json(name = "creditLine") val creditLine: String? = "",
    @Json(name = "repository") val museum: String? = "",
    @Json(name = "objectURL") val url: String? = "",
    @Json(name = "department") val department: String? = ""
) {
    companion object {
        val EMPTY = MetArtwork()
    }

    fun toArtwork() =
        Artwork(
            id = id?.toString() ?: "",
            title = title ?: "",
            author = author ?: "Anonymous",
            dating = Dating(year = dating ?: ""),
            dimensions = dimensions?.firstOrNull { it.type == "Overall" }?.toDimension() ?: emptyList(),
            image = image ?: "",
            categories = listOf(Category(category = category ?: "")),
            materials = listOf(Material(material = material ?: "")),
            creditLine = creditLine ?: "",
            museum = museum ?: "",
            collection = Collection.Met,
            department = department ?: "",
            shouldBeUpdated = false,
            url = url ?: ""
        )
}

@JsonClass(generateAdapter = true)
data class MetDimension(
    @Json(name = "elementName") val type: String? = "",
    @Json(name = "elementDescription") val description: String? = "",
    @Json(name = "elementMeasurements") val measures: Map<String, Double>? = mutableMapOf(),
) {

    fun toDimension(): List<Dimension> {
        val dimensions = mutableListOf<Dimension>()

        measures?.forEach { entry ->
            when(entry.key) {
                "Height" -> dimensions.add(Dimension.Height(value = entry.value, unit = MeasureUnit(unit = "cm")))
                "Width" -> dimensions.add(Dimension.Width(value = entry.value, unit = MeasureUnit(unit = "cm")))
                "Depth" -> dimensions.add(Dimension.Depth(value = entry.value, unit = MeasureUnit(unit = "cm")))
                "Weight" -> dimensions.add(Dimension.Weight(value = entry.value, unit = MeasureUnit(unit = "gr")))
            }
        }

        return dimensions
    }
}

