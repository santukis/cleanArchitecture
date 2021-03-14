package com.santukis.cleanarchitecture.core.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import com.santukis.cleanarchitecture.core.ui.activities.BaseActivity
import com.santukis.cleanarchitecture.core.ui.activities.MainActivity
import com.santukis.cleanarchitecture.core.ui.viewmodels.NavigationViewModel
import com.santukis.cleanarchitecture.game.ui.viewmodels.GameViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di

abstract class BaseFragment<Binding: ViewBinding>: Fragment(), DIAware {

    override val di: DI by di()

    protected lateinit var binding: Binding

    protected val navigationViewModel: NavigationViewModel? by lazy { (activity as? BaseActivity<*>)?.navigationViewModel }

    protected val artworkViewModel: ArtworkViewModel? by lazy { (activity as? BaseActivity<*>)?.artworkViewModel }

    protected val gameViewModel: GameViewModel? by lazy { (activity as? BaseActivity<*>)?.gameViewModel }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewComponents(binding)
        initializeViewListeners(binding)
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    open fun initializeViewComponents(binding: Binding) {}

    open fun initializeViewListeners(binding: Binding) {}
}