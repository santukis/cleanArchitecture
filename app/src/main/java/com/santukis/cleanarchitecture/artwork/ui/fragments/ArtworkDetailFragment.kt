package com.santukis.cleanarchitecture.artwork.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.santukis.cleanarchitecture.databinding.FragmentArtworkDetailBinding

class ArtworkDetailFragment: Fragment() {

    companion object {
        const val ARTWORK_ID = "artwork_id"
    }

    private lateinit var binding: FragmentArtworkDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentArtworkDetailBinding.inflate(inflater)

        return binding.root
    }
}