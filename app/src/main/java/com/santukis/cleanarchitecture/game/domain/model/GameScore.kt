package com.santukis.cleanarchitecture.game.domain.model

import androidx.annotation.StringRes
import com.santukis.cleanarchitecture.R
import kotlin.random.Random


class GameHistory(
    val totalScore: GameScore.TotalScore = GameScore.TotalScore(),
    val titleScore: GameScore.TitleScore = GameScore.TitleScore(),
    val authorScore: GameScore.AuthorScore = GameScore.AuthorScore(),
    val datingScore: GameScore.DatingScore = GameScore.DatingScore()
) {
    companion object {
        val EMPTY = GameHistory()
    }
}

sealed class GameScore(
    val count: Int,
    val success: Int
) {

    class TotalScore(score: Int = 0, success: Int = 0): GameScore(score, success)

    class TitleScore(score: Int = 0, success: Int = 0): GameScore(score, success)

    class AuthorScore(score: Int = 0, success: Int = 0): GameScore(score, success)

    class DatingScore(score: Int = 0, success: Int= 0): GameScore(score, success)

    fun getSuccessPercentage(): String = "${ (count / (success.takeIf { it > 0 } ?: 1)) * 100 }%"

    fun getQuestionCount(): String = "$count"
}

sealed class Question(
    var answers: List<Answer>,
    @StringRes var title: Int
) {

    private val rightOption: Int = Random.nextInt(0, answers.size)

    var successfullyAnswered: Boolean = false

    fun getRightAnswer() = answers[rightOption]

    fun checkAnswer(artwork: Answer) {
        successfullyAnswered = getRightAnswer().text == artwork.text
    }

    class TitleQuestion(answers: List<Answer>): Question(answers, title = R.string.title_question)

    class AuthorQuestion(answers: List<Answer>): Question(answers, title = R.string.author_question)

    class DatingQuestion(answers: List<Answer>): Question(answers, title = R.string.dating_question)
}

data class Answer(
    var image: String = "",
    var text: String = "",
    var explanation: String = ""
)