package com.santukis.cleanarchitecture.artwork.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class ArtworkViewModel(application: Application): AndroidViewModel(application), DIAware {

    override val di: DI by di()

    private val artworkDataSource: ArtworkDataSource by di.instance("repository")

    val artwork: MutableLiveData<Response<Artwork>> = MutableLiveData()
    val artworks: MutableLiveData<Response<List<Artwork>>> = MutableLiveData()

    init {
        loadArtworks(0)
    }

    fun loadArtworks(lastVisible: Int = 0) {
        viewModelScope.launch {
            artworkDataSource.loadArtworks(lastVisible)
                .flowOn(Dispatchers.IO)
                .onStart { artworks.postValue(Response.Loading()) }
                .catch { exception -> artworks.postValue(Response.Error(exception)) }
                .collect { artworks.postValue(it) }
        }
    }

    fun loadArtworkDetail(artworkId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            artwork.postValue(artworkDataSource.loadArtworkDetail(artworkId))
        }
    }
}