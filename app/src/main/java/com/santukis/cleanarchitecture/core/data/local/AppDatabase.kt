package com.santukis.cleanarchitecture.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.santukis.cleanarchitecture.artwork.data.local.*

@Database(
    entities = [
        ArtworkDb::class,
        DimensionDb::class,
        ColorDb::class
    ],
    exportSchema = true, version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun artworkDao(): ArtworkDao

    abstract fun dimensionsDao(): DimensionDao

    abstract fun colorsDao(): ColorDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}