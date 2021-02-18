package com.santukis.cleanarchitecture.game.data.datasources

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.santukis.cleanarchitecture.artwork.ArtworkDataProvider
import com.santukis.cleanarchitecture.artwork.data.mappers.toArtworkDb
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.game.domain.model.Question
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LocalGameDataSourceTest {

    private lateinit var database: AppDatabase
    private lateinit var gameDataSource: GameDataSource

    @Before
    fun setup() {
        val mockedContext = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(mockedContext, AppDatabase::class.java).build()
        gameDataSource = LocalGameDataSource(mockedContext, database)
    }

    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun loadQuestionShouldReturnTitleQuestionWhenTypeIs0() {
        runBlocking {
            database.artworkDao().saveItems(ArtworkDataProvider.artworks.map { it.toArtworkDb() })
            when(val response = gameDataSource.loadQuestion(0)) {
                is Response.Success -> assertEquals(Question.TitleQuestion::class.java.simpleName, response.data::class.java.simpleName)
                is Response.Error -> fail("Success should be called")
            }
        }
    }

    @Test
    fun loadQuestionShouldReturnAuthorQuestionWhenTypeIs1() {
        runBlocking {
            database.artworkDao().saveItems(ArtworkDataProvider.artworks.map { it.toArtworkDb() })
            when(val response = gameDataSource.loadQuestion(1)) {
                is Response.Success -> assertEquals(Question.AuthorQuestion::class.java.simpleName, response.data::class.java.simpleName)
                is Response.Error -> fail("Success should be called")
            }
        }
    }

    @Test
    fun loadQuestionShouldReturnDatingQuestionWhenTypeIs2() {
        runBlocking {
            database.artworkDao().saveItems(ArtworkDataProvider.artworks.map { it.toArtworkDb() })
            when(val response = gameDataSource.loadQuestion(2)) {
                is Response.Success -> assertEquals(Question.DatingQuestion::class.java.simpleName, response.data::class.java.simpleName)
                is Response.Error -> fail("Success should be called")
            }
        }
    }

    @Test
    fun loadQuestionShouldReturnErrorWhenTypeIsOutOfRange() {
        runBlocking {
            database.artworkDao().saveItems(ArtworkDataProvider.artworks.map { it.toArtworkDb() })
            when(val response = gameDataSource.loadQuestion(-1)) {
                is Response.Success -> fail("Error should be called")
                is Response.Error -> assertEquals("Unable to load Question", response.error.message)
            }
        }
    }
}