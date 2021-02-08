package com.santukis.cleanarchitecture.artwork.data.mappers

import com.santukis.cleanarchitecture.artwork.data.local.ArtworkDb
import com.santukis.cleanarchitecture.artwork.data.local.DatingDb
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
        color = color ?: "#000000"
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

fun DatingDb.toDating() =
    Dating(
        year = year,
        started = started,
        finished = finished
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

private fun Dating.toDatingDb(): DatingDb =
    DatingDb(
        year = year,
        started = started,
        finished = finished
    )
