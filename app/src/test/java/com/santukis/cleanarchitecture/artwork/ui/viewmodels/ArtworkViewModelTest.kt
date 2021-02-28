package com.santukis.cleanarchitecture.artwork.ui.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.santukis.cleanarchitecture.artwork.ArtworkDataProvider
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.utils.SyncExecutor
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.provider
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@Config(sdk = [28])
@RunWith(AndroidJUnit4::class)
class ArtworkViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockedArtworkDataSource: ArtworkDataSource

    private val di by DI.lazy {
        bind<ArtworkDataSource>(tag = "repository") with provider { mockedArtworkDataSource }
        bind<Executor>(tag = "executor") with factory { SyncExecutor() }
    }

    private var viewModel: ArtworkViewModel? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = ArtworkViewModel(Application(), di)
    }

    @After
    fun tearDown() {
        viewModel = null
    }

    @Test
    fun loadArtworkDetailShouldReturnExpectedArtwork() {
        runBlocking {
            val expectedResponse = Artwork(id = "ExpectedId")
            val observer = createObserver<Response<Artwork>>()
            viewModel?.artwork?.observeForever(observer)

            whenever(mockedArtworkDataSource.loadArtworkDetail(, any())).thenReturn(Response.Success(expectedResponse))
            whenever(mockedArtworkDataSource.isArtworkFavourite(any())).thenReturn(true)

            viewModel?.loadArtworkDetail("anyId")

            verify(observer).onChanged(Response.Loading())

            verify(observer).onChanged(Response.Success(expectedResponse))

            removeObserver(viewModel?.artwork, observer)
        }
    }

    @Test
    fun loadArtworkDetailShouldReturnError() {
        runBlocking {
            val expectedResponse = Response.Error<Artwork>(Exception("Expected response"))
            val observer = createObserver<Response<Artwork>>()
            viewModel?.artwork?.observeForever(observer)

            whenever(mockedArtworkDataSource.loadArtworkDetail(, any())).thenReturn(expectedResponse)
            whenever(mockedArtworkDataSource.isArtworkFavourite(any())).thenReturn(true)

            viewModel?.loadArtworkDetail("anyId")

            verify(observer).onChanged(Response.Loading())

            verify(observer).onChanged(expectedResponse)

            removeObserver(viewModel?.artwork, observer)
        }
    }

    @Test
    fun loadArtworksShouldReturnSuccess() {
        runBlocking {
            val expectedResponse = Response.Success(ArtworkDataProvider.artworks)
            val observer = createObserver<Response<List<Artwork>>>()
            viewModel?.artworks?.observeForever(observer)

            whenever(mockedArtworkDataSource.loadArtworks(, any())).thenReturn(flow { emit(expectedResponse) } )

            viewModel?.loadArtworks()

            verify(observer).onChanged(Response.Loading())

            verify(observer).onChanged(expectedResponse)

            removeObserver(viewModel?.artworks, observer)
        }
    }

    @Test
    fun loadArtworksShouldReturnError() {
        runBlocking {
            val expectedResponse = Response.Error<List<Artwork>>(Exception("No items"))
            val observer = createObserver<Response<List<Artwork>>>()
            viewModel?.artworks?.observeForever(observer)

            whenever(mockedArtworkDataSource.loadArtworks(, any())).thenReturn(flow { emit(expectedResponse) } )

            viewModel?.loadArtworks()

            verify(observer).onChanged(Response.Loading())

            verify(observer).onChanged(expectedResponse)

            removeObserver(viewModel?.artworks, observer)
        }
    }

    @Test
    fun loadFavouritesShouldReturnSuccess() {
        runBlocking {
            val expectedResponse = Response.Success(ArtworkDataProvider.artworks)
            val observer = createObserver<Response<List<Artwork>>>()
            viewModel?.favourites?.observeForever(observer)

            whenever(mockedArtworkDataSource.loadFavourites()).thenReturn(flow { emit(expectedResponse) } )

            viewModel?.loadFavourites()

            verify(observer).onChanged(Response.Loading())

            verify(observer).onChanged(expectedResponse)

            removeObserver(viewModel?.favourites, observer)
        }
    }

    @Test
    fun loadFavouritesShouldReturnError() {
        runBlocking {
            val expectedResponse = Response.Error<List<Artwork>>(Exception("No items"))
            val observer = createObserver<Response<List<Artwork>>>()
            viewModel?.favourites?.observeForever(observer)

            whenever(mockedArtworkDataSource.loadFavourites()).thenReturn(flow { emit(expectedResponse) } )

            viewModel?.loadFavourites()

            verify(observer).onChanged(Response.Loading())

            verify(observer).onChanged(expectedResponse)

            removeObserver(viewModel?.favourites, observer)
        }
    }

    @Test
    fun isFavouriteShouldToggleFavouriteToFalseWhenPreviousStatusIsTrue() {
        runBlocking {
            whenever(mockedArtworkDataSource.toggleFavourite(any())).thenReturn(Response.Success(Unit))
            whenever(mockedArtworkDataSource.isArtworkFavourite(any())).thenReturn(false)

            viewModel?.toggleFavourite("")

            assertFalse(viewModel?.isFavourite?.get() ?: true)
        }
    }

    @Test
    fun isFavouriteShouldToggleFavouriteToTrueWhenPreviousStatusIsFalse() {
        runBlocking {
            whenever(mockedArtworkDataSource.toggleFavourite(any())).thenReturn(Response.Success(Unit))
            whenever(mockedArtworkDataSource.isArtworkFavourite(any())).thenReturn(true)

            viewModel?.toggleFavourite("")

            assertTrue(viewModel?.isFavourite?.get() ?: false)
        }
    }

    private fun <Item> createObserver() = mock<Observer<Item>>()

    private fun <Item> removeObserver(observable: MutableLiveData<Item>?, observer: Observer<Item>) {
        observable?.removeObserver(observer)
    }

}