package com.santukis.cleanarchitecture.game.ui.fragments

import android.view.*
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.core.ui.navigator.DefaultActivityFactory
import com.santukis.cleanarchitecture.databinding.FragmentGamesBinding
import com.santukis.cleanarchitecture.game.ui.activities.PuzzleGameActivity
import com.santukis.cleanarchitecture.game.ui.activities.QuizGameActivity

class GamesFragment: BaseFragment<FragmentGamesBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGamesBinding =
        FragmentGamesBinding.inflate(inflater, container, false)

    override fun initializeViewListeners(binding: FragmentGamesBinding) {
        super.initializeViewListeners(binding)
        binding.quiz.setOnClickListener {
            navigator?.openActivity(DefaultActivityFactory(QuizGameActivity::class.java))
        }

        binding.puzzle.setOnClickListener {
            navigator?.openActivity(DefaultActivityFactory(PuzzleGameActivity::class.java))
        }
    }
}