package com.santukis.cleanarchitecture.artwork.data.remote

import com.santukis.cleanarchitecture.artwork.domain.model.*
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WaltersResponse(
    @Json(name = "Items") val data: List<WaltersArtwork> = emptyList()
)

@JsonClass(generateAdapter = true)
data class WaltersArtwork(
    @Json(name = "ObjectID") val id: Int? = 0,
    @Json(name = "Title") val title: String? = "",
    @Json(name = "Description") val description: String? = "",
    @Json(name = "Creators") val author: String? = "",
    @Json(name = "PrimaryImage") val image: WaltersImage? = WaltersImage.EMPTY,
    @Json(name = "DateText") val dating: String? = "",
    @Json(name = "Dimensions") val dimensions: String? = "",
    @Json(name = "Keywords") val category: String? = "",
    @Json(name = "Medium") val material: String? = "",
    @Json(name = "CreditLine") val creditLine: String? = "",
    @Json(name = "ResourceURL") val url: String? = "",
    @Json(name = "Collection") val collection: String? = "",
    @Json(name = "DisplayLocation") val department: String? = "",
    @Json(name = "Provenance") val provenance: String? = ""
) {
    companion object {
        val EMPTY = WaltersArtwork()
    }

    fun toArtwork() =
        Artwork(
            id = id?.toString() ?: "",
            title = title ?: "",
            description = (description ?: "").plus(" ").plus(provenance ?: ""),
            author = author ?: "Anonymous",
            dating = Dating(year = dating ?: ""),
            dimensions = dimensions?.extractDimensions() ?: emptyList(),
            image = image?.large ?: image?.medium ?: image?.small ?: "",
            categories = category?.takeIf { it.isNotEmpty() }?.let { it.split(";").map { Category(it) } } ?: emptyList(),
            materials = material?.takeIf { it.isNotEmpty() }?.let { listOf(Material(material = it)) } ?: emptyList(),
            creditLine = creditLine ?: "",
            collection = Collection.Walters,
            url = url ?: "",
            department = (collection ?: "").plus(" ").plus(department ?: ""),
            shouldBeUpdated = false
        )
}

@JsonClass(generateAdapter = true)
data class WaltersImage(
    @Json(name = "Tiny") val tiny: String? = "",
    @Json(name = "Small") val small: String? = "",
    @Json(name = "Medium") val medium: String? = "",
    @Json(name = "Large") val large: String? = "",
    @Json(name = "Raw") val raw: String? = ""
) {
    companion object {
        val EMPTY = WaltersImage()
    }
}