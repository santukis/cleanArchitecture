package com.santukis.cleanarchitecture.game.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.binding.CommonBinding
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentPuzzleGameBinding
import com.santukis.cleanarchitecture.game.domain.model.Difficulty

class PuzzleFragment: BaseFragment<FragmentPuzzleGameBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentPuzzleGameBinding =
        FragmentPuzzleGameBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentPuzzleGameBinding) {
        super.initializeViewComponents(binding)
        binding.util = CommonBinding
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
                is Response.Success -> binding.puzzle = response.data
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.apply { hideSystemUI(this) }
    }

    override fun releaseViewComponents() {
        super.releaseViewComponents()
        (activity as? AppCompatActivity)?.apply { showSystemUI(this) }
    }

    private fun hideSystemUI(activity: AppCompatActivity) {
        activity.supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(activity.window, true)
        WindowInsetsControllerCompat(activity.window, activity.window.decorView).hide(WindowInsetsCompat.Type.statusBars())
    }

    private fun showSystemUI(activity: AppCompatActivity) {
        activity.supportActionBar?.show()
        WindowCompat.setDecorFitsSystemWindows(activity.window, true)
        WindowInsetsControllerCompat(activity.window, activity.window.decorView).show(WindowInsetsCompat.Type.statusBars())
    }
}