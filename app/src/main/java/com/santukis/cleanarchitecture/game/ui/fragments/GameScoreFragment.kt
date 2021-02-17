package com.santukis.cleanarchitecture.game.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentGameScoreBinding
import com.santukis.cleanarchitecture.game.domain.model.GameHistory

class GameScoreFragment: BaseFragment<FragmentGameScoreBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGameScoreBinding =
        FragmentGameScoreBinding.inflate(inflater, container, false)

    override fun initializeViewListeners(binding: FragmentGameScoreBinding) {
        super.initializeViewListeners(binding)
        gameViewModel?.gameHistory?.observe(this) { response ->

            when(response) {
                is Response.Success -> binding.history = response.data
                is Response.Error -> binding.history = GameHistory.EMPTY
            }
        }
    }

    override fun onStart() {
        super.onStart()
        gameViewModel?.loadGameHistory()
    }
}