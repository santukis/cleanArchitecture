package com.santukis.cleanarchitecture.artwork.data.remote

import org.junit.Assert.*
import org.junit.Test

class ChicagoArtworkTest {

    @Test
    fun extractDimensionsShouldReturnExpectedValuesAreThree() {
        val chicagoArtwork = ChicagoArtwork(dimensions = "160.0 × 120.2 × 56.3 cm (63 × 47 5/16 × 22 3/16 in.)")

        val dimensions = chicagoArtwork.extractDimensions()

        print(dimensions.toString())

        assertEquals(3, dimensions.size)
        assertEquals(160.0, dimensions[0].value, 0.0)
        assertEquals(120.2, dimensions[1].value, 0.0)
        assertEquals(56.3, dimensions[2].value, 0.0)
        assertEquals("cm", dimensions[0].unit.unit)
    }

    @Test
    fun extractDimensionsShouldReturnExpectedValuesWhenDimensionsAreTwo() {
        val chicagoArtwork = ChicagoArtwork(dimensions = "73.6 × 92.3 cm (29 × 36 5/8 in.)")

        val dimensions = chicagoArtwork.extractDimensions()

        print(dimensions.toString())

        assertEquals(2, dimensions.size)
        assertEquals(73.6, dimensions[0].value, 0.0)
        assertEquals(92.3, dimensions[1].value, 0.0)
        assertEquals("cm", dimensions[0].unit.unit)
    }
}