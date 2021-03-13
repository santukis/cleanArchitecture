package com.santukis.cleanarchitecture.artwork.data.local

import com.santukis.cleanarchitecture.artwork.domain.model.*

fun Artwork.toArtworkDb() =
    ArtworkDb(
        id = id,
        title = title,
        description = description,
        author = author,
        image = image,
        dating = dating.year,
        creditLine = creditLine,
        url = url,
        style = style,
        collection = collection,
        department = department,
        isPuzzle = categories.find { it.category.contains("paint", ignoreCase = true) } != null,
        shouldBeUpdated = shouldBeUpdated
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