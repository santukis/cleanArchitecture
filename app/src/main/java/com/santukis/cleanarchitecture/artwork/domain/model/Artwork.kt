package com.santukis.cleanarchitecture.artwork.domain.model

data class Artwork(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val author: String = "",
    val dating: Dating = Dating.EMPTY,
    var dimensions: List<Dimension> = emptyList(),
    val image: String = "",
    var colors: List<Color> = emptyList(),
    var categories: List<Category> = emptyList(),
    var materials: List<Material> = emptyList(),
    var techniques: List<Technique> = emptyList(),
    var creditLine: String = "",
    var collection: Collection = Collection.Unknown,
    var url: String = "",
    var shouldBeUpdated: Boolean = false
) {
    companion object {
        val EMPTY = Artwork()
    }
}

data class Dating(
    val year: String = "",
) {
    companion object {
        val EMPTY = Dating()
    }
}

sealed class Dimension(
    val value: Double = 0.0,
    val unit: MeasureUnit = MeasureUnit.EMPTY
) {

    class Height(value: Double, unit: MeasureUnit): Dimension(value, unit)

    class Width(value: Double, unit: MeasureUnit): Dimension(value, unit)

    class Depth(value: Double, unit: MeasureUnit): Dimension(value, unit)

    class Weight(value: Double, unit: MeasureUnit): Dimension(value, unit)

    class Unknown(value: Double, unit: MeasureUnit): Dimension(value, unit)

}

data class MeasureUnit(val unit: String) {

    companion object {
        val EMPTY = MeasureUnit("")
    }
}

data class Color(
    val color: Int = 0,
    val normalizedColor: String = "#000000"
)

data class Category(
    val category: String = ""
)

data class Material(
    val material: String = ""
)

data class Technique(
    val technique: String = ""
)

enum class Collection {
    Unknown, Rijksmuseum, Met, Chicago, Cleveland, Hardvard, Walters
}