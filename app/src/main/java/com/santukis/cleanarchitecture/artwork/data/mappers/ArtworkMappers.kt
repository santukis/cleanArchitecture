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
        colors = colors?.map { it.toColor() } ?: emptyList(),
        categories = categories?.map { Category(it) } ?: emptyList(),
        materials = materials?.map { Material(it) } ?: emptyList(),
        techniques = techniques?.map { Technique(it) } ?: emptyList()
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
        year = year ?: 0
    )

fun ColorDto.toColor() =
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

fun ArtworkDb.toArtwork() =
    Artwork(
        id = id,
        title = title,
        description = description,
        author = author,
        image = image,
        dating = Dating(year = dating),
        shouldBeUpdated = (System.currentTimeMillis() - updatedAt) >= 24 * 60  * 60* 1000L
    )

fun ArtworkDb.toArtwork(
    dimensions: List<Dimension>,
    colors: List<Color>,
    categories: List<Category>,
    materials: List<Material>,
    techniques: List<Technique>) =
    Artwork(
        id = id,
        title = title,
        description = description,
        author = author,
        image = image,
        dating = Dating(year = dating),
        dimensions = dimensions,
        colors = colors,
        categories = categories,
        materials = materials,
        techniques = techniques,
        shouldBeUpdated = (System.currentTimeMillis() - updatedAt) >= 24 * 60  * 60* 1000L
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
        normalizedColor = normalizedColor,
        color = color
    )

fun CategoryDb.toCategory() = Category(category)

fun MaterialDb.toMaterial() = Material(material)

fun TechniqueDb.toTechnique() = Technique(technique)

fun ArtworkDetailDb.toArtwork() =
    artworkDb.toArtwork(
        dimensions = dimensions.map { it.toDimension() },
        colors = colors.map { it.toColor() },
        categories = categories.map { it.toCategory() },
        materials = materials.map { it.toMaterial() },
        techniques = techniques.map { it.toTechnique() }
    )

fun Artwork.toArtworkDb() =
    ArtworkDb(
        id = id,
        title = title,
        description = description,
        author = author,
        image = image,
        dating = dating.year
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
        normalizedColor = normalizedColor,
        color = color
    )

fun Category.toCategoryDb(parentId: String) =
    CategoryDb(
        id = null,
        parentId = parentId,
        category = category
    )

fun Material.toMaterialDb(parentId: String) =
    MaterialDb(
        id = null,
        parentId = parentId,
        material = material
    )

fun Technique.toTechniqueDb(parentId: String) =
    TechniqueDb(
        id = null,
        parentId = parentId,
        technique = technique
    )