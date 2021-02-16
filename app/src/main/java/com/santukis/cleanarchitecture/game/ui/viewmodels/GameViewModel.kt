package com.santukis.cleanarchitecture.game.ui.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.domain.model.Answer
import com.santukis.cleanarchitecture.game.domain.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class GameViewModel(application: Application) : AndroidViewModel(application), DIAware {

    companion object {
        const val QUESTION_SCREEN = 0
        const val ANSWER_SCREEN = 1
    }

    override val di: DI by di()

    private val artworkDataSource: ArtworkDataSource by di.instance("local")

    private val _question: MutableLiveData<Response<Question>> = MutableLiveData()
    val question: LiveData<Response<Question>> = _question

    private val _screen: MutableLiveData<Int> = MutableLiveData()
    val screen: LiveData<Int> = _screen

    fun loadQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            _question.postValue(artworkDataSource.loadQuestion())
            _screen.postValue(QUESTION_SCREEN)
        }
    }

    val onAnswerClick: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, item: Any) {
            if (item is Answer) {
                updateQuestionAnswer(item)
                _screen.postValue(ANSWER_SCREEN)
            }
        }
    }

    private fun updateQuestionAnswer(answer: Answer) {
        (_question.value as? Response.Success)?.data?.let { question ->
            question.checkAnswer(answer)
            _question.postValue(Response.Success(question))
        }
    }
}