package com.santukis.cleanarchitecture.game.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentGameScoreBinding

class GameScoreFragment: BaseFragment<FragmentGameScoreBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameScoreBinding =
        FragmentGameScoreBinding.inflate(inflater, container, false)


}