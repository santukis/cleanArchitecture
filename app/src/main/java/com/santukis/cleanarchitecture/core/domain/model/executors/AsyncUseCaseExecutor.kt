package com.santukis.cleanarchitecture.core.domain.model.executors

import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.domain.model.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AsyncUseCaseExecutor: Executor, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun <Params, Response> execute(useCase: UseCase<Params, Response>, params: Params, onResult: (Result<Response>) -> Unit) {
        launch(coroutineContext) {
            useCase.run(params)
                .flowOn(Dispatchers.IO)
                .catch { exception -> onResult(Result.failure(exception)) }
                .collect { response -> onResult(response) }
        }
    }
}