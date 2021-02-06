package com.santukis.cleanarchitecture.artwork.data.local

import androidx.room.*

@Entity(
    tableName = "artworks",
    indices = [Index("title", "title"), Index("author", "author")])
data class ArtworkDb(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val author: String,
    val image: String,
    @Embedded val dating: DatingDb
) {
}

data class DatingDb(
    @ColumnInfo(name = "_dating_year") val year: Int,
    @ColumnInfo(name = "_dating_started") val started: Int,
    @ColumnInfo(name = "_dating_finished") val finished: Int
)

@Entity(
    tableName = "dimensions",
    foreignKeys = [ ForeignKey(entity = ArtworkDb::class, parentColumns = ["id"], childColumns = ["parentId"], onDelete = ForeignKey.CASCADE) ],
    indices = [Index("parentId", "parentId"), Index("value", "value")]
)
data class DimensionDb(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val parentId: String,
    @Embedded val unit: MeasureUnitDb,
    val value: Double,
    val type: String
)

data class MeasureUnitDb(val unit: String)

@Entity(
    tableName = "colors",
    foreignKeys = [ ForeignKey(entity = ArtworkDb::class, parentColumns = ["id"], childColumns = ["parentId"], onDelete = ForeignKey.CASCADE) ],
    indices = [Index("parentId", "parentId"), Index("color", "color")]
)
data class ColorDb(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val parentId: String,
    val percentage: Int,
    val color: String
)