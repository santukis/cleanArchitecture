package com.santukis.cleanarchitecture.game.domain.model

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import kotlin.random.Random

data class Question(
    var options: List<Artwork>,
    var title: String = "What's the title of this artwork",
    var rightOption: Int = Random.nextInt(0, 3)
) {

    fun checkAnswer(artwork: Artwork): Boolean =
        options.getOrNull(rightOption)?.title == artwork.title
}