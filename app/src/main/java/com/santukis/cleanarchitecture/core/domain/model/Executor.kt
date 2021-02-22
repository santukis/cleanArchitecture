package com.santukis.cleanarchitecture.core.domain.model


interface Executor {

    fun execute(block: suspend () -> Unit = {})
}