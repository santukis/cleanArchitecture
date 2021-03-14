package com.santukis.cleanarchitecture.game.ui.viewmodels

import android.app.Application
import android.util.Size
import android.view.View
import androidx.lifecycle.*
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.data.datasources.GameDataSource
import com.santukis.cleanarchitecture.game.domain.model.*
import com.santukis.cleanarchitecture.game.ui.views.PuzzleLayout
import org.kodein.di.DI
import org.kodein.di.instance
import kotlin.random.Random

class GameViewModel(application: Application, di: DI) : AndroidViewModel(application), PuzzleLayout.OnPuzzleStateChanged {

    companion object {
        const val QUESTION_SCREEN = 0
        const val ANSWER_SCREEN = 1
    }

    private val executor: Executor by di.instance(tag = "executor", arg = viewModelScope)
    private val gameDataSource: GameDataSource by di.instance("local")

    private val _gameHistory: MutableLiveData<Response<GameHistory>> = MutableLiveData()
    val gameHistory: LiveData<Response<GameHistory>> = _gameHistory

    private val _question: MutableLiveData<Response<Question>> = MutableLiveData()
    val question: LiveData<Response<Question>> = _question

    private val _puzzles: MutableLiveData<Response<List<Puzzle>>> = MutableLiveData()
    val puzzles: LiveData<Response<List<Puzzle>>> = _puzzles

    private val _puzzle: MutableLiveData<Response<Puzzle>> = MutableLiveData()
    val puzzle: LiveData<Response<Puzzle>> = _puzzle

    private var difficulty: Difficulty = Difficulty.Medium

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
        executor.execute {
            _question.postValue(gameDataSource.loadQuestion(Random.nextInt(0, 3)))
            _screen.postValue(QUESTION_SCREEN)
        }
    }

    fun loadPuzzles() {
        executor.execute {
            _puzzles.postValue(Response.Loading())
            _puzzles.postValue(gameDataSource.loadPuzzles())
        }
    }

    fun loadPuzzle(puzzleId: String, difficulty: Difficulty) {
        executor.execute {
            _puzzle.postValue(Response.Loading())
            _puzzle.postValue(gameDataSource.loadPuzzle(puzzleId, difficulty))
        }
    }

    fun loadGameHistory() {
        executor.execute {
            _gameHistory.postValue(gameDataSource.loadGameHistory())
        }
    }

    private fun updateQuestionAnswer(answer: Answer): Question? =
        (_question.value as? Response.Success)?.data?.checkAnswer(answer)

    private fun updateGameHistory(question: Question) {
        executor.execute {
            gameDataSource.addScore(question.getScore())
        }
    }

    override fun onPieceChanged(puzzleId: String, piece: Piece) {
        executor.execute {
            gameDataSource.updatePuzzlePiece(puzzleId, piece)
        }
    }

    override fun onPiecesCreated(puzzleId: String, pieces: List<Piece>) {
        executor.execute {
            gameDataSource.createPuzzlePieces(puzzleId, pieces)
        }
    }
}