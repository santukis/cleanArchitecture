package com.santukis.cleanarchitecture.utils

import com.santukis.cleanarchitecture.core.domain.model.Executor
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class SyncExecutor: Executor {

    override fun execute(block: suspend () -> Unit) {
        runBlocking {
            block()
        }
    }
}