package com.santukis.cleanarchitecture.game.fragments.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentGameAnswerBinding

class AnswerFragment: BaseFragment<FragmentGameAnswerBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameAnswerBinding =
        FragmentGameAnswerBinding.inflate(inflater, container, false)

    override fun initializeViewListeners(binding: FragmentGameAnswerBinding) {
        super.initializeViewListeners(binding)
    }
}