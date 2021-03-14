package com.santukis.cleanarchitecture.game.data.local

import androidx.room.Dao
import androidx.room.Query
import com.santukis.cleanarchitecture.core.data.local.BaseDao
import com.santukis.cleanarchitecture.game.domain.model.Difficulty

@Dao
interface PuzzleDao: BaseDao<PuzzleDb> {

    @Query("SELECT * FROM puzzles WHERE puzzles.id = :puzzleId AND difficulty = :difficulty LIMIT 1")
    fun loadPuzzle(puzzleId: String, difficulty: Difficulty): PuzzleDetailDb?

}

@Dao
interface PieceDao: BaseDao<PieceDb>