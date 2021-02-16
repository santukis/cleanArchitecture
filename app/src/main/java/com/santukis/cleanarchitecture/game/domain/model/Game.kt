package com.santukis.cleanarchitecture.game.domain.model

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import kotlin.random.Random

data class Game(
    var score: Int = 0
)

data class Question(
    var options: List<Artwork>,
    var title: String = "What's the title of this artwork",
    var isRightAnswer: Boolean = false
) {

    var rightOption: Int = Random.nextInt(0, 3)
        private set

    fun checkAnswer(artwork: Artwork) {
        isRightAnswer = options.getOrNull(rightOption)?.title == artwork.title
    }
}