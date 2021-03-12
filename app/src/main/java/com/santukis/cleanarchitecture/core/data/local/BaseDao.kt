package com.santukis.cleanarchitecture.core.data.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface  BaseDao<Item> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveItem(item: Item): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveItems(items: List<Item>): List<Long>

    @Delete
    fun deleteItem(item: Item): Int

    @Update
    fun updateItem(item: Item): Int
}