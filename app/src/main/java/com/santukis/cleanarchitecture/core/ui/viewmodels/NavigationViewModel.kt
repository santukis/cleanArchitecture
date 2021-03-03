package com.santukis.cleanarchitecture.core.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class NavigationViewModel(application: Application) : AndroidViewModel(application) {

    private val _destiny: MutableLiveData<Int> = MutableLiveData()
    val destiny: LiveData<Int> = _destiny

    fun navigateTo(destiny: Int) {
        _destiny.postValue(destiny)
    }

    fun navigateUp() {
        _destiny.postValue(-1)
    }
}