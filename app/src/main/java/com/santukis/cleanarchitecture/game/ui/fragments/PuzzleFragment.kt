package com.santukis.cleanarchitecture.game.ui.fragments

import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.ViewHolderProvider
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.ElementPuzzleItemBinding
import com.santukis.cleanarchitecture.databinding.FragmentPuzzleGameBinding
import com.santukis.cleanarchitecture.game.domain.model.Difficulty
import com.santukis.cleanarchitecture.game.domain.model.Puzzle
import com.santukis.cleanarchitecture.game.ui.binding.PuzzleViewHolder

class PuzzleFragment: BaseFragment<FragmentPuzzleGameBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentPuzzleGameBinding =
        FragmentPuzzleGameBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentPuzzleGameBinding) {
        super.initializeViewComponents(binding)
        gameViewModel?.loadPuzzle(
            puzzleId = arguments?.getString("puzzleId") ?: "",
            difficulty = arguments?.getInt("difficulty", 1)?.let { Difficulty.values().getOrNull(it) } ?: Difficulty.Medium)
    }

    override fun initializeViewListeners(binding: FragmentPuzzleGameBinding) {
        super.initializeViewListeners(binding)
        gameViewModel?.puzzle?.observe(this) { response ->
            binding.progress.isVisible = response is Response.Loading

            when(response) {
                is Response.Success -> binding.container.createPuzzle(response.data)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            it.actionBar?.hide()
            hideSystemUI(window = it.window)
        }
    }

    private fun hideSystemUI(window: Window) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.statusBars())
    }
}