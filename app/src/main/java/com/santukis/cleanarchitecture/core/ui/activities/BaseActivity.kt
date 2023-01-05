package com.santukis.cleanarchitecture.core.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import com.santukis.cleanarchitecture.game.ui.viewmodels.GameViewModel
import com.santukis.navigator.Navigator
import com.santukis.navigator.NavigatorFactory
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

abstract class BaseActivity<Binding: ViewBinding>: AppCompatActivity() , DIAware {

    override val di: DI by closestDI()

    private val viewModelFactory: ViewModelProvider.Factory by instance()

    protected lateinit var binding: Binding

    val artworkViewModel: ArtworkViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(ArtworkViewModel::class.java)
    }

    val gameViewModel: GameViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)
    }

    val navigator: Navigator by lazy { NavigatorFactory().create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        setUpActionBar()
        initializeViewComponents()
        initializeViewListeners()
    }

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp()
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