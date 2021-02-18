package com.santukis.cleanarchitecture.artwork.ui.binding

import androidx.navigation.findNavController
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.ui.fragments.FavouritesFragmentDirections
import com.santukis.cleanarchitecture.databinding.ElementFavouriteItemBinding

class FavouriteViewHolder(binding: ElementFavouriteItemBinding): ItemsAdapter.ItemBindingViewHolder<Artwork, ElementFavouriteItemBinding>(binding) {

    override fun bind(value: Artwork, position: Int) {
        binding.artwork = value
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: Artwork, position: Int) {
        binding.root.setOnClickListener {
            it.findNavController().navigate(FavouritesFragmentDirections.openFavouriteDetail(item.id))
        }
    }
}