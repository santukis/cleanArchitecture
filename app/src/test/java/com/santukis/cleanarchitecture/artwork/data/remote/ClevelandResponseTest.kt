package com.santukis.cleanarchitecture.artwork.data.remote

import org.junit.Assert.*
import org.junit.Test

class ClevelandResponseTest {

    @Test
    fun extractDimensionsShouldReturnExpectedValuesAreThree() {
        val clevelandArtwork = ClevelandArtwork(dimensions = "35.5 x 62.5 x 2.7 cm (14 x 24 5/8 x 1 1/16 in.)")

        val dimensions = clevelandArtwork.extractDimensions()

        assertEquals(3, dimensions.size)
        assertEquals(35.5, dimensions[0].value, 0.0)
        assertEquals(62.5, dimensions[1].value, 0.0)
        assertEquals(2.7, dimensions[2].value, 0.0)
        assertEquals("cm", dimensions[0].unit.unit)
    }

    @Test
    fun extractDimensionsShouldReturnExpectedValuesWhenDimensionsAreTwo() {
        val clevelandArtwork = ClevelandArtwork(dimensions = "Framed: 125.4 x 186.7 x 9.2 cm (49 3/8 x 73 1/2 x 3 5/8 in.); Unframed: 99.5 x 160.4 cm (39 3/16 x 63 1/8 in.)")

        val dimensions = clevelandArtwork.extractDimensions()

        assertEquals(3, dimensions.size)
        assertEquals(125.4, dimensions[0].value, 0.0)
        assertEquals(186.7, dimensions[1].value, 0.0)
        assertEquals(9.2, dimensions[2].value, 0.0)
        assertEquals("cm", dimensions[0].unit.unit)
    }
}