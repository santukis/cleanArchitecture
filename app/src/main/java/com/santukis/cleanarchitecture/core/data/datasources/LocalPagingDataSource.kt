package com.santukis.cleanarchitecture.core.data.datasources

import android.content.Context
import android.content.SharedPreferences
import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.domain.model.Collection

class LocalPagingDataSource(context: Context): PagingDataSource {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}.paging", Context.MODE_PRIVATE)

    override fun getNextPage(collection: Collection): Int {
        val nextPage = preferences.getInt("${collection.name}_artworks_page", 1)
        preferences.edit().putInt("${collection.name}_artworks_page", nextPage + 1).apply()
        return nextPage
    }
}