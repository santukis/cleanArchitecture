package com.santukis.cleanarchitecture.core.domain.model

sealed class Response<Item> {

    data class Success<Item>(val data: Item): Response<Item>()

    data class Error<Item>(val error: Throwable): Response<Item>()

    fun isSuccessful() = this is Success
}