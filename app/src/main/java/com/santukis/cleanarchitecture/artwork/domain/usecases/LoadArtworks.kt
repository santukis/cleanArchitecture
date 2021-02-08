package com.santukis.cleanarchitecture.artwork.domain.usecases

import com.santukis.cleanarchitecture.artwork.data.repository.ArtworkRepository
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.FlowUseCase
import kotlinx.coroutines.flow.Flow

class LoadArtworks(private val repository: ArtworkRepository): FlowUseCase<Unit, List<Artwork>> {

    override suspend fun flow(params: Unit): Flow<List<Artwork>> = repository.loadArtworks()
}