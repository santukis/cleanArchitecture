package com.santukis.cleanarchitecture.core.domain.model

import kotlinx.coroutines.flow.Flow

interface UseCase<Params, Result> {

    fun run(params: Params): Flow<Response<Result>>
}