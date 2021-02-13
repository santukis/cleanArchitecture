package com.santukis.cleanarchitecture.artwork.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.domain.model.FlowUseCase
import com.santukis.cleanarchitecture.core.domain.model.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class ArtworkViewModel(application: Application): AndroidViewModel(application), DIAware {

    override val di: DI by di()

    private val executor: Executor by di.instance("asyncExecutor")
    private val loadArtworks: FlowUseCase<Int, List<Artwork>> by di.instance("loadArtworks")
    private val loadArtworkDetail: FlowUseCase<String, Artwork> by di.instance("loadArtworkDetail")

    val artworks: MutableLiveData<Response<List<Artwork>>> = MutableLiveData()
    val artwork: MutableLiveData<Response<Artwork>> = MutableLiveData()

    fun loadArtworks(lastVisible: Int) {
        executor.execute(loadArtworks, lastVisible) { response ->
            artworks.postValue(response)
        }
    }

    fun loadArtworkDetail(artworkId: String) {
        executor.execute(loadArtworkDetail, artworkId) { response ->
            artwork.postValue(response)
        }
    }
}