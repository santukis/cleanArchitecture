package com.santukis.cleanarchitecture.game.data.model

import android.graphics.Point
import android.util.Size
import androidx.room.*
import com.santukis.cleanarchitecture.game.domain.model.Piece

@Entity(tableName = "puzzles")
data class PuzzleDb(
    @PrimaryKey
    val id: String = "",
    val size: Size
)

@Entity(
    tableName = "pieces",
    foreignKeys = [
        ForeignKey(
            entity = PuzzleDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class PieceDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val parentId: String = "",
    val position: Point = Point(),
    val coordinates: Point = Point(),
    val size: Size = Size(0, 0),
    val canMove: Boolean = true
)

data class PuzzleDetailDb(
    @Embedded val puzzleDb: PuzzleDb,

    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    ) val pieces: List<Piece>
)