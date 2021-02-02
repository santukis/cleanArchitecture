package com.santukis.cleanarchitecture.core.model

sealed class Response<Item> {

    data class Success<Item>(val data: Item): Response<Item>()

    data class Error<Item>(val error: Throwable): Response<Item>()
}