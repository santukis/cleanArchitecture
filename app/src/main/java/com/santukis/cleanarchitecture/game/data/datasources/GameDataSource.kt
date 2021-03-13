package com.santukis.cleanarchitecture.game.data.datasources

import android.util.Size
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.domain.model.GameHistory
import com.santukis.cleanarchitecture.game.domain.model.GameScore
import com.santukis.cleanarchitecture.game.domain.model.Puzzle
import com.santukis.cleanarchitecture.game.domain.model.Question

interface GameDataSource {

    suspend fun loadGameHistory(): Response<GameHistory>

    suspend fun addScore(score: GameScore)

    suspend fun loadQuestion(type: Int): Response<Question> = Response.Error(Exception("Unable to load Question"))

    suspend fun loadPuzzle(puzzleId: String, size: Size): Response<Puzzle> = Response.Error(Exception("Unable to load Puzzle"))

    suspend fun loadPuzzles(): Response<List<Puzzle>> = Response.Error(Exception("Unable to load Puzzles"))
}