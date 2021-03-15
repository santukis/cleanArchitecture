package com.santukis.cleanarchitecture.game.ui.fragments

import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.ViewHolderProvider
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.ElementPuzzleItemBinding
import com.santukis.cleanarchitecture.databinding.FragmentPuzzleGameBinding
import com.santukis.cleanarchitecture.game.domain.model.Difficulty
import com.santukis.cleanarchitecture.game.domain.model.Puzzle
import com.santukis.cleanarchitecture.game.ui.binding.PuzzleViewHolder
import com.santukis.cleanarchitecture.game.ui.views.PuzzleLayout

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
        gameViewModel?.apply { binding.container.addOnPuzzleStateChanged(this) }
        gameViewModel?.puzzle?.observe(this) { response ->
            binding.progress.isVisible = response is Response.Loading

            when(response) {
                is Response.Success -> binding.container.createPuzzle(response.data)
            }
        }
    }

    override fun releaseViewComponents() {
        (activity as? AppCompatActivity)?.let {
            it.supportActionBar?.show()
            showSystemUI(it.window)
        }
        super.releaseViewComponents()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.let {
            it.supportActionBar?.hide()
            hideSystemUI(it.window)
        }
    }

    private fun hideSystemUI(window: Window) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.statusBars())
    }

    private fun showSystemUI(window: Window) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.statusBars())
    }
}