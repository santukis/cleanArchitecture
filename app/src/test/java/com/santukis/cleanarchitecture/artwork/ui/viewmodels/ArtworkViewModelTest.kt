package com.santukis.cleanarchitecture.artwork.ui.viewmodels

import android.app.Application
import android.os.Looper.getMainLooper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.provider
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@Config(sdk = [28])
@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
class ArtworkViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var artworkDataSource: ArtworkDataSource

    @Mock
    lateinit var artworkObserver: Observer<Response<Artwork>>

    private val di by DI.lazy {
        bind<ArtworkDataSource>(tag = "repository") with provider { artworkDataSource }
    }

    private var viewModel: ArtworkViewModel? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = ArtworkViewModel(Application(), di)
        viewModel?.artwork?.observeForever(artworkObserver)
    }

    @After
    fun tearDown() {
        viewModel?.artwork?.removeObserver(artworkObserver)
        viewModel = null
    }

    @Test
    fun loadArtworkDetailShouldReturnExpectedArtwork() {
        val expectedResponse = Artwork(id = "ExpectedId")

        runBlocking {
            whenever(artworkDataSource.loadArtworkDetail(any())).thenReturn(Response.Success(expectedResponse))
            whenever(artworkDataSource.isArtworkFavourite(any())).thenReturn(true)
        }

        shadowOf(getMainLooper()).idle()
        viewModel?.loadArtworkDetail("anyId")

        shadowOf(getMainLooper()).idle()
        verify(artworkObserver).onChanged(Response.Loading())
        shadowOf(getMainLooper()).idle()

        verify(artworkObserver).onChanged(Response.Success(expectedResponse))
        shadowOf(getMainLooper()).idle()
    }

    @Test
    fun loadArtworkDetailShouldReturnError() {
        val expectedResponse = Response.Error<Artwork>(Exception("Expected response"))

        runBlocking {
            whenever(artworkDataSource.loadArtworkDetail(any())).thenReturn(expectedResponse)
            whenever(artworkDataSource.isArtworkFavourite(any())).thenReturn(true)
        }

        shadowOf(getMainLooper()).idle()
        viewModel?.loadArtworkDetail("anyId")

        shadowOf(getMainLooper()).idle()
        verify(artworkObserver).onChanged(Response.Loading())
        shadowOf(getMainLooper()).idle()

        verify(artworkObserver).onChanged(expectedResponse)
        shadowOf(getMainLooper()).idle()
    }
}