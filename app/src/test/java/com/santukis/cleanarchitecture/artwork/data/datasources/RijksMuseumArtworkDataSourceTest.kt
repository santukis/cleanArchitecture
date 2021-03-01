package com.santukis.cleanarchitecture.artwork.data.datasources

import com.santukis.cleanarchitecture.artwork.ArtworkDataProvider
import com.santukis.cleanarchitecture.artwork.domain.model.Collection
import com.santukis.cleanarchitecture.artwork.domain.model.Color
import com.santukis.cleanarchitecture.artwork.domain.model.Dating
import com.santukis.cleanarchitecture.artwork.domain.model.Dimension
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RijksMuseumArtworkDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var artworkDataSource: ArtworkDataSource

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val client = HttpClient(mockWebServer.url("").toString())
        artworkDataSource = RijksmuseumArtworkDataSource(client)
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
    fun `loadArtworks should return success when response is Ok`() {
        mockWebServer.enqueue(MockResponse().setBody(ArtworkDataProvider.rijksMuseumResponseMultipleItems))

        runBlocking {
            artworkDataSource.loadArtworks(collection = Collection.Rijksmuseum)
                .catch { fail() }
                .onEmpty { fail() }
                .collect {
                    val response = (it as Response.Success).data

                    assertEquals(2, response.size)

                    val sampleItem = response.getOrNull(0)
                    assertNotNull(sampleItem)
                    assertEquals("SK-C-597", sampleItem?.id)
                    assertEquals("Portrait of a Woman, Possibly Maria Trip", sampleItem?.title)
                    assertEquals("", sampleItem?.description)
                    assertEquals("Rembrandt van Rijn", sampleItem?.author)
                    assertEquals(
                        "https://lh3.googleusercontent.com/AyiKhdEWJ7XmtPXQbRg_kWqKn6mCV07bsuUB01hJHjVVP-ZQFmzjTWt7JIWiQFZbb9l5tKFhVOspmco4lMwqwWImfgg=s0",
                        sampleItem?.image
                    )
                    assertEquals(Dating.EMPTY, sampleItem?.dating)
                    assertEquals(emptyList<Dimension>(), sampleItem?.dimensions)
                    assertEquals(emptyList<Color>(), sampleItem?.colors)
                }
        }
    }

    @Test
    fun `loadArtworks should return error when response is fail`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        runBlocking {
            artworkDataSource.loadArtworks(collection = Collection.Rijksmuseum)
                .onEmpty {
                    fail()
                }
                .collect {
                    assertTrue(it is Response.Error)
                }
        }
    }

    @Test
    fun `loadArtworks should build the request successfully when lastItem is default`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        runBlocking {
            artworkDataSource.loadArtworks(collection = Collection.Rijksmuseum).collect()

            val request = mockWebServer.takeRequest()
            assertEquals("/api/en/collection?key=YtHr5uf6&imgonly=true&toppieces=true&s=objecttype&ps=50&p=1", request.path)
        }
    }

    @Test
    fun `loadArtworks should build the request successfully when lastItem is not default`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        runBlocking {
            artworkDataSource.loadArtworks(collection = Collection.Rijksmuseum, lastItem = 320).collect()

            val request = mockWebServer.takeRequest()
            assertEquals("/api/en/collection?key=YtHr5uf6&imgonly=true&toppieces=true&s=objecttype&ps=50&p=7", request.path)
        }
    }
}