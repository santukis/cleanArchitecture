package com.santukis.cleanarchitecture.game.fragments.domain.model

import com.santukis.cleanarchitecture.artwork.domain.model.Artwork


data class Question(
    var options: List<Artwork>
)