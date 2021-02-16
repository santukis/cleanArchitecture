package com.santukis.cleanarchitecture.game.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentGameAnswerBinding

class AnswerFragment: BaseFragment<FragmentGameAnswerBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameAnswerBinding =
        FragmentGameAnswerBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentGameAnswerBinding) {
        super.initializeViewComponents(binding)
        binding.viewmodel = gameViewModel
    }

    override fun initializeViewListeners(binding: FragmentGameAnswerBinding) {
        super.initializeViewListeners(binding)

    }
}