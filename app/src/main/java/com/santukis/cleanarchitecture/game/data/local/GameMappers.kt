package com.santukis.cleanarchitecture.game.data.local

import com.santukis.cleanarchitecture.game.domain.model.Piece
import com.santukis.cleanarchitecture.game.domain.model.Puzzle

fun fromQuestionTypeToSqlQuery(type: Int): String {
    val (search, orderBy) = when(type) {
        0 -> "artworks.title" to "title"
        1 -> "artworks.author" to "author"
        else -> "artworks.dating" to "dating"
    }

    return "SELECT * FROM artworks WHERE $search != '' GROUP BY $orderBy ORDER BY RANDOM() LIMIT 3"
}

fun Puzzle.toPuzzleDb() =
    PuzzleDb(
        id = id,
        image = image,
        difficulty = difficulty,
        scaleFactor = scaleFactor
    )

fun Piece.toPieceDb(puzzleId: String) =
    PieceDb(
        parentId = puzzleId,
        cell = cell,
        position = position,
        coordinates = coordinates,
        size = size,
        canMove = canMove
    )