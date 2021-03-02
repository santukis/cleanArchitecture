package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.ArtworkCollection
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.*

class RemoteArtworkDataSource(
    private val rijksmuseumArtworkDataSource: ArtworkDataSource,
    private val metArtworkDataSource: ArtworkDataSource,
    private val chicagoArtworkDataSource: ArtworkDataSource,
    private val clevelandArtworkDataSource: ArtworkDataSource,
    private val waltersArtworkDataSource: ArtworkDataSource,
    private val hardvardArtworkDataSource: ArtworkDataSource
): ArtworkDataSource {

    override suspend fun loadCollections(): Flow<Response<List<ArtworkCollection>>> =
        flow {
            Collection.values().forEach { collection ->
                loadArtworks(collection, 0)
                    .collect { response ->
                        when(response) {
                            is Response.Success -> emit(Response.Success(listOf(ArtworkCollection(collection, response.data))))
                            is Response.Error -> emit(Response.Error<List<ArtworkCollection>>(response.error))
                        }
                    }
            }
        }

    override suspend fun loadArtworks(collection: Collection, lastItem: Int): Flow<Response<List<Artwork>>> =
        when(collection) {
            Collection.Rijksmuseum -> rijksmuseumArtworkDataSource.loadArtworks(collection, lastItem)
            Collection.Met -> metArtworkDataSource.loadArtworks(collection, lastItem)
            Collection.Chicago -> chicagoArtworkDataSource.loadArtworks(collection, lastItem)
            Collection.Cleveland -> clevelandArtworkDataSource.loadArtworks(collection, lastItem)
            Collection.Walters -> waltersArtworkDataSource.loadArtworks(collection, lastItem)
            Collection.Hardvard -> hardvardArtworkDataSource.loadArtworks(collection, lastItem)
            else -> emptyFlow()
        }

    override suspend fun loadArtworkDetail(collection: Collection, artworkId: String): Response<Artwork> =
        when(collection) {
            Collection.Rijksmuseum -> rijksmuseumArtworkDataSource.loadArtworkDetail(collection, artworkId)
            else -> super.loadArtworkDetail(collection, artworkId)
    }
}