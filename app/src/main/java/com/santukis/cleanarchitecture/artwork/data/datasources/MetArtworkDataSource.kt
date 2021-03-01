package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MetArtworkDataSource(private val client: HttpClient = HttpClient(host = BuildConfig.MET_END_POINT)): ArtworkDataSource {

    companion object {
        const val MAX_ITEM_SIZE = 20
        const val DEPARTMENTS = "11|21" // 9 -> "Drawings and Prints" 11 -> "European Paintings" 21 -> "Modern Art"
    }

    override suspend fun loadArtworks(collection: Collection, lastItem: Int): Flow<Response<List<Artwork>>> =
        flow {
            try {
                val ids = client.artworkService.loadMetArtworks(
                    departmentId = DEPARTMENTS
                ).ids

                when(ids.isNullOrEmpty()) {
                    true -> emit(Response.Error<List<Artwork>>(Exception("No more items")))
                    false -> emit(Response.Success(loadArtworks(ids = ids.subList(lastItem, lastItem + MAX_ITEM_SIZE))))
                }
            } catch (exception: Exception) {
                emit(Response.Error<List<Artwork>>(exception))
            }
        }

    private suspend fun loadArtworks(ids: List<Int>): List<Artwork> {
        val artworks = mutableListOf<Artwork>()

        ids.forEach { id ->
            when(val response = loadArtworkDetail(Collection.Met, id.toString())) {
                is Response.Success -> artworks.add(response.data)
                is Response.Error -> {}
            }
        }

        return artworks.filter { it.image.isNotEmpty() }
    }

    override suspend fun loadArtworkDetail(collection: Collection, artworkId: String): Response<Artwork> =
        try {
            Response.Success(client.artworkService.loadMetArtworkDetail(artworkId).toArtwork().apply { shouldBeUpdated = false })

        } catch (exception: Exception) {
            exception.printStackTrace()
            Response.Error(exception)
    }
}