package com.santukis.cleanarchitecture.game.domain.model

import android.graphics.Point
import android.util.Size
import androidx.annotation.StringRes
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import java.text.DecimalFormat
import kotlin.random.Random

class GameHistory(
    val titleScore: GameScore.TitleScore = GameScore.TitleScore(),
    val authorScore: GameScore.AuthorScore = GameScore.AuthorScore(),
    val datingScore: GameScore.DatingScore = GameScore.DatingScore()
) {

    fun getTotalScore(): GameScore.TotalScore = GameScore.TotalScore(
        count = titleScore.count + authorScore.count + datingScore.count,
        success = titleScore.success + authorScore.success + datingScore.success
    )

    companion object {
        val EMPTY = GameHistory()
    }
}

sealed class GameScore(
    val count: Int,
    val success: Int
) {

    class TotalScore(count: Int = 0, success: Int = 0): GameScore(count, success)

    class TitleScore(count: Int = 0, success: Int = 0): GameScore(count, success)

    class AuthorScore(count: Int = 0, success: Int = 0): GameScore(count, success)

    class DatingScore(count: Int = 0, success: Int= 0): GameScore(count, success)

    fun getSuccessPercentage(): String = DecimalFormat("#.#%").format(success.toFloat() / (count.takeIf { it > 0 }?.toFloat() ?: 1f))

    fun getQuestionCount(): String = "$count"
}

sealed class Question(
    var answers: List<Answer>,
    @StringRes var title: Int
) {

    private val rightOption: Int = Random.nextInt(0, answers.size.takeIf { it > 0 } ?: 1)

    var successfullyAnswered: Boolean = false

    fun getRightAnswer() = answers.getOrNull(rightOption)

    fun checkAnswer(artwork: Answer): Question {
        successfullyAnswered = getRightAnswer()?.text == artwork.text
        return this
    }

    abstract fun getScore(): GameScore

    class TitleQuestion(answers: List<Answer>): Question(answers, title = R.string.title_question) {
        override fun getScore(): GameScore = GameScore.TitleScore(count = 1, success = successfullyAnswered.toInt())
    }

    class AuthorQuestion(answers: List<Answer>): Question(answers, title = R.string.author_question) {
        override fun getScore(): GameScore = GameScore.AuthorScore(count = 1, success = successfullyAnswered.toInt())
    }

    class DatingQuestion(answers: List<Answer>): Question(answers, title = R.string.dating_question) {
        override fun getScore(): GameScore = GameScore.DatingScore(count = 1, success = successfullyAnswered.toInt())
    }

    protected fun Boolean.toInt(): Int = if (successfullyAnswered) 1 else 0
}

data class Answer(
    var image: String = "",
    var text: String = "",
    var explanation: String = ""
)

data class Puzzle(
    val artwork: Artwork = Artwork.EMPTY,
    val pieces: List<Piece> = emptyList(),
    val size: Size = Size(4, 3)
)

data class Piece(
    val position: Point = Point(),
    val coordinates: Point = Point(),
    var size: Size = Size(0, 0),
    var canMove: Boolean = true
)