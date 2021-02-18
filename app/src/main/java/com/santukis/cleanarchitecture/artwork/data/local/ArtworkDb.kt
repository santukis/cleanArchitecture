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
    val dating: Int,
    var updatedAt: Long = 0L
)

@Entity(
    tableName = "dimensions",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
                  ],
    indices = [Index("parentId", "parentId"), Index("value", "value")]
)
data class DimensionDb(
    @PrimaryKey(autoGenerate = true) val dimensionId: Long?,
    val parentId: String,
    @Embedded val unit: MeasureUnitDb,
    val value: Double,
    val type: String
)

data class MeasureUnitDb(val unit: String)

@Entity(
    tableName = "colors",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ) ],
    indices = [Index("parentId", "parentId"), Index("color", "color")]
)
data class ColorDb(
    @PrimaryKey(autoGenerate = true) val colorId: Long?,
    val parentId: String,
    val color: Int = 0,
    val normalizedColor: String = "#000000"
)

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ) ],
    indices = [Index("parentId", "parentId"), Index("category", "category")]
)
data class CategoryDb(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val parentId: String,
    val category: String = ""
)

@Entity(
    tableName = "materials",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ) ],
    indices = [Index("parentId", "parentId"), Index("material", "material")]
)
data class MaterialDb(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val parentId: String,
    val material: String = ""
)

@Entity(
    tableName = "techniques",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ) ],
    indices = [Index("parentId", "parentId"), Index("technique", "technique")]
)
data class TechniqueDb(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val parentId: String,
    val technique: String = ""
)

data class ArtworkDetailDb(
    @Embedded val artworkDb: ArtworkDb,

    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    ) val dimensions: List<DimensionDb>,

    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    ) val colors: List<ColorDb>,

    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    ) val categories: List<CategoryDb>,

    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    ) val materials: List<MaterialDb>,

    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    ) val techniques: List<TechniqueDb>
)

@Entity(tableName = "favourites",
        foreignKeys = [
            androidx.room.ForeignKey(
                entity = ArtworkDb::class,
                parentColumns = ["id"],
                childColumns = ["id"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.NO_ACTION
            )
        ]
)
data class Favourites(
    @PrimaryKey()
    val id: String
)