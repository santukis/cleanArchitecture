package com.santukis.cleanarchitecture.game.ui.fragments

import android.view.*
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentGamesBinding

class GamesFragment: BaseFragment<FragmentGamesBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGamesBinding =
        FragmentGamesBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentGamesBinding) {
        super.initializeViewComponents(binding)
        binding.viewModel = navigationViewModel
    }
}