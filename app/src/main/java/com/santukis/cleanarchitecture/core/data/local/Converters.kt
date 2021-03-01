package com.santukis.cleanarchitecture.core.data.local

import androidx.room.TypeConverter
import com.santukis.cleanarchitecture.artwork.domain.model.Collection

class Converters {

    @TypeConverter
    fun fromCollectionToInt(collection: Collection): Int = collection.ordinal

    @TypeConverter
    fun fromIntToCollection(collection: Int): Collection = Collection.values().getOrNull(collection) ?: Collection.Unknown

}