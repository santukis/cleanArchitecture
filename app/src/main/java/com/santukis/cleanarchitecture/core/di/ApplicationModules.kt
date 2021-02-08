package com.santukis.cleanarchitecture.core.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.data.datasources.LocalArtworkDataSource
import com.santukis.cleanarchitecture.artwork.data.datasources.RemoteArtworkDataSource
import com.santukis.cleanarchitecture.artwork.data.repository.ArtworkRepository
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.domain.usecases.LoadArtworks
import com.santukis.cleanarchitecture.artwork.domain.usecases.LoadMoreArtworks
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.domain.model.FlowUseCase
import com.santukis.cleanarchitecture.core.domain.model.UseCase
import com.santukis.cleanarchitecture.core.domain.model.executors.AsyncUseCaseExecutor
import com.santukis.cleanarchitecture.core.ui.viewmodels.ViewModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

fun applicationModules(application: Application) = DI.Module("appModule", allowSilentOverride = true) {
    import(androidXModule(application))
    bind<AppDatabase>() with eagerSingleton { AppDatabase.getInstance(instance()) }
    bind<HttpClient>() with provider { HttpClient(BuildConfig.END_POINT) }

    import(artwork(), allowOverride = true)
    import(usecases(), allowOverride = true)
    import(viewmodels(), allowOverride = true)
}

fun usecases() = DI.Module("usecases", allowSilentOverride = true) {
    bind<Executor>(tag = "asyncExecutor") with provider { AsyncUseCaseExecutor() }
    bind<FlowUseCase<Unit, List<Artwork>>>(tag = "loadArtworks") with provider { LoadArtworks(instance()) }
    bind<UseCase<Int, Unit>>(tag = "loadMoreArtworks") with provider { LoadMoreArtworks(instance()) }
}

fun viewmodels() = DI.Module("viewmodels", allowSilentOverride = true) {
    bind<ViewModel>(tag = ArtworkViewModel::class.java.simpleName) with provider { ArtworkViewModel(instance()) }
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(di) }
}

fun artwork() = DI.Module("artworks", allowSilentOverride = true) {
    bind<ArtworkDataSource>(tag = "local") with provider { LocalArtworkDataSource(instance()) }
    bind<ArtworkDataSource>(tag = "remote") with provider { RemoteArtworkDataSource(instance()) }
    bind<ArtworkRepository>() with provider { ArtworkRepository(instance("local"), instance("remote")) }
}