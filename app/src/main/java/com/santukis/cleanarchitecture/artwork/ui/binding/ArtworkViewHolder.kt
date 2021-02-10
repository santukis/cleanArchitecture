package com.santukis.cleanarchitecture.artwork.ui.binding

import androidx.navigation.findNavController
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.ui.fragments.ArtworksFragmentDirections
import com.santukis.cleanarchitecture.databinding.ElementArtworkItemBinding

class ArtworkViewHolder(binding: ElementArtworkItemBinding): ItemsAdapter.ItemBindingViewHolder<Artwork, ElementArtworkItemBinding>(binding) {

    override fun bind(value: Artwork, position: Int) {
        binding.artwork = value
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: Artwork, position: Int) {
        binding.root.setOnClickListener {
            it.findNavController().navigate(ArtworksFragmentDirections.openArtworkDetail(item.id))
        }
    }
}