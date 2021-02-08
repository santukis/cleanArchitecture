package com.santukis.cleanarchitecture.artwork.domain.usecases

import com.santukis.cleanarchitecture.artwork.data.repository.ArtworkRepository
import com.santukis.cleanarchitecture.core.domain.model.UseCase

class LoadMoreArtworks(private val repository: ArtworkRepository): UseCase<Int, Unit> {

    override suspend fun run(params: Int) {
        repository.checkArtworks(params)
    }
}