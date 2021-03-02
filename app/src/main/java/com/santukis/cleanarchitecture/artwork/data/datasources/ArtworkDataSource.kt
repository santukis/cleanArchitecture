package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.ArtworkCollection
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface ArtworkDataSource {

    suspend fun loadArtworks(collection: Collection, lastItem: Int = 0): Flow<Response<List<Artwork>>> = emptyFlow()

    suspend fun saveArtworks(artworks: List<Artwork>): Response<List<Artwork>> = Response.Error(Exception("Unable to save Artworks"))

    suspend fun loadArtworkDetail(collection: Collection = Collection.Unknown, artworkId: String): Response<Artwork> = Response.Error(Exception("Unable to load Artwork"))

    suspend fun saveArtwork(artwork: Artwork): Response<Artwork> = Response.Error(Exception("Unable to save Artwork"))

    suspend fun loadFavourites(): Flow<Response<List<Artwork>>> = emptyFlow()

    suspend fun toggleFavourite(artworkId: String): Response<Unit> = Response.Error(Exception("Unable to toggle Favourite status"))

    suspend fun isArtworkFavourite(artworkId: String): Boolean = false

    suspend fun loadCollections(): Flow<Response<List<ArtworkCollection>>> = emptyFlow()

    suspend fun saveCollections(collections: List<ArtworkCollection>): Response<List<ArtworkCollection>> = Response.Error(Exception("Unable to save Artworks"))
}