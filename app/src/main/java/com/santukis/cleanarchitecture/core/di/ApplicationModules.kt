package com.santukis.cleanarchitecture.core.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.santukis.cleanarchitecture.artwork.data.datasources.*
import com.santukis.cleanarchitecture.artwork.data.repository.ArtworkRepository
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import com.santukis.cleanarchitecture.core.data.datasources.LocalPagingDataSource
import com.santukis.cleanarchitecture.core.data.datasources.PagingDataSource
import com.santukis.cleanarchitecture.core.data.local.AppDatabase
import com.santukis.cleanarchitecture.core.data.remote.HttpClient
import com.santukis.cleanarchitecture.core.domain.executors.AsyncExecutor
import com.santukis.cleanarchitecture.core.domain.model.Executor
import com.santukis.cleanarchitecture.core.ui.viewmodels.NavigationViewModel
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
    bind<HttpClient>() with provider { HttpClient("") }

    import(artwork(), allowOverride = true)
    import(paging(), allowOverride = true)
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

fun paging() = DI.Module("paging", allowSilentOverride = true) {
    bind<PagingDataSource>() with singleton { LocalPagingDataSource(instance()) }
}

fun artwork() = DI.Module("artworks", allowSilentOverride = true) {
    bind<ArtworkDataSource>(tag = "local") with singleton { LocalArtworkDataSource(instance()) }
    bind<ArtworkDataSource>(tag = "rijks") with singleton { RijksMuseumArtworkDataSource(pagingDataSource = instance()) }
    bind<ArtworkDataSource>(tag = "met") with singleton { MetArtworkDataSource() }
    bind<ArtworkDataSource>(tag = "chicago") with singleton { ChicagoArtworkDataSource(pagingDataSource = instance()) }
    bind<ArtworkDataSource>(tag = "cleveland") with singleton { ClevelandArtworkDataSource() }
    bind<ArtworkDataSource>(tag = "hardvard") with singleton { HardvardArtworkDataSource(pagingDataSource = instance()) }
    bind<ArtworkDataSource>(tag = "walters") with singleton { WaltersArtworkDataSource(pagingDataSource = instance()) }
    bind<ArtworkDataSource>(tag = "remote") with singleton { RemoteArtworkDataSource(instance("rijks"), instance("met"), instance("chicago"), instance("cleveland"), instance("walters"), instance("hardvard")) }
    bind<ArtworkDataSource>(tag = "repository") with singleton { ArtworkRepository(instance("local"), instance("remote")) }
}

fun game() = DI.Module("game", allowSilentOverride = true) {
    bind<GameDataSource>(tag = "local") with singleton { LocalGameDataSource(instance(), instance()) }
}