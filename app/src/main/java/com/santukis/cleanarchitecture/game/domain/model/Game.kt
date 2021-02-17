package com.santukis.cleanarchitecture.game.domain.model

import androidx.annotation.StringRes
import com.santukis.cleanarchitecture.R
import org.threeten.bp.LocalDateTime
import kotlin.random.Random
import kotlin.reflect.KClass

data class Game(
    var score: Int = 0,
    var date: LocalDateTime = LocalDateTime.now()
) {

    fun getPercentageScore(): String = ""

    fun getQuestionCount(): String = ""

    fun getQuestionCount(type: KClass<out Question>): String = ""

    fun getPercentageScore(type: KClass<out Question>): String = ""
}

sealed class Question(
    var answers: List<Answer>,
    @StringRes var title: Int,
    var successfullyAnswered: Boolean = false
) {

    private val rightOption: Int = Random.nextInt(0, answers.size)

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