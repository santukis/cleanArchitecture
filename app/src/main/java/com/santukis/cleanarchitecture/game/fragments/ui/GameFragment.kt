package com.santukis.cleanarchitecture.game.fragments.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentGameBinding

class GameFragment: BaseFragment<FragmentGameBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameBinding =
        FragmentGameBinding.inflate(inflater, container, false)
}