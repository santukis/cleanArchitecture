package com.santukis.cleanarchitecture.core.ui

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.santukis.cleanarchitecture.core.di.applicationModules
import org.kodein.di.DI
import org.kodein.di.DIAware

class ArtworkApplication: Application(), DIAware {

    override val di by DI.lazy {
        importAll(applicationModules(this@ArtworkApplication), allowOverride = true)
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}