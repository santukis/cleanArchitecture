package com.santukis.cleanarchitecture.core.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.instance

class MainActivity: AppCompatActivity() , DIAware {

    override val di: DI by di()

    private val viewModelFactory: ViewModelProvider.Factory by instance()

    val artworkViewModel: ArtworkViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(ArtworkViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}