package com.santukis.cleanarchitecture.artwork.data.remote

import com.santukis.cleanarchitecture.artwork.ArtworkDataProvider
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.squareup.moshi.Moshi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ArtworkDtoParsing {

    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder().build()
    }

    @Test
    fun artWorkResponseAdapterShouldReturnExpectedItemsWhenResponseContainsMultipleItems() {
        val adapter = moshi.adapter(ArtworkResponse::class.java).lenient()

        val items = adapter.fromJson(ArtworkDataProvider.artWorkResponseMultipleItems)?.items
        assertEquals(2, items?.size)

        val sampleItem = items?.getOrNull(0)
        assertNotNull(sampleItem)
        assertEquals("SK-C-597", sampleItem?.id)
        assertEquals("Portrait of a Woman, Possibly Maria Trip", sampleItem?.title)
        assertEquals("", sampleItem?.description)
        assertEquals("Rembrandt van Rijn", sampleItem?.author)
        assertEquals("https://lh3.googleusercontent.com/AyiKhdEWJ7XmtPXQbRg_kWqKn6mCV07bsuUB01hJHjVVP-ZQFmzjTWt7JIWiQFZbb9l5tKFhVOspmco4lMwqwWImfgg=s0", sampleItem?.image?.url)
        assertEquals(DatingDto.EMPTY, sampleItem?.dating)
        assertEquals(emptyList<DimensionDto>(), sampleItem?.dimensions)
        assertEquals(emptyList<ColorDto>(), sampleItem?.colors)
    }

    @Test
    fun artWorkResponseAdapterShouldReturnExpectedItemWhenResponseContainsOneItem() {
        val adapter = moshi.adapter(ArtworkResponse::class.java).lenient()
        val item =  adapter.fromJson(ArtworkDataProvider.artworkResponseSingleItem)?.item

        assertNotEquals(ArtworkDto.EMPTY, item)
        assertEquals("SK-C-6", item?.id)
        assertEquals("The Sampling Officials of the Amsterdam Drapers’ Guild, Known as ‘The Syndics’", item?.title)
        assertEquals("Samplers checked the quality of dyed cloth. Here Rembrandt shows them at work, distracted for a moment and looking up. One syndic is about to sit, or stand, so not all the heads are at the same level. A clever trick which, with the confident brushwork and subtle use of light, make this one of the liveliest group portraits of the 17th century. Originally painted for the sampling hall (Staalhof), in 1771 it was acquired by Amsterdam’s town hall.", item?.description)
        assertEquals("Rembrandt van Rijn", item?.author)
        assertEquals("https://lh3.googleusercontent.com/gShVRyvLLbwVB8jeIPghCXgr96wxTHaM4zqfmxIWRsUpMhMn38PwuUU13o1mXQzLMt5HFqX761u8Tgo4L_JG1XLATvw=s0", item?.image?.url)
        assertEquals(DatingDto(1662, 1662, 1662), item?.dating)
        assertEquals(2, item?.dimensions?.size)
        assertEquals(DimensionDto(type = "height", unit = "cm", value = "191.5"), item?.dimensions?.getOrNull(0))
        assertEquals(4, item?.colors?.size)
        assertEquals(ColorDto(percentage = 75, color = "#000000"), item?.colors?.getOrNull(0))
    }

    @Test
    fun artWorkResponseAdapterShouldReturnNullValuesWhenResponseFieldsAreNulls() {
        val adapter = moshi.adapter(ArtworkResponse::class.java).lenient()
        val item =  adapter.fromJson(ArtworkDataProvider.artworkResponseSingleItemWithNulls)?.item

        assertEquals("SK-C-6", item?.id)
        assertNull(item?.title)
        assertNull(item?.description)
    }

    @Test
    fun toArtWorkShouldReturnDefaultValuesWhenDtoValuesAreNull() {
        val artworkDto = ArtworkDto(
            id = null,
            title = null,
            description = null,
            image = null,
            dating = null,
            author = null,
            dimensions = null,
            colors = null
        )

        assertEquals(Artwork.EMPTY, artworkDto.toArtwork())
    }
}