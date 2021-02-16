package com.santukis.cleanarchitecture.game.fragments.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.frikiplanet.proteo.FragmentsAdapter
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentGameBinding

class GameFragment: BaseFragment<FragmentGameBinding>() {

    private var fragmentsAdapter: FragmentsAdapter? = null

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameBinding =
        FragmentGameBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentGameBinding) {
        super.initializeViewComponents(binding)

        fragmentsAdapter = FragmentsAdapter(this)
        binding.fragmentContainer.adapter = fragmentsAdapter
        fragmentsAdapter?.showFragments(listOf(QuestionFragment(), AnswerFragment()))

    }

    override fun initializeViewListeners(binding: FragmentGameBinding) {
        super.initializeViewListeners(binding)

    }

}