package com.santukis.cleanarchitecture.artwork.ui.viewmodels

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.ArtworkCollection
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
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

    val artworks: MutableLiveData<Response<List<Artwork>>> = MutableLiveData()
    var isFavourite: ObservableBoolean = ObservableBoolean()

    init {
        loadFavourites()
    }

    fun loadCollections(): LiveData<Response<List<ArtworkCollection>>> {
        val collections: MutableLiveData<Response<List<ArtworkCollection>>> = MutableLiveData()
        executor.execute {
            artworkDataSource.loadCollections()
                .onStart { collections.postValue(Response.Loading()) }
                .catch { exception -> collections.postValue(Response.Error(exception)) }
                .collect { collections.postValue(it) }
        }
        return collections
    }

    fun loadArtworks(collection: Collection, lastVisible: Int = 0) {
        executor.execute {
            artworkDataSource.loadArtworks(collection, lastVisible)
                    .onStart { artworks.postValue(Response.Loading()) }
                    .catch { exception -> artworks.postValue(Response.Error(exception)) }
                    .collect { artworks.postValue(it) }
        }
    }

    fun loadArtworkDetail(artworkId: String): LiveData<Response<Artwork>> {
        val artwork: MutableLiveData<Response<Artwork>> = MutableLiveData()
        executor.execute {
            artwork.postValue(Response.Loading())
            artwork.postValue(artworkDataSource.loadArtworkDetail(artworkId = artworkId))
            isFavourite.set(artworkDataSource.isArtworkFavourite(artworkId))
        }
        return artwork
    }

    fun loadFavourites(): LiveData<Response<List<Artwork>>> {
        val favourites: MutableLiveData<Response<List<Artwork>>> = MutableLiveData()
        executor.execute {
            artworkDataSource.loadFavourites()
                    .onStart { favourites.postValue(Response.Loading()) }
                    .catch { exception -> favourites.postValue(Response.Error(exception)) }
                    .collect { favourites.postValue(it) }
        }
        return favourites
    }

    fun toggleFavourite(artworkId: String) {
        executor.execute {
            artworkDataSource.toggleFavourite(artworkId)
            isFavourite.set(artworkDataSource.isArtworkFavourite(artworkId))
        }
    }

    fun showMoreInfo(url: String?) {

    }
}