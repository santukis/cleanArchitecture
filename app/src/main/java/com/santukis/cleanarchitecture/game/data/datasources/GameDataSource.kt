package com.santukis.cleanarchitecture.game.data.datasources

import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.domain.model.*

interface GameDataSource {

    suspend fun loadGameHistory(): Response<GameHistory>

    suspend fun addScore(score: GameScore)

    suspend fun loadQuestion(type: Int): Response<Question> = Response.Error(Exception("Unable to load Question"))

    suspend fun loadPuzzle(puzzleId: String, difficulty: Difficulty): Response<Puzzle> = Response.Error(Exception("Unable to load Puzzle"))

    suspend fun loadPuzzles(): Response<List<Puzzle>> = Response.Error(Exception("Unable to load Puzzles"))

    suspend fun createPuzzlePieces(puzzleId: String, pieces: List<Piece>)

    suspend fun updatePuzzlePiece(puzzleId: String, piece: Piece)

    suspend fun updatePuzzle(puzzle: Puzzle)
}