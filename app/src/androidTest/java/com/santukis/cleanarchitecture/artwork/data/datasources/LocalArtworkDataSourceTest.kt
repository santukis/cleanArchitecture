package com.santukis.cleanarchitecture.artwork.data.datasources

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.santukis.cleanarchitecture.artwork.ArtworkDataProvider
import com.santukis.cleanarchitecture.artwork.data.mappers.toArtworkDb
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalArtworkDataSourceTest {

    private lateinit var database: AppDatabase
    private lateinit var artworkDataSource: ArtworkDataSource

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, AppDatabase::class.java).build()
        artworkDataSource = LocalArtworkDataSource(database)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun loadArtworksShouldReturnErrorWhenThereIsNoItemsStoredInDatabase() {
        runBlocking {
            artworkDataSource.loadArtworks()
                .catch { fail() }
                .onEmpty { fail() }
                .take(1)
                .collect{ response ->
                    when(response) {
                        is Response.Error -> assertEquals("No Items", response.error.message)
                        else -> fail()
                    }
                }
        }
    }

    @Test
    fun loadArtworksShouldReturnSuccessWhenThereAreItemsStoredInDatabase() {
        runBlocking {
            database.artworkDao().saveItems(ArtworkDataProvider.artworks.map { it.toArtworkDb() })
            artworkDataSource.loadArtworks()
                .catch { fail() }
                .onEmpty { fail() }
                .take(1)
                .collect{ response ->
                    when(response) {
                        is Response.Success -> assertEquals(3, response.data.size)
                        else -> fail()
                    }
                }
        }
    }
}