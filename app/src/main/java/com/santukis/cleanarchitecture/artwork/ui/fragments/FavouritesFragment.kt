package com.santukis.cleanarchitecture.artwork.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentFavouritesBinding

class FavouritesFragment: BaseFragment<FragmentFavouritesBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavouritesBinding =
        FragmentFavouritesBinding.inflate(inflater, container, false)
}