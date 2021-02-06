package com.santukis.cleanarchitecture.core.domain.model.executors

import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.domain.model.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AsyncUseCaseExecutor: Executor, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun <Params, Result> execute(useCase: UseCase<Params, Result>, params: Params, onResult: (Response<Result>) -> Unit) {
        launch(coroutineContext) {
            useCase.run(params)
                .flowOn(Dispatchers.IO)
                .catch { exception -> onResult(Response.Error(exception)) }
                .collect { response -> onResult(response) }
        }
    }

    override suspend fun <Params, Result> execute(useCase: UseCase<Params, Result>, params: Params): Response<Result> {
        return withContext(coroutineContext) {
            useCase.run(params)
                .flowOn(Dispatchers.IO)
                .catch { exception -> Response.Error<Result>(exception) }
                .single()
        }
    }
}