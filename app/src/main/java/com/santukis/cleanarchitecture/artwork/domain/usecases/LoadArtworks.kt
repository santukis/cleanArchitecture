package com.santukis.cleanarchitecture.artwork.domain.usecases

import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.FlowUseCase
import kotlinx.coroutines.flow.Flow

class LoadArtworks(private val repository: ArtworkDataSource): FlowUseCase<Int, List<Artwork>> {

    override suspend fun flow(params: Int): Flow<List<Artwork>> = repository.loadArtworks(params)
}