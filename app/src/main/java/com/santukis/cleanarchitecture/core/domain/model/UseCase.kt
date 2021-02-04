package com.santukis.cleanarchitecture.core.domain.model

import kotlinx.coroutines.flow.Flow

interface UseCase<Params, Response> {

    fun run(params: Params): Flow<Result<Response>>
}