package com.santukis.cleanarchitecture.game.fragments.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.fragments.domain.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class GameViewModel(application: Application): AndroidViewModel(application), DIAware {

    override val di: DI by di()

    private val artworkDataSource: ArtworkDataSource by di.instance("local")

    private val _question: MutableLiveData<Response<Question>> = MutableLiveData()
    val question: LiveData<Response<Question>> = _question

    fun loadQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            _question.postValue(artworkDataSource.loadQuestion())
        }
    }

    fun checkAnswer(answer: Artwork) {
        
    }

}