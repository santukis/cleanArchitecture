package com.santukis.cleanarchitecture.core.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import com.santukis.cleanarchitecture.core.ui.viewmodels.NavigationViewModel
import com.santukis.cleanarchitecture.game.ui.viewmodels.GameViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.instance

abstract class BaseActivity<Binding: ViewBinding>: AppCompatActivity() , DIAware {

    override val di: DI by di()

    private val viewModelFactory: ViewModelProvider.Factory by instance()

    protected lateinit var binding: Binding

    val artworkViewModel: ArtworkViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(ArtworkViewModel::class.java)
    }

    val gameViewModel: GameViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)
    }

    val navigationViewModel: NavigationViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(NavigationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        setUpActionBar()
        observeNavigation()
        initializeViewComponents()
        initializeViewListeners()
    }

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp()
    }

    private fun observeNavigation() {
        navigationViewModel?.destiny?.observe(this) { destiny ->
            when(destiny) {
                -1 -> onSupportNavigateUp()
                else -> getNavController().navigate(destiny)
            }
        }
    }

    private fun setUpActionBar() {
        NavigationUI.setupActionBarWithNavController(
            this,
            getNavController()
        )
    }

    open fun initializeViewComponents() {}

    open fun initializeViewListeners() {}

    abstract fun getViewBinding(): Binding

    abstract fun getNavController(): NavController
}