package com.santukis.cleanarchitecture.core.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.santukis.cleanarchitecture.BuildConfig
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import com.santukis.cleanarchitecture.artwork.data.datasources.LocalArtworkDataSource
import com.santukis.cleanarchitecture.artwork.data.datasources.RijksmuseumArtworkDataSource
import com.santukis.cleanarchitecture.artwork.data.repository.ArtworkRepository
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.executors.AsyncExecutor
import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.ui.viewmodels.ViewModelFactory
import com.santukis.cleanarchitecture.game.data.datasources.GameDataSource
import com.santukis.cleanarchitecture.game.data.datasources.LocalGameDataSource
import com.santukis.cleanarchitecture.game.ui.viewmodels.GameViewModel
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

fun applicationModules(application: Application) = DI.Module("appModule", allowSilentOverride = true) {
    import(androidXModule(application))
    bind<AppDatabase>() with eagerSingleton { AppDatabase.getInstance(instance()) }
    bind<HttpClient>() with provider { HttpClient(BuildConfig.END_POINT) }

    import(artwork(), allowOverride = true)
    import(game(), allowOverride = true)
    import(viewmodels(), allowOverride = true)
    import(executors(), allowOverride = true)
}

fun executors() = DI.Module(name = "executors", allowSilentOverride = true) {
    bind<Executor>(tag = "executor") with factory { scope: CoroutineScope -> AsyncExecutor(scope) }
}

fun viewmodels() = DI.Module("viewmodels", allowSilentOverride = true) {
    bind<ViewModel>(tag = ArtworkViewModel::class.java.simpleName) with provider { ArtworkViewModel(instance(), di) }
    bind<ViewModel>(tag = GameViewModel::class.java.simpleName) with provider { GameViewModel(instance(), di) }
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(di) }
}

fun artwork() = DI.Module("artworks", allowSilentOverride = true) {
    bind<ArtworkDataSource>(tag = "local") with singleton { LocalArtworkDataSource(instance()) }
    bind<ArtworkDataSource>(tag = "remote") with singleton { RijksmuseumArtworkDataSource(instance()) }
    bind<ArtworkDataSource>(tag = "repository") with singleton { ArtworkRepository(instance("local"), instance("remote")) }
}

fun game() = DI.Module("game", allowSilentOverride = true) {
    bind<GameDataSource>(tag = "local") with singleton { LocalGameDataSource(instance(), instance()) }
}