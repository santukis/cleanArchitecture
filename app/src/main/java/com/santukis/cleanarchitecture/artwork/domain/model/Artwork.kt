package com.santukis.cleanarchitecture.artwork.domain.model

data class Artwork(
    val id: String,
    val title: String = "",
    val description: String = "",
    val author: String = "",
    val dating: Dating = Dating.EMPTY,
    val dimensions: List<Dimension> = emptyList(),
    val image: String = "",
    val colors: List<Color> = emptyList()
)

data class Dating(
    val year: Int = 0,
    val started: Int = 0,
    val finished: Int = 0
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

    class Weight(value: Double, unit: MeasureUnit): Dimension(value, unit)

    class Unknown(value: Double, unit: MeasureUnit): Dimension(value, unit)

}

data class MeasureUnit(val unit: String) {

    companion object {
        val EMPTY = MeasureUnit("")
    }
}

data class Color(
    val percentage: Int = 0,
    val color: String = "#000000"
)