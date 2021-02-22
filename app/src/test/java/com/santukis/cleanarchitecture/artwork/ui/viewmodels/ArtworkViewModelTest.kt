package com.santukis.cleanarchitecture.artwork.ui.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.utils.SyncExecutor
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

    @Mock
    lateinit var artworkObserver: Observer<Response<Artwork>>

    private val di by DI.lazy {
        bind<ArtworkDataSource>(tag = "repository") with provider { mockedArtworkDataSource }
        bind<Executor>(tag = "executor") with factory { SyncExecutor() }
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
        runBlocking {
            val expectedResponse = Artwork(id = "ExpectedId")

            whenever(mockedArtworkDataSource.loadArtworkDetail(any())).thenReturn(Response.Success(expectedResponse))
            whenever(mockedArtworkDataSource.isArtworkFavourite(any())).thenReturn(true)

            viewModel?.loadArtworkDetail("anyId")

            verify(artworkObserver).onChanged(Response.Loading())

            verify(artworkObserver).onChanged(Response.Success(expectedResponse))
        }
    }

    @Test
    fun loadArtworkDetailShouldReturnError() {
        runBlocking {
            val expectedResponse = Response.Error<Artwork>(Exception("Expected response"))

            whenever(mockedArtworkDataSource.loadArtworkDetail(any())).thenReturn(expectedResponse)
            whenever(mockedArtworkDataSource.isArtworkFavourite(any())).thenReturn(true)

            viewModel?.loadArtworkDetail("anyId")

            verify(artworkObserver).onChanged(Response.Loading())

            verify(artworkObserver).onChanged(expectedResponse)
        }
    }
}