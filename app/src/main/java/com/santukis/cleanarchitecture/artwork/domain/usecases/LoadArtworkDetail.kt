package com.santukis.cleanarchitecture.artwork.domain.usecases

import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.FlowUseCase
import kotlinx.coroutines.flow.Flow

class LoadArtworkDetail(private val repository: ArtworkDataSource): FlowUseCase<String, Artwork> {

    override suspend fun flow(params: String): Flow<Artwork> = repository.loadArtworkDetail(params)
}