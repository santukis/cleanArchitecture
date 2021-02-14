package com.santukis.cleanarchitecture.artwork.data.local

import androidx.room.Dao
import androidx.room.Query
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworkDao: BaseDao<ArtworkDb> {

    @Query("SELECT * FROM artworks")
    fun loadArtworks(): Flow<List<ArtworkDb>>

    @Query("SELECT * FROM artworks WHERE artworks.id = :artworkId LIMIT 1")
    fun loadArtwork(artworkId: String): ArtworkDetailDb?

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