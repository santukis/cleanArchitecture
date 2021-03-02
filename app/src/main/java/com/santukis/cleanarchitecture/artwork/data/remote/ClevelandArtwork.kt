package com.santukis.cleanarchitecture.artwork.data.remote

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
            description = description.plus("\n").plus(funFact),
            author = author?.firstOrNull()?.name ?: "Anonymous",
            dating = Dating(year = dating ?: ""),
            dimensions = dimensions?.extractDimensions() ?: emptyList(),
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

