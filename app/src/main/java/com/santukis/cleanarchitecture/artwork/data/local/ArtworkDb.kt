package com.santukis.cleanarchitecture.artwork.data.local

import androidx.room.*
import com.santukis.cleanarchitecture.artwork.domain.model.*
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.game.data.local.PieceDb
import com.santukis.cleanarchitecture.game.domain.model.Answer
import com.santukis.cleanarchitecture.game.domain.model.Difficulty
import com.santukis.cleanarchitecture.game.domain.model.Puzzle
import com.santukis.cleanarchitecture.game.domain.model.Question

@Entity(
    tableName = "artworks",
    indices = [Index("title", "title"), Index("author", "author")]
)
data class ArtworkDb(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val author: String,
    val image: String,
    val dating: String,
    val creditLine: String,
    val url: String,
    val collection: Collection,
    val department: String,
    val style: String,
    val isPuzzle: Boolean,
    var shouldBeUpdated: Boolean = false
) {

    fun toArtwork() =
        Artwork(
            id = id,
            title = title,
            description = description,
            author = author,
            image = image,
            dating = Dating(year = dating),
            creditLine = creditLine,
            url = url,
            style = style,
            collection = collection,
            department = department,
            shouldBeUpdated = shouldBeUpdated
        )

    fun toArtwork(
        dimensions: List<Dimension>,
        colors: List<Color>,
        categories: List<Category>,
        materials: List<Material>,
        techniques: List<Technique>
    ) =
        Artwork(
            id = id,
            title = title,
            description = description,
            author = author,
            image = image,
            dating = Dating(year = dating),
            creditLine = creditLine,
            url = url,
            style = style,
            collection = collection,
            dimensions = dimensions,
            colors = colors,
            categories = categories,
            materials = materials,
            techniques = techniques,
            department = department,
            shouldBeUpdated = shouldBeUpdated
        )

    fun toPuzzle() =
        Puzzle(
            id = id,
            image = image
        )
}

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
) {
    fun toDimension() =
        when (type) {
            Dimension.Height::class.java.name -> Dimension.Height(value = value, unit = unit.toMeasureUnit())
            Dimension.Width::class.java.name -> Dimension.Width(value = value, unit = unit.toMeasureUnit())
            Dimension.Depth::class.java.name -> Dimension.Depth(value = value, unit = unit.toMeasureUnit())
            Dimension.Weight::class.java.name -> Dimension.Weight(value = value, unit = unit.toMeasureUnit())
            else -> Dimension.Unknown(value = value, unit = unit.toMeasureUnit())
        }
}

data class MeasureUnitDb(val unit: String) {
    fun toMeasureUnit() = MeasureUnit(unit)
}

@Entity(
    tableName = "colors",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [Index("parentId", "parentId"), Index("color", "color")]
)
data class ColorDb(
    @PrimaryKey(autoGenerate = true) val colorId: Long?,
    val parentId: String,
    val color: Int = 0,
    val normalizedColor: String = "#000000"
) {
    fun toColor() =
        Color(
            normalizedColor = normalizedColor,
            color = color
        )
}

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [Index("parentId", "parentId"), Index("category", "category")]
)
data class CategoryDb(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val parentId: String,
    val category: String = ""
) {
    fun toCategory() = Category(category)
}

@Entity(
    tableName = "materials",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [Index("parentId", "parentId"), Index("material", "material")]
)
data class MaterialDb(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val parentId: String,
    val material: String = ""
) {
    fun toMaterial() = Material(material)
}

@Entity(
    tableName = "techniques",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkDb::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [Index("parentId", "parentId"), Index("technique", "technique")]
)
data class TechniqueDb(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val parentId: String,
    val technique: String = ""
) {
    fun toTechnique() = Technique(technique)
}

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
) {
    fun toArtwork() =
        artworkDb.toArtwork(
            dimensions = dimensions.map { it.toDimension() },
            colors = colors.map { it.toColor() },
            categories = categories.map { it.toCategory() },
            materials = materials.map { it.toMaterial() },
            techniques = techniques.map { it.toTechnique() }
        )

    fun toTitleAnswer() = Answer(artworkDb.image, artworkDb.title, artworkDb.description)

    fun toAuthorAnswer() = Answer(artworkDb.image, artworkDb.author, artworkDb.description)

    fun toDatingAnswer() = Answer(artworkDb.image, artworkDb.dating, artworkDb.description)

    fun toPuzzle(difficulty: Difficulty, pieces: List<PieceDb>) =
        Puzzle(
            id = artworkDb.id,
            image = artworkDb.image,
            difficulty = difficulty,
            pieces = pieces.map { it.toPiece() }
        )
}

fun List<ArtworkDetailDb>.toQuestion(type: Int): Question? =
    when (type) {
        0 -> Question.TitleQuestion(answers = map { it.toTitleAnswer() })
        1 -> Question.AuthorQuestion(answers = map { it.toAuthorAnswer() })
        2 -> Question.DatingQuestion(answers = map { it.toDatingAnswer() })
        else -> null
    }

@Entity(
    tableName = "favourites",
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
data class FavouriteDb(
    @PrimaryKey()
    val id: String
)