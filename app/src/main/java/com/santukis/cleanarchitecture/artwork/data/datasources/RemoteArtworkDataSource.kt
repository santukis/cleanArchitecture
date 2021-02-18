package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.data.mappers.toArtwork
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.*

class RemoteArtworkDataSource(private val client: HttpClient) : ArtworkDataSource {

    companion object {
        const val MAX_ITEM_SIZE = 20
    }

    override suspend fun loadArtworks(lastItem: Int): Flow<Response<List<Artwork>>> =
        flow {
            try {
                val artworks = client.artworkService.loadArtworks(
                    apiKey = BuildConfig.API_KEY,
                    fields = mapOf("ps" to MAX_ITEM_SIZE.toString(), "p" to (((lastItem + 1) / MAX_ITEM_SIZE) + 1).toString())
                ).items.map { it.toArtwork() }

                when (artworks.isNullOrEmpty()) {
                    true -> emit(Response.Error<List<Artwork>>(Exception("No more items")))
                    else -> emit(Response.Success(artworks))
                }

            } catch (exception: Exception) {
                emit(Response.Error<List<Artwork>>(exception))
            }
        }

    override suspend fun loadArtworkDetail(artworkId: String): Response<Artwork> = try {
        Response.Success(
            client.artworkService.loadArtworkDetail(
                apiKey = BuildConfig.API_KEY,
                artworkId = artworkId
            ).item.toArtwork()
        )

    } catch (exception: Exception) {
        Response.Error(exception)
    }
}