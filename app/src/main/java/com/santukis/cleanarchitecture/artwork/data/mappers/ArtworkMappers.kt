package com.santukis.cleanarchitecture.artwork.data.mappers

import com.santukis.cleanarchitecture.artwork.data.local.*
import com.santukis.cleanarchitecture.artwork.data.remote.ArtworkDto
import com.santukis.cleanarchitecture.artwork.data.remote.ColorDto
import com.santukis.cleanarchitecture.artwork.data.remote.DatingDto
import com.santukis.cleanarchitecture.artwork.data.remote.DimensionDto
import com.santukis.cleanarchitecture.artwork.domain.model.*

fun ArtworkDto.toArtwork() =
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

fun DimensionDto.toDimension() = when(type) {
    "height" -> Dimension.Height(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
    "width" -> Dimension.Width(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
    "depth" -> Dimension.Depth(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
    "weight" -> Dimension.Weight(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
    else -> Dimension.Unknown(value = value?.toDoubleOrNull() ?: 0.0, unit = MeasureUnit(unit = unit ?: ""))
}

fun DatingDto.toDating() =
    Dating(
        year = year ?: 0,
        started = from ?: 0,
        finished = to ?: 0
    )

fun ColorDto.toColor() =
    Color(
        percentage = percentage ?: 0,
        color = color?.trim() ?: "#000000"
    )

fun ArtworkDb.toArtwork() =
    Artwork(
        id = id,
        title = title,
        description = description,
        author = author,
        image = image,
        dating = dating.toDating()
    )

fun ArtworkDb.toArtwork(dimensions: List<Dimension>, colors: List<Color>) =
    Artwork(
        id = id,
        title = title,
        description = description,
        author = author,
        image = image,
        dating = dating.toDating(),
        dimensions = dimensions,
        colors = colors
    )

fun DatingDb.toDating() =
    Dating(
        year = year,
        started = started,
        finished = finished
    )

fun DimensionDb.toDimension() =
    when(type) {
        Dimension.Height::class.java.name -> Dimension.Height(value = value, unit = unit.toMeasureUnit())
        Dimension.Width::class.java.name -> Dimension.Width(value = value, unit = unit.toMeasureUnit())
        Dimension.Depth::class.java.name -> Dimension.Depth(value = value, unit = unit.toMeasureUnit())
        Dimension.Weight::class.java.name -> Dimension.Weight(value = value, unit = unit.toMeasureUnit())
        else -> Dimension.Unknown(value = value, unit = unit.toMeasureUnit())
    }

fun MeasureUnitDb.toMeasureUnit() = MeasureUnit(unit)

fun ColorDb.toColor() =
    Color(
        percentage = percentage,
        color = color
    )

fun ArtworkDetailDb.toArtwork() =
    artworkDb.toArtwork(
        dimensions = dimensions.map { it.toDimension() },
        colors = colors.map { it.toColor() }
    )


fun Artwork.toArtworkDb() =
    ArtworkDb(
        id = id,
        title = title,
        description = description,
        author = author,
        image = image,
        dating = dating.toDatingDb()
    )

fun Dating.toDatingDb(): DatingDb =
    DatingDb(
        year = year,
        started = started,
        finished = finished
    )

fun Dimension.toDimensionDb(parentId: String): DimensionDb =
    DimensionDb(
        dimensionId = null,
        parentId = parentId,
        unit = unit.toMeasureUnitDb(),
        value = value,
        type = this::class.java.name
    )

fun MeasureUnit.toMeasureUnitDb() =
    MeasureUnitDb(unit = unit)

fun Color.toColorDb(parentId: String) =
    ColorDb(
        colorId = null,
        parentId = parentId,
        percentage = percentage,
        color = color
    )