package com.santukis.cleanarchitecture.core.domain.model

interface Executor {

    fun <Params, Response> execute(useCase: UseCase<Params, Response>,
                                 params: Params,
                                 onResult: (Result<Response>) -> Unit = {})
}