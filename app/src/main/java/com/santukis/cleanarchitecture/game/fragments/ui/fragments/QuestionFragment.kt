package com.santukis.cleanarchitecture.game.fragments.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentGameQuestionBinding

class QuestionFragment: BaseFragment<FragmentGameQuestionBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameQuestionBinding =
        FragmentGameQuestionBinding.inflate(inflater, container, false)

    override fun initializeViewListeners(binding: FragmentGameQuestionBinding) {
        super.initializeViewListeners(binding)

        gameViewModel?.question?.observe(this) { response ->
            when(response) {
                is Response.Success -> binding.setQuestion(response.data)
                is Response.Error -> {}
            }
        }
    }

    override fun onStart() {
        super.onStart()
        gameViewModel?.loadQuestion()
    }
}