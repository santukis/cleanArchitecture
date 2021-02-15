package com.santukis.cleanarchitecture.game.fragments.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.santukis.cleanarchitecture.artwork.data.datasources.ArtworkDataSource
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class GameViewModel(application: Application): AndroidViewModel(application), DIAware {

    override val di: DI by di()

    private val artworkDataSource: ArtworkDataSource by di.instance("local")


}