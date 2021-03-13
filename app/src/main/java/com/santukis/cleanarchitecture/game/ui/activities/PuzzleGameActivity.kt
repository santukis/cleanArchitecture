package com.santukis.cleanarchitecture.game.ui.activities

import android.util.Size
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.activities.BaseActivity
import com.santukis.cleanarchitecture.databinding.ActivityPuzzleGameBinding

class PuzzleGameActivity: BaseActivity<ActivityPuzzleGameBinding>() {


    override fun getViewBinding(): ActivityPuzzleGameBinding = ActivityPuzzleGameBinding.inflate(layoutInflater)

    override fun initializeViewComponents() {
        hideSystemUI()
        gameViewModel?.loadPuzzle("")

        gameViewModel?.puzzle?.observe(this) { response ->
            when(response) {
                is Response.Success -> binding.container.createPuzzle(response.data.image, Size(7, 8))
            }
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.statusBars())
    }
}