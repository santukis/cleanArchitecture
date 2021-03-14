package com.santukis.cleanarchitecture.game.ui.activities

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.core.ui.activities.BaseActivity
import com.santukis.cleanarchitecture.databinding.ActivityPuzzleGameBinding

class PuzzleGameActivity: BaseActivity<ActivityPuzzleGameBinding>() {


    override fun getViewBinding(): ActivityPuzzleGameBinding = ActivityPuzzleGameBinding.inflate(layoutInflater)

    override fun getNavController(): NavController = findNavController(R.id.nav_host_fragment)

    override fun initializeViewComponents() {

    }
}