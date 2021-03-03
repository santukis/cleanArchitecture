package com.santukis.cleanarchitecture.game.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentGameQuestionBinding

class QuestionFragment: BaseFragment<FragmentGameQuestionBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameQuestionBinding =
        FragmentGameQuestionBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentGameQuestionBinding) {
        super.initializeViewComponents(binding)
        binding.viewmodel = gameViewModel
    }

    override fun initializeViewListeners(binding: FragmentGameQuestionBinding) {
        super.initializeViewListeners(binding)

        gameViewModel?.question?.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Success -> binding.setQuestion(response.data)
                is Response.Error -> binding.noInfoPanel.isVisible = true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        gameViewModel?.loadQuestion()
    }
}