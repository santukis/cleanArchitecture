package com.santukis.cleanarchitecture.game.ui.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.data.datasources.GameDataSource
import com.santukis.cleanarchitecture.game.domain.model.Answer
import com.santukis.cleanarchitecture.game.domain.model.GameHistory
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
    private val gameDataSource: GameDataSource by di.instance("local")

    private val _gameHistory: MutableLiveData<Response<GameHistory>> = MutableLiveData()
    val gameHistory: LiveData<Response<GameHistory>> = _gameHistory

    private val _question: MutableLiveData<Response<Question>> = MutableLiveData()
    val question: LiveData<Response<Question>> = _question

    private val _screen: MutableLiveData<Int> = MutableLiveData()
    val screen: LiveData<Int> = _screen

    val onAnswerClick: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, item: Any) {
            if (item is Answer) {
                updateQuestionAnswer(item)?.let { updatedQuestion ->
                    updateGameHistory(updatedQuestion)
                    _question.postValue(Response.Success(updatedQuestion))
                }

                _screen.postValue(ANSWER_SCREEN)
            }
        }
    }

    fun loadQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            _question.postValue(artworkDataSource.loadQuestion())
            _screen.postValue(QUESTION_SCREEN)
        }
    }

    fun loadGameHistory() {
        viewModelScope.launch {
            _gameHistory.postValue(gameDataSource.loadGameHistory())
        }
    }

    private fun updateQuestionAnswer(answer: Answer): Question? =
        (_question.value as? Response.Success)?.data?.checkAnswer(answer)

    private fun updateGameHistory(question: Question) {
        viewModelScope.launch {
            gameDataSource.addScore(question.getScore())
        }
    }
}