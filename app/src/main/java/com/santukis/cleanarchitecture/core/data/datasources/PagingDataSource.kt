package com.santukis.cleanarchitecture.core.data.datasources

import com.santukis.cleanarchitecture.artwork.domain.model.Collection

interface PagingDataSource {

    fun getNextPage(collection: Collection): Int
}