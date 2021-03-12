package com.santukis.cleanarchitecture.game.data.local

import androidx.room.Dao
import com.santukis.cleanarchitecture.core.data.local.BaseDao

@Dao
interface PuzzleDao: BaseDao<PuzzleDb>

@Dao
interface PieceDao: BaseDao<PieceDb>