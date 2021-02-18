package com.santukis.cleanarchitecture.artwork.ui.binding

import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.ui.fragments.ArtworksFragmentDirections
import com.santukis.cleanarchitecture.databinding.ElementArtworkItemBinding
import com.santukis.cleanarchitecture.databinding.ElementFavouriteItemBinding

class ArtworkViewHolder<Binding: ViewBinding>(binding: Binding): ItemsAdapter.ItemBindingViewHolder<Artwork, ViewBinding>(binding) {

    override fun bind(value: Artwork, position: Int) {
        when(binding) {
            is ElementArtworkItemBinding -> binding.artwork = value
            is ElementFavouriteItemBinding -> binding.artwork = value
        }
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: Artwork, position: Int) {
        binding.root.setOnClickListener {
            it.findNavController().navigate(ArtworksFragmentDirections.openArtworkDetail(item.id))
        }
    }
}