package com.santukis.cleanarchitecture.game.data.local

import com.santukis.cleanarchitecture.artwork.data.local.ArtworkDb

fun fromQuestionTypeToSqlQuery(type: Int): String {
    val (search, orderBy) = when(type) {
        0 -> "artworks.title" to "title"
        1 -> "artworks.author" to "author"
        else -> "artworks.dating" to "dating"
    }

    return "SELECT * FROM artworks WHERE $search != '' GROUP BY $orderBy ORDER BY RANDOM() LIMIT 3"
}