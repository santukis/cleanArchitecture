package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.ArtworkDataProvider
import com.santukis.cleanarchitecture.artwork.domain.model.Color
import com.santukis.cleanarchitecture.artwork.domain.model.Dating
import com.santukis.cleanarchitecture.artwork.domain.model.Dimension
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RemoteArtworkDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var artworkDataSource: ArtworkDataSource

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val client = HttpClient(mockWebServer.url("").toString())
        artworkDataSource = RemoteArtworkDataSource(client)
    }

    @After
    fun tearDown() {
        try {
            mockWebServer.shutdown()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    @Test
    fun loadArtworksShouldReturnSuccessWhenResponseIsOk() {
        mockWebServer.enqueue(MockResponse().setBody(ArtworkDataProvider.artWorkResponseMultipleItems))

        runBlocking {
            when (val response = artworkDataSource.loadArtworks()) {
                is Response.Success -> {
                    assertEquals(2, response.data.size)

                    val sampleItem = response.data.getOrNull(0)
                    assertNotNull(sampleItem)
                    assertEquals("SK-C-597", sampleItem?.id)
                    assertEquals("Portrait of a Woman, Possibly Maria Trip", sampleItem?.title)
                    assertEquals("", sampleItem?.description)
                    assertEquals("Rembrandt van Rijn", sampleItem?.author)
                    assertEquals("https://lh3.googleusercontent.com/AyiKhdEWJ7XmtPXQbRg_kWqKn6mCV07bsuUB01hJHjVVP-ZQFmzjTWt7JIWiQFZbb9l5tKFhVOspmco4lMwqwWImfgg=s0", sampleItem?.image)
                    assertEquals(Dating.EMPTY, sampleItem?.dating)
                    assertEquals(emptyList<Dimension>(), sampleItem?.dimensions)
                    assertEquals(emptyList<Color>(), sampleItem?.colors)

                }
                else -> fail("Success should be called")
            }
        }
    }

    @Test
    fun loadArtworksShouldReturnErrorWhenResponseIsFail() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        runBlocking {
            when (val response = artworkDataSource.loadArtworks()) {
                is Response.Success -> fail("Error should be called")
                is Response.Error -> assertEquals("Server Error", response.error.message)
            }
        }
    }

    @Test
    fun loadArtworksShouldBuildTheRequestSuccessfullyWhenLastItemIsDefault() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        runBlocking {
            artworkDataSource.loadArtworks()

            val request = mockWebServer.takeRequest()
            assertEquals("/api/en/collection?key=YtHr5uf6&ps=25&p=0", request.path)
        }
    }

    @Test
    fun loadArtworksShouldBuildTheRequestSuccessfullyWhenLastItemIsNotDefault() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        runBlocking {
            artworkDataSource.loadArtworks(lastItem = 320)

            val request = mockWebServer.takeRequest()
            assertEquals("/api/en/collection?key=YtHr5uf6&ps=25&p=12", request.path)
        }
    }
}