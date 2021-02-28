package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class RemoteArtworkDataSource(
    private val rijksmuseumArtworkDataSource: ArtworkDataSource,
    private val metArtworkDataSource: ArtworkDataSource
): ArtworkDataSource {

    override suspend fun loadArtworks(collection: Collection, lastItem: Int): Flow<Response<List<Artwork>>> =
        when(collection) {
            Collection.Rijksmuseum -> rijksmuseumArtworkDataSource.loadArtworks(collection, lastItem)
            Collection.Met -> metArtworkDataSource.loadArtworks(collection, lastItem)
            else -> emptyFlow()
        }

    override suspend fun loadArtworkDetail(collection: Collection, artworkId: String): Response<Artwork> =
        when(collection) {
            Collection.Rijksmuseum -> rijksmuseumArtworkDataSource.loadArtworkDetail(collection, artworkId)
            else -> super.loadArtworkDetail(collection, artworkId)
    }
}