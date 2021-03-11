package com.santukis.cleanarchitecture.core.ui.activities

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.databinding.ActivityMainBinding


class MainActivity: BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initializeViewComponents() {
        setUpActionBar()
        setUpNavigation()
    }

    private fun setUpActionBar() {
        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.nav_host_fragment)
        )
    }

    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(
            binding.bottomActionBar,
            findNavController(R.id.nav_host_fragment)
        )
    }
}