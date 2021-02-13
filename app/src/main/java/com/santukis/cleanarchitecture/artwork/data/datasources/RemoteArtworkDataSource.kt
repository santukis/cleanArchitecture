package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.data.mappers.toArtwork
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import kotlinx.coroutines.flow.*

class RemoteArtworkDataSource(private val client: HttpClient) : ArtworkDataSource {

    companion object {
        const val MAX_ITEM_SIZE = 20
    }

    override suspend fun loadArtworks(lastItem: Int): Flow<List<Artwork>>  =
        flowOf(
            try {
                client.artworkService.loadArtworks(
                    apiKey = BuildConfig.API_KEY,
                    fields = mapOf("ps" to MAX_ITEM_SIZE.toString(), "p" to (((lastItem + 1) / MAX_ITEM_SIZE) + 1).toString())
                ).items.map { it.toArtwork() }

            } catch (exception: Exception) {
                throw exception
            }
        )

    override suspend fun loadArtworkDetail(artworkId: String): Flow<Artwork> =
        flowOf(
            client.artworkService.loadArtworkDetail(
                apiKey = BuildConfig.API_KEY,
                artworkId = artworkId
            ).item.toArtwork()
        )
}