package com.santukis.cleanarchitecture.game.data.local

import android.util.Size
import androidx.room.Dao
import androidx.room.Query
import com.santukis.cleanarchitecture.core.data.local.BaseDao

@Dao
interface PuzzleDao: BaseDao<PuzzleDb> {

    @Query("SELECT * FROM puzzles WHERE puzzles.id = :puzzleId AND size = :size LIMIT 1")
    fun loadPuzzle(puzzleId: String, size: Size): PuzzleDetailDb?

}

@Dao
interface PieceDao: BaseDao<PieceDb>