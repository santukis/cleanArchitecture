package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoteArtworkDataSource(private val client: HttpClient) : ArtworkDataSource {

    companion object {
        const val MAX_ITEM_SIZE = 25
    }

    override suspend fun loadArtworks(lastItem: Int): Flow<Response<List<Artwork>>> =
        try {
            flowOf(
                Response.Success(client.artworkService.loadArtworks(
                    apiKey = BuildConfig.API_KEY,
                    fields = mapOf(
                        "ps" to MAX_ITEM_SIZE.toString(),
                        "p" to (lastItem / MAX_ITEM_SIZE).toString()
                    )
                ).items.map { it.toArtwork() })
            )

        } catch (exception: Exception) {
            flowOf(Response.Error(exception))
        }
}