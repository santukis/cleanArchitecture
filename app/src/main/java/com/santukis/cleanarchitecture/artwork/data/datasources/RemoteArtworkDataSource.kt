package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.data.remote.ArtworkDto
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.data.remote.unwrapCall
import com.santukis.cleanarchitecture.core.domain.model.Response

class RemoteArtworkDataSource(private val client: HttpClient) : ArtworkDataSource {

    companion object {
        const val MAX_ITEM_SIZE = 25
    }

    override suspend fun loadArtworks(lastItem: Int): Response<List<Artwork>> = try {
        val response = client.artworkService.loadArtworks(
            apiKey = BuildConfig.API_KEY,
            fields = mapOf(
                "ps" to MAX_ITEM_SIZE.toString(),
                "p" to (lastItem / MAX_ITEM_SIZE).toString()
            )
        ).unwrapCall(success = { items }, error = { Exception(this) })

        when(response) {
            is Throwable -> Response.Error(response)
            is List<*> -> Response.Success((response as List<ArtworkDto>).map { it.toArtwork() })
            else -> super.loadArtworks(lastItem)
        }

    } catch (exception: Exception) {
        Response.Error(exception)
    }
}