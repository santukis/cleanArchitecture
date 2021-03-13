package com.santukis.cleanarchitecture.game.data.local

import android.graphics.Point
import android.util.Size
import androidx.room.*
import com.santukis.cleanarchitecture.game.domain.model.Piece
import com.santukis.cleanarchitecture.game.domain.model.Puzzle

@Entity(tableName = "puzzles")
data class PuzzleDb(
    @PrimaryKey
    val id: String = "",
    val image: String = "",
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
    var id: Long? = null,
    val parentId: String = "",
    val position: Point = Point(),
    val coordinates: Point = Point(),
    val size: Size = Size(0, 0),
    val canMove: Boolean = true
) {
    fun toPiece() =
        Piece(
            id = id ?: 0L,
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
            size = puzzleDb.size,
            pieces = pieces.map { it.toPiece() }
        )
}