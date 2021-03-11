package com.santukis.cleanarchitecture.game.ui.activities

import android.util.Size
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.activities.BaseActivity
import com.santukis.cleanarchitecture.databinding.ActivityPuzzleGameBinding

class PuzzleGameActivity: BaseActivity<ActivityPuzzleGameBinding>() {


    override fun getViewBinding(): ActivityPuzzleGameBinding = ActivityPuzzleGameBinding.inflate(layoutInflater)

    override fun initializeViewComponents() {
        gameViewModel?.loadPuzzle()

        gameViewModel?.puzzle?.observe(this) { response ->
            when(response) {
                is Response.Success -> binding.container.createPuzzle(response.data.image, Size(7, 8))
            }
        }
    }
}