package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.core.data.datasources.PagingDataSource
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.*

class RijksMuseumArtworkDataSource(
    private val client: HttpClient = HttpClient(host = BuildConfig.RIJKSMUSEUM_END_POINT),
    private val pagingDataSource: PagingDataSource
) : ArtworkDataSource {

    companion object {
        const val MAX_ITEM_SIZE = 50
    }

    override suspend fun loadArtworks(collection: Collection, lastItem: Int): Flow<Response<List<Artwork>>> =
        flow {
            try {
                val artworks = client.artworkService.loadRijksMuseumArtworks(
                    apiKey = BuildConfig.RIJKSMUSEUM_API_KEY,
                    fields = mapOf("ps" to MAX_ITEM_SIZE.toString(), "p" to pagingDataSource.getNextPage(collection).toString())
                ).items.map { it.toArtwork().apply { shouldBeUpdated = true } }

                when (artworks.isNullOrEmpty()) {
                    true -> emit(Response.Error(Exception("No more items")))
                    else -> emit(Response.Success(artworks))
                }

            } catch (exception: Exception) {
                emit(Response.Error(exception))
            }
        }

    override suspend fun loadArtworkDetail(collection: Collection, artworkId: String): Response<Artwork> = try {
        Response.Success(
            client.artworkService.loadRijksMuseumArtworkDetail(
                apiKey = BuildConfig.RIJKSMUSEUM_API_KEY,
                artworkId = artworkId
            ).item.toArtwork().apply { shouldBeUpdated = false }
        )

    } catch (exception: Exception) {
        Response.Error(exception)
    }
}