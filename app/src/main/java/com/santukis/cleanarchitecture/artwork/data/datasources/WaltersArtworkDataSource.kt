package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.core.data.datasources.PagingDataSource
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WaltersArtworkDataSource(
    private val client: HttpClient = HttpClient(host = BuildConfig.WALTERS_END_POINT),
    private val pagingDataSource: PagingDataSource
): ArtworkDataSource {

    companion object {
        const val MAX_ITEM_SIZE = 50
    }

    override suspend fun loadArtworks(collection: Collection, lastItem: Int): Flow<Response<List<Artwork>>> =
        flow {
            try {
                val artworks = client.artworkService.loadWaltersArtworks(
                    apiKey = BuildConfig.WALTERS_API_KEY,
                    page = pagingDataSource.getNextPage(collection),
                    size = MAX_ITEM_SIZE
                ).data.filter { it.image != null }

                when {
                    artworks.isNullOrEmpty() -> super.loadArtworks(collection, lastItem)
                    else -> emit(Response.Success(artworks.map { it.toArtwork() }))
                }
            } catch (exception: Exception) {
                emit(Response.Error(exception))
            }
        }
}