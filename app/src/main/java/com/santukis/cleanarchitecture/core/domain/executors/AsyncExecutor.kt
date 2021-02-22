package com.santukis.cleanarchitecture.core.domain.executors

import com.santukis.cleanarchitecture.core.domain.model.Executor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AsyncExecutor(private val scope: CoroutineScope): Executor {

    override fun execute(block: suspend () -> Unit) {
        scope.launch(Dispatchers.IO) { block() }
    }
}