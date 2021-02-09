package com.santukis.cleanarchitecture.artwork.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
    private val loadArtworks: FlowUseCase<Unit, List<Artwork>> by di.instance("loadArtworks")
    private val refreshArtworks: UseCase<Int, Unit> by di.instance("refreshArtworks")
    private val loadArtworkDetail: FlowUseCase<String, Artwork> by di.instance("loadArtworkDetail")

    private val _artworks: MutableLiveData<Response<List<Artwork>>> = MutableLiveData()
    val artworks: LiveData<Response<List<Artwork>>> = _artworks

    private val _artwork: MutableLiveData<Response<Artwork>> = MutableLiveData()
    val artwork: LiveData<Response<Artwork>> = _artwork

    fun loadArtworks() {
        executor.execute(loadArtworks, Unit) { response ->
            _artworks.postValue(response)
        }
    }

    fun notifyLastVisible(lastVisible: Int) {
        _artworks.postValue(Response.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            executor.execute(refreshArtworks, lastVisible)
        }
    }

    fun loadArtworkDetail(artworkId: String) {
        executor.execute(loadArtworkDetail, artworkId) { response ->
            _artwork.postValue(response)
        }
    }
}