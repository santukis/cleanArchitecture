package com.santukis.cleanarchitecture.artwork.data.remote

import com.santukis.cleanarchitecture.artwork.ArtworkDataProvider
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.model.Dating
import com.squareup.moshi.Moshi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RijksMuseumArtworkTest {

    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder().build()
    }

    @Test
    fun artWorkResponseAdapterShouldReturnExpectedItemsWhenResponseContainsMultipleItems() {
        val adapter = moshi.adapter(RijksMuseumResponse::class.java).lenient()

        val items = adapter.fromJson(ArtworkDataProvider.rijksMuseumResponseMultipleItems)?.items
        assertEquals(2, items?.size)

        val sampleItem = items?.getOrNull(0)
        assertNotNull(sampleItem)
        assertEquals("SK-C-597", sampleItem?.id)
        assertEquals("Portrait of a Woman, Possibly Maria Trip", sampleItem?.title)
        assertEquals("", sampleItem?.description)
        assertEquals("Rembrandt van Rijn", sampleItem?.author)
        assertEquals("https://lh3.googleusercontent.com/AyiKhdEWJ7XmtPXQbRg_kWqKn6mCV07bsuUB01hJHjVVP-ZQFmzjTWt7JIWiQFZbb9l5tKFhVOspmco4lMwqwWImfgg=s0", sampleItem?.image?.url)
        assertEquals(RijksMuseumDating.EMPTY, sampleItem?.dating)
        assertEquals(emptyList<RijksMuseumDimension>(), sampleItem?.dimensions)
        assertEquals(emptyList<RijksMuseumColor>(), sampleItem?.colors)
    }

    @Test
    fun artWorkResponseAdapterShouldReturnExpectedItemWhenResponseContainsOneItem() {
        val adapter = moshi.adapter(RijksMuseumResponse::class.java).lenient()
        val item =  adapter.fromJson(ArtworkDataProvider.rijksMuseumResponseSingleItem)?.item

        assertNotEquals(RijksMuseumArtwork.EMPTY, item)
        assertEquals("SK-C-6", item?.id)
        assertEquals("The Sampling Officials of the Amsterdam Drapers’ Guild, Known as ‘The Syndics’", item?.title)
        assertEquals("Samplers checked the quality of dyed cloth. Here Rembrandt shows them at work, distracted for a moment and looking up. One syndic is about to sit, or stand, so not all the heads are at the same level. A clever trick which, with the confident brushwork and subtle use of light, make this one of the liveliest group portraits of the 17th century. Originally painted for the sampling hall (Staalhof), in 1771 it was acquired by Amsterdam’s town hall.", item?.description)
        assertEquals("Rembrandt van Rijn", item?.author)
        assertEquals("https://lh3.googleusercontent.com/gShVRyvLLbwVB8jeIPghCXgr96wxTHaM4zqfmxIWRsUpMhMn38PwuUU13o1mXQzLMt5HFqX761u8Tgo4L_JG1XLATvw=s0", item?.image?.url)
        assertEquals(RijksMuseumDating("1662"), item?.dating)
        assertEquals(2, item?.dimensions?.size)
        assertEquals(RijksMuseumDimension(type = "height", unit = "cm", value = "191.5"), item?.dimensions?.getOrNull(0))
        assertEquals(6, item?.colors?.size)
        assertEquals(RijksMuseumColor(color = "#8B7759", normalizedColor = "#E0CC91"), item?.colors?.getOrNull(0))
    }

    @Test
    fun artWorkResponseAdapterShouldReturnNullValuesWhenResponseFieldsAreNulls() {
        val adapter = moshi.adapter(RijksMuseumResponse::class.java).lenient()
        val item =  adapter.fromJson(ArtworkDataProvider.rijksMuseumResponseSingleItemWithNulls)?.item

        assertEquals("SK-C-6", item?.id)
        assertNull(item?.title)
        assertNull(item?.description)
    }

    @Test
    fun toArtWorkShouldReturnDefaultValuesWhenRijksMuseumArtworkValuesAreNull() {
        val artworkDto = RijksMuseumArtwork(
            id = null,
            title = null,
            description = null,
            image = null,
            dating = null,
            author = null,
            dimensions = null,
            colors = null
        )

        val result = artworkDto.toArtwork()
        assertEquals("", result.id)
        assertEquals("", result.title)
        assertEquals("", result.description)
        assertEquals("", result.image)
        assertEquals(Dating.EMPTY, result.dating)
        assertEquals("", result.author)
        assertTrue(result.dimensions.isEmpty())
        assertTrue(result.colors.isEmpty())
    }
}