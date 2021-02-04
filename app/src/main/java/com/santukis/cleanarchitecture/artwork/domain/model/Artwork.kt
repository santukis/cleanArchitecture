package com.santukis.cleanarchitecture.artwork.domain.model

data class Artwork(
    val id: String,
    val title: String = "",
    val description: String = "",
    val author: String = "",
    val year: Int = 0,
    val dimensions: String = "",
    val image: String = "",
    val colors: List<Color> = emptyList()
) {
}

data class Color(
    val percentage: Int = 0,
    val color: String = "#000000"
)