package com.santukis.cleanarchitecture.core.domain.model

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<Params, Result> {

    suspend fun flow(params: Params): Flow<Result>
}

interface UseCase<Params, Result> {

    suspend fun run(params: Params): Result
}