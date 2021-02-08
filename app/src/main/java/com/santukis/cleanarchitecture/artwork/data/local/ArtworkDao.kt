package com.santukis.cleanarchitecture.artwork.data.local

import androidx.room.Dao
import androidx.room.Query
import com.santukis.cleanarchitecture.core.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworkDao: BaseDao<ArtworkDb> {

    @Query("SELECT * FROM artworks")
    fun loadArtworks(): Flow<List<ArtworkDb>>?

}

@Dao
interface DimensionDao: BaseDao<DimensionDb> {

    @Query("SELECT * FROM dimensions WHERE parentId = :from")
    fun loadDimensions(from: String): Flow<List<DimensionDb>>

}

@Dao
interface ColorDao: BaseDao<ColorDb> {

    @Query("SELECT * FROM colors WHERE parentId = :from")
    fun loadColors(from: String): Flow<List<ColorDb>>

}