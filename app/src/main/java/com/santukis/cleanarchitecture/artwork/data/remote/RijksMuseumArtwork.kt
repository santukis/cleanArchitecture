package com.santukis.cleanarchitecture.artwork.data.remote

import com.santukis.cleanarchitecture.artwork.domain.model.*
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class RijksMuseumResponse(
    @Json(name = "artObject") val item: RijksMuseumArtwork = RijksMuseumArtwork.EMPTY,
    @Json(name = "artObjects") val items: List<RijksMuseumArtwork> = emptyList()
)

@JsonClass(generateAdapter = true)
data class RijksMuseumArtwork(
    @Json(name = "objectNumber") val id: String? = "",
    @Json(name = "title") val title: String? = "",
    @Json(name = "plaqueDescriptionEnglish") val description: String? = "",
    @Json(name = "principalOrFirstMaker") val author: String? = "",
    @Json(name = "webImage") val image: RijksMuseumImage? = RijksMuseumImage(),
    @Json(name = "dating") val dating: RijksMuseumDating? = RijksMuseumDating.EMPTY,
    @Json(name = "dimensions") val dimensions: List<RijksMuseumDimension>? = emptyList(),
    @Json(name = "colorsWithNormalization") val colors: List<RijksMuseumColor>? = emptyList(),
    @Json(name = "objectTypes") val categories: List<String>? = emptyList(),
    @Json(name = "materials") val materials: List<String>? = emptyList(),
    @Json(name = "techniques") val techniques: List<String>? = emptyList(),
    @Json(name = "label") val resume: RijksMuseumLabel? = RijksMuseumLabel.EMPTY,
    @Json(name = "links") val links: RijksMuseumLink = RijksMuseumLink.EMPTY,
    @Json(name = "acquisition") val creditLine: RijksMuseumAcquisition = RijksMuseumAcquisition.EMPTY
) {
    companion object {
        val EMPTY = RijksMuseumArtwork()
    }

    fun toArtwork() =
        Artwork(
            id = id ?: "",
            title = title.takeIf { !it.isNullOrEmpty() } ?: resume?.title ?: "",
            description = description.takeIf { !it.isNullOrEmpty() } ?: resume?.description ?: "",
            author = author ?: "",
            image = image?.url ?: "",
            dating = dating?.toDating() ?: Dating.EMPTY,
            dimensions = dimensions?.map { it.toDimension() } ?: emptyList(),
            colors = colors?.map { it.toColor() } ?: emptyList(),
            categories = categories?.map { Category(it) } ?: emptyList(),
            materials = materials?.map { Material(it) } ?: emptyList(),
            techniques = techniques?.map { Technique(it) } ?: emptyList(),
            creditLine = creditLine.toString(),
            collection = Collection.Rijksmuseum,
            url = links.url ?: ""
    )
}

@JsonClass(generateAdapter = true)
data class RijksMuseumDimension(
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
data class RijksMuseumImage(
    @Json(name = "url") val url: String? = ""
)

@JsonClass(generateAdapter = true)
data class RijksMuseumDating(
    @Json(name = "presentingDate") val year: String? = ""
) {
    companion object {
        val EMPTY = RijksMuseumDating()
    }

    fun toDating() = Dating(year = year ?: "")
}

@JsonClass(generateAdapter = true)
data class RijksMuseumColor(
    @Json(name = "originalHex") val color: String? = "#000000",
    @Json(name = "normalizedHex") val normalizedColor: String? = "#000000"
) {
    fun toColor() =
        Color(
            normalizedColor = normalizedColor ?: "#000000",
            color = color?.trim()?.toRGB() ?: android.graphics.Color.BLACK
        )

    fun String.toRGB(): Int = try {
        when (isNullOrEmpty()) {
            false -> android.graphics.Color.parseColor(this)
            true -> android.graphics.Color.BLACK
        }
    } catch (exception: Exception) {
        android.graphics.Color.BLACK
    }
}

@JsonClass(generateAdapter = true)
data class RijksMuseumLabel(
    @Json(name = "title") val title: String? = "",
    @Json(name = "markerLine") val subtitle: String? = "",
    @Json(name = "description") val description: String? = ""
) {
    companion object {
        val EMPTY = RijksMuseumLabel()
    }
}

@JsonClass(generateAdapter = true)
data class RijksMuseumLink(
    @Json(name = "self") val url: String? = ""

) {
    companion object {
        val EMPTY = RijksMuseumLink()
    }
}

@JsonClass(generateAdapter = true)
data class RijksMuseumAcquisition(
    @Json(name = "method") val method: String? = "",
    @Json(name = "date") val date: String? = "",
    @Json(name = "creditLine") val creditLine: String? = ""
) {
    companion object {
        val EMPTY = RijksMuseumAcquisition()
    }

    override fun toString(): String {
        return "$creditLine ${date.takeIf { !it.isNullOrEmpty() }?.let { LocalDateTime.parse(it) }}"
    }
}