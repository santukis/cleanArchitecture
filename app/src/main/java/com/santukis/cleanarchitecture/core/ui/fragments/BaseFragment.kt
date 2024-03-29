package com.santukis.cleanarchitecture.core.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import com.santukis.cleanarchitecture.core.ui.activities.BaseActivity
import com.santukis.cleanarchitecture.game.ui.viewmodels.GameViewModel
import com.santukis.navigator.Navigator
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI

abstract class BaseFragment<Binding: ViewBinding>: Fragment(), DIAware {

    override val di: DI by closestDI()

    protected lateinit var binding: Binding

    protected val navigator: Navigator? by lazy { (activity as? BaseActivity<*>)?.navigator }

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

    override fun onDestroyView() {
        releaseViewComponents()
        super.onDestroyView()
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    open fun initializeViewComponents(binding: Binding) {}

    open fun initializeViewListeners(binding: Binding) {}

    open fun releaseViewComponents() {}
}