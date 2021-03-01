package com.santukis.cleanarchitecture.artwork.data.remote

import org.junit.Assert.*
import org.junit.Test

class HardvardArtworkTest {

    @Test
    fun extractDimensionsShouldReturnExpectedValuesAreThree() {
        val dimensions = "box: 18 x 13 x 1.2 cm (7 1/16 x 5 1/8 x 1/2 in.)".extractDimensions()

        assertEquals(3, dimensions.size)
        assertEquals(18.0, dimensions[0].value, 0.0)
        assertEquals(13.0, dimensions[1].value, 0.0)
        assertEquals(1.2, dimensions[2].value, 0.0)
        assertEquals("cm", dimensions[0].unit.unit)
    }

    @Test
    fun extractDimensionsShouldReturnExpectedValuesWhenDimensionsAreTwo() {
        val dimensions = "greatest dimen.: 65 x 70 cm (25 9/16 x 27 9/16 in.)".extractDimensions()

        assertEquals(2, dimensions.size)
        assertEquals(65.0, dimensions[0].value, 0.0)
        assertEquals(70.0, dimensions[1].value, 0.0)
        assertEquals("cm", dimensions[0].unit.unit)
    }
}