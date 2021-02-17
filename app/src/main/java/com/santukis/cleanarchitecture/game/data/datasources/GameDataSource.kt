package com.santukis.cleanarchitecture.game.data.datasources

import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.domain.model.GameHistory
import com.santukis.cleanarchitecture.game.domain.model.GameScore

interface GameDataSource {

    suspend fun loadGameHistory(): Response<GameHistory>

    suspend fun addScore(score: GameScore)

}