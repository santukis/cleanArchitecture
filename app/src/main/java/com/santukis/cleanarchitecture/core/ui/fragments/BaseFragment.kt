package com.santukis.cleanarchitecture.core.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

abstract class BaseFragment<Binding: ViewBinding>: Fragment(), DIAware {

    override val di: DI by di()

    protected lateinit var binding: Binding

    private val viewModelFactory: ViewModelProvider.Factory by instance()

    protected val artworkViewModel: ArtworkViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(ArtworkViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewComponents(binding)
        initializeViewListeners(binding)
        loadData()
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    open fun initializeViewComponents(binding: Binding) {}

    open fun initializeViewListeners(binding: Binding) {}

    open fun loadData() {}
}