package com.santukis.cleanarchitecture.game.data.local

import android.graphics.Point
import android.util.Size
import androidx.room.*
import com.santukis.cleanarchitecture.game.domain.model.Difficulty
import com.santukis.cleanarchitecture.game.domain.model.Piece
import com.santukis.cleanarchitecture.game.domain.model.Puzzle

@Entity(tableName = "puzzles")
data class PuzzleDb(
    @PrimaryKey
    val id: String = "",
    val image: String = "",
    val difficulty: Difficulty,
    val scaleFactor: Float = 1f
)

@Entity(
    tableName = "pieces",
    primaryKeys = ["coordinates", "parentId"],
    foreignKeys = [
        ForeignKey(
            entity = PuzzleDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.NO_ACTION
        )
    ]
)
data class PieceDb(
    val parentId: String = "",
    val cell: Point = Point(),
    val position: Point = Point(),
    val coordinates: Point = Point(),
    val size: Size = Size(0, 0),
    val canMove: Boolean = true
) {
    fun toPiece() =
        Piece(
            cell = cell,
            position = position,
            coordinates = coordinates,
            size = size,
            canMove = canMove
        )
}

data class PuzzleDetailDb(
    @Embedded val puzzleDb: PuzzleDb,

    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    ) val pieces: List<PieceDb>
) {
    fun toPuzzle(): Puzzle =
        Puzzle(
            id = puzzleDb.id,
            image = puzzleDb.image,
            difficulty = puzzleDb.difficulty,
            pieces = pieces.map { it.toPiece() },
            scaleFactor = puzzleDb.scaleFactor
        )
}