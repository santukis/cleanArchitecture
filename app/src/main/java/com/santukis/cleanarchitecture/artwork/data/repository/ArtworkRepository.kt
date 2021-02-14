package com.santukis.cleanarchitecture.artwork.data.repository

import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.*

class ArtworkRepository(
    private val localDataSource: ArtworkDataSource,
    private val remoteDataSource: ArtworkDataSource
): ArtworkDataSource {

    override suspend fun loadArtworks(lastItem: Int): Flow<Response<List<Artwork>>> =
        when(lastItem) {
            0 -> loadArtworksFromLocal()
            else -> loadArtworksFromRemote(lastItem)
        }

    private suspend fun loadArtworksFromLocal(): Flow<Response<List<Artwork>>> =
        localDataSource.loadArtworks()
            .flatMapConcat { response ->
                when(response) {
                    is Response.Success -> flowOf(response)
                    else -> loadArtworksFromRemote(0)
                }
            }

    private suspend fun loadArtworksFromRemote(lastItem: Int = 0): Flow<Response<List<Artwork>>> =
        flow {
            remoteDataSource.loadArtworks(lastItem)
                .collect { response ->
                    when(response) {
                        is Response.Success -> localDataSource.saveArtworks(response.data)
                        is Response.Error -> emit(response)
                    }
                }
        }

    override suspend fun loadArtworkDetail(artworkId: String): Response<Artwork> {
        val response = localDataSource.loadArtworkDetail(artworkId)

        return when {
            response is Response.Success && response.data.shouldBeUpdated -> loadArtworkDetailFromRemote(response.data)
            else -> response
        }
    }

    private suspend fun loadArtworkDetailFromRemote(artwork: Artwork): Response<Artwork> =
        when(val response = remoteDataSource.loadArtworkDetail(artwork.id)) {
            is Response.Success -> localDataSource.saveArtwork(response.data)
            else -> Response.Success(artwork)
        }
}