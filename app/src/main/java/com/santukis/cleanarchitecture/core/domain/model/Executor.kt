package com.santukis.cleanarchitecture.core.domain.model

interface Executor {

    fun <Params, Result> execute(useCase: UseCase<Params, Result>,
                                 params: Params,
                                 onResult: (Response<Result>) -> Unit = {})


    suspend fun <Params, Result> execute(useCase: UseCase<Params, Result>,
                                         params: Params): Response<Result>
}