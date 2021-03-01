package com.santukis.cleanarchitecture.artwork.data.repository

import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.*

class ArtworkRepository(
    private val localDataSource: ArtworkDataSource,
    private val remoteDataSource: ArtworkDataSource
): ArtworkDataSource {

    override suspend fun loadArtworks(collection: Collection, lastItem: Int): Flow<Response<List<Artwork>>> =
        when(lastItem) {
            0 -> loadArtworksFromLocal(collection)
            else -> loadArtworksFromRemote(collection, lastItem)
        }

    private suspend fun loadArtworksFromLocal(collection: Collection): Flow<Response<List<Artwork>>> =
        localDataSource.loadArtworks(collection)
            .flatMapConcat { response ->
                when(response) {
                    is Response.Success -> flowOf(response)
                    else -> loadArtworksFromRemote(collection)
                }
            }

    private suspend fun loadArtworksFromRemote(collection: Collection, lastItem: Int = 0): Flow<Response<List<Artwork>>> =
        flow {
            remoteDataSource.loadArtworks(collection, lastItem)
                .collect { response ->
                    when(response) {
                        is Response.Success -> localDataSource.saveArtworks(response.data).takeIf { it is Response.Error }?.let { emit(it) }
                        is Response.Error -> emit(response)
                    }
                }
        }

    override suspend fun loadArtworkDetail(collection: Collection, artworkId: String): Response<Artwork> {
        val response = localDataSource.loadArtworkDetail(collection, artworkId)

        return when {
            response is Response.Success && response.data.shouldBeUpdated -> loadArtworkDetailFromRemote(collection, response.data)
            else -> response
        }
    }

    private suspend fun loadArtworkDetailFromRemote(collection: Collection, artwork: Artwork): Response<Artwork> =
        when(val response = remoteDataSource.loadArtworkDetail(collection, artwork.id)) {
            is Response.Success -> localDataSource.saveArtwork(response.data)
            else -> Response.Success(artwork)
        }

    override suspend fun loadFavourites(): Flow<Response<List<Artwork>>> =
        localDataSource.loadFavourites()

    override suspend fun toggleFavourite(artworkId: String): Response<Unit> =
        localDataSource.toggleFavourite(artworkId)

    override suspend fun isArtworkFavourite(artworkId: String): Boolean =
        localDataSource.isArtworkFavourite(artworkId)
}