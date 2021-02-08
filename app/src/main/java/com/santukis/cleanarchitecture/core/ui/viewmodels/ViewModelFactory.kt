package com.santukis.cleanarchitecture.core.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instanceOrNull

class ViewModelFactory(private val di: DI) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return di.direct.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as? T ?: modelClass.newInstance()
    }
}