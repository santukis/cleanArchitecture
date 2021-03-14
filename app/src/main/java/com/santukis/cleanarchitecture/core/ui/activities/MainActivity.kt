package com.santukis.cleanarchitecture.core.ui.activities

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.databinding.ActivityMainBinding


class MainActivity: BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initializeViewComponents() {
        setUpNavigation()
    }

    override fun getNavController(): NavController = findNavController(R.id.nav_host_fragment)

    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(
            binding.bottomActionBar,
            getNavController()
        )
    }
}