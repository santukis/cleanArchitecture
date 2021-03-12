package com.santukis.cleanarchitecture.core.data.local

import android.graphics.Point
import android.util.Size
import androidx.room.TypeConverter
import com.santukis.cleanarchitecture.artwork.domain.model.Collection

class Converters {

    @TypeConverter
    fun fromCollectionToInt(collection: Collection): Int = collection.ordinal

    @TypeConverter
    fun fromIntToCollection(collection: Int): Collection = Collection.values().getOrNull(collection) ?: Collection.Unknown

    @TypeConverter
    fun fromPointToString(point: Point): String = "${point.x},${point.y}"

    @TypeConverter
    fun fromStringToPoint(point: String): Point =
        point.takeIf { it.isNotEmpty() }?.split(",")
            ?.takeIf { it.isNotEmpty() }?.let { Point(it.first().toInt(), it.last().toInt()) } ?: Point()

    @TypeConverter
    fun fromSizeToString(size: Size): String = size.toString()

    @TypeConverter
    fun fromStringToSize(size: String): Size =
        size.takeIf { it.isNotEmpty() }?.let { Size.parseSize(it) } ?: Size(0, 0)
}