package com.santukis.cleanarchitecture.core.domain.model.executors

import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.domain.model.FlowUseCase
import com.santukis.cleanarchitecture.core.domain.model.UseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class AsyncUseCaseExecutor: Executor, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun <Params, Result> execute(useCase: FlowUseCase<Params, Result>, params: Params, onResult: (Response<Result>) -> Unit) {
        launch(coroutineContext) {
            useCase.flow(params)
                .flowOn(Dispatchers.IO)
                .onStart { onResult(Response.Loading()) }
                .onEmpty { onResult(Response.Error(Exception("No Results"))) }
                .catch { exception -> onResult(Response.Error(exception)) }
                .collect { items -> onResult(Response.Success(items)) }
        }
    }

    override suspend fun <Params, Result> execute(useCase: UseCase<Params, Result>, params: Params): Result {
        return useCase.run(params)
    }
}