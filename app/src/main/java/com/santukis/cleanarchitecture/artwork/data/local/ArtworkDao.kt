package com.santukis.cleanarchitecture.artwork.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery
import com.santukis.cleanarchitecture.core.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworkDao: BaseDao<ArtworkDb> {

    @Query("SELECT * FROM artworks")
    fun loadArtworks(): Flow<List<ArtworkDb>>

    @Query("SELECT * FROM artworks WHERE artworks.id = :artworkId LIMIT 1")
    fun loadArtwork(artworkId: String): ArtworkDetailDb?

    @Query("SELECT * FROM artworks INNER JOIN favourites ON artworks.id = favourites.id")
    fun loadFavouriteArtworks(): Flow<List<ArtworkDb>>

    @RawQuery
    fun loadQuestion(query: SupportSQLiteQuery): List<ArtworkDetailDb>?
}

@Dao
interface DimensionDao: BaseDao<DimensionDb>

@Dao
interface ColorDao: BaseDao<ColorDb>

@Dao
interface CategoryDao: BaseDao<CategoryDb>

@Dao
interface MaterialDao: BaseDao<MaterialDb>

@Dao
interface TechniqueDao: BaseDao<TechniqueDb>

@Dao
interface FavouritesDao: BaseDao<FavouriteDb> {

    @Query("SELECT * FROM favourites WHERE id = :artworkId LIMIT 1")
    fun loadFavourite(artworkId: String): FavouriteDb?

    @Transaction
    fun toggleFavourite(artworkId: String): Int =
        when(val favourite = loadFavourite(artworkId)) {
            null -> saveItem(FavouriteDb(artworkId)).toInt()
            else -> deleteItem(favourite)
        }
}