package com.santukis.cleanarchitecture.core.data.remote

import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.data.remote.ArtworkService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient(host: String) {

    private val client: OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
            })
            .build()

    private val jsonConverter = Moshi.Builder().build()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(host)
            .addConverterFactory(MoshiConverterFactory.create(jsonConverter))
            .build()

    val artworkService: ArtworkService = retrofit.create(ArtworkService::class.java)
}

inline fun <Item, Success> Response<Item>.unwrapResponse(success: Item.() -> Success?, error: String.() -> Throwable) =
    try {
        when(isSuccessful) {
            true -> body()?.success()
            false -> message().error() ?: "Unknown error".error()
        }

    } catch (exception: Exception) {
        exception.message?.error() ?: "Unknown error".error()
    }