package com.santukis.cleanarchitecture.core.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import com.santukis.cleanarchitecture.databinding.ActivityMainBinding
import com.santukis.cleanarchitecture.game.ui.viewmodels.GameViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.instance

class MainActivity: AppCompatActivity() , DIAware {

    override val di: DI by di()

    private val viewModelFactory: ViewModelProvider.Factory by instance()

    private lateinit var binding: ActivityMainBinding

    val artworkViewModel: ArtworkViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(ArtworkViewModel::class.java)
    }

    val gameViewModel: GameViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()
        setUpNavigation()
    }

    private fun setUpActionBar() {
        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.nav_host_fragment)
        )
    }

    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(
            binding.bottomActionBar,
            findNavController(R.id.nav_host_fragment)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

}