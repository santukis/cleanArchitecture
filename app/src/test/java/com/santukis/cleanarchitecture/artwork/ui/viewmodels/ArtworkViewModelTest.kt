package com.santukis.cleanarchitecture.artwork.ui.viewmodels

import android.app.Application
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.DI
import org.kodein.di.bind
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
    lateinit var artworkDataSource: ArtworkDataSource

    private val di by DI.lazy {
        bind<ArtworkDataSource>(tag = "repository") with provider { artworkDataSource }
    }

    private var viewModel: ArtworkViewModel? = null


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = ArtworkViewModel(Application(), di)
    }

    @Test
    fun loadArtworkDetailShouldReturnExpectedArtwork() {
        runBlocking {
            val observer = mock<Observer<Response<Artwork>>>()
            val expectedResponse = Artwork(id = "ExpectedId")
            whenever(artworkDataSource.loadArtworkDetail(any())).thenReturn(Response.Success(expectedResponse))
            whenever(artworkDataSource.isArtworkFavourite(any())).thenReturn(true)
            viewModel?.artwork?.observeForever(observer)

            viewModel?.loadArtworkDetail("anyId")

            verify(observer).onChanged(Response.Loading())
            verify(observer).onChanged(Response.Success(expectedResponse))

            viewModel?.artwork?.removeObserver(observer)
        }
    }
}