package com.santukis.cleanarchitecture.game.ui.fragments

import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentPuzzleGameBinding

class PuzzleGameFragment: BaseFragment<FragmentPuzzleGameBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentPuzzleGameBinding =
        FragmentPuzzleGameBinding.inflate(inflater, container, false)


    override fun initializeViewComponents(binding: FragmentPuzzleGameBinding) {
        super.initializeViewComponents(binding)
        gameViewModel?.loadPuzzle()
    }

    override fun initializeViewListeners(binding: FragmentPuzzleGameBinding) {
        super.initializeViewListeners(binding)
        gameViewModel?.puzzle?.observe(this) { response ->
            when(response) {
                is Response.Success -> binding.container.createPuzzle(response.data.image, Size(8, 7))
            }
        }
    }
}