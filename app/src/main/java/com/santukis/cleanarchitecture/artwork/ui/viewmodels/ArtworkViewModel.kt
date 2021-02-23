package com.santukis.cleanarchitecture.artwork.ui.viewmodels

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import org.kodein.di.DI
import org.kodein.di.instance

class ArtworkViewModel(application: Application, di: DI): AndroidViewModel(application) {

    private val executor: Executor by di.instance(tag = "executor", arg = viewModelScope)
    private val artworkDataSource: ArtworkDataSource by di.instance(tag = "repository")

    val artwork: MutableLiveData<Response<Artwork>> = MutableLiveData()
    val artworks: MutableLiveData<Response<List<Artwork>>> = MutableLiveData()
    val favourites: MutableLiveData<Response<List<Artwork>>> = MutableLiveData()
    var isFavourite: ObservableBoolean = ObservableBoolean()

    init {
        loadArtworks()
        loadFavourites()
    }

    fun loadArtworks(lastVisible: Int = 0) {
        executor.execute {
            artworkDataSource.loadArtworks(lastVisible)
                    .onStart { artworks.postValue(Response.Loading()) }
                    .catch { exception -> artworks.postValue(Response.Error(exception)) }
                    .collect { artworks.postValue(it) }
        }
    }

    fun loadArtworkDetail(artworkId: String) {
        executor.execute {
            artwork.postValue(Response.Loading())
            artwork.postValue(artworkDataSource.loadArtworkDetail(artworkId))
            isFavourite.set(artworkDataSource.isArtworkFavourite(artworkId))
        }
    }

    @VisibleForTesting
    fun loadFavourites() {
        executor.execute {
            artworkDataSource.loadFavourites()
                    .onStart { favourites.postValue(Response.Loading()) }
                    .catch { exception -> favourites.postValue(Response.Error(exception)) }
                    .collect { favourites.postValue(it) }
        }
    }

    fun toggleFavourite(artworkId: String) {
        executor.execute {
            artworkDataSource.toggleFavourite(artworkId)
            isFavourite.set(artworkDataSource.isArtworkFavourite(artworkId))
        }
    }
}