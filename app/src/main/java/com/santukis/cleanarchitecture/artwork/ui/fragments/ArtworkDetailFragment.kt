package com.santukis.cleanarchitecture.artwork.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentArtworkDetailBinding

class ArtworkDetailFragment: BaseFragment<FragmentArtworkDetailBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentArtworkDetailBinding =
        FragmentArtworkDetailBinding.inflate(inflater, container, false)


    override fun initializeViewListeners(binding: FragmentArtworkDetailBinding) {
        super.initializeViewListeners(binding)

        artworkViewModel?.artwork?.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Success -> binding.artwork = response.data
                is Response.Error -> {}
            }
        }
    }

    override fun loadData() {
      arguments?.getString("artworkId")?.apply {
          artworkViewModel?.loadArtworkDetail(this)
      }
    }
}