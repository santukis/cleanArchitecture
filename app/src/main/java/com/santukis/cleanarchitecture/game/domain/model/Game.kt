package com.santukis.cleanarchitecture.game.domain.model

import androidx.annotation.StringRes
import com.santukis.cleanarchitecture.R
import kotlin.random.Random

data class Game(
    var score: Int = 0
)

sealed class Question(
    var answers: List<Answer>,
    @StringRes var title: Int,
    var successfullyAnswered: Boolean = false
) {

    var rightOption: Int = Random.nextInt(0, 3)
        private set

    fun checkAnswer(artwork: Answer) {
        successfullyAnswered = answers.getOrNull(rightOption)?.text == artwork.text
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