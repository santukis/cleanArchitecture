package com.santukis.cleanarchitecture.core.domain.model

import org.junit.Assert.*
import org.junit.Test

class ResponseTest {

    @Test
    fun whenResponseIsSuccessDataShouldBeAvailable() {
        when(val expectedResponse = Response.Success("Expected Response")) {
            is Response.Success -> assertEquals("Expected Response", expectedResponse.data)
            is Response.Error<*> -> fail()
        }
    }

    @Test
    fun whenResponseIsErrorErrorFieldShouldBeAvailable() {
        when(val expectedResponse = Response.Error<String>(Exception("Expected Error"))) {
            is Response.Success<*> -> fail()
            is Response.Error<String> -> assertEquals("Expected Error", expectedResponse.error.message)
        }
    }

    @Test
    fun isSuccessfulShouldReturnTrueWhenResponseIsSuccessful() {
        val response = Response.Success("Expected Response")

        assertTrue(response.isSuccessful())
    }

    @Test
    fun isSuccessfulShouldReturnFalseWhenResponseIsError() {
        val response = Response.Error<String>(Exception("Expected Response"))

        assertFalse(response.isSuccessful())
    }
}