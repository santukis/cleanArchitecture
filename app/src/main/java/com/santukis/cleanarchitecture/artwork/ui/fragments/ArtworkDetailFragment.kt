package com.santukis.cleanarchitecture.artwork.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentArtworkDetailBinding

class ArtworkDetailFragment: BaseFragment<FragmentArtworkDetailBinding>() {

    private val args: ArtworkDetailFragmentArgs by navArgs()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentArtworkDetailBinding =
        FragmentArtworkDetailBinding.inflate(inflater, container, false)


    override fun initializeViewComponents(binding: FragmentArtworkDetailBinding) {
        super.initializeViewComponents(binding)
        binding.viewmodel = artworkViewModel
    }

    override fun onStart() {
        super.onStart()
        artworkViewModel?.loadArtworkDetail(args.artworkId)?.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Loading -> binding.artwork = Artwork.EMPTY
                is Response.Success -> binding.artwork = response.data
                is Response.Error -> Toast.makeText(binding.root.context, "Unable to show Detail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}