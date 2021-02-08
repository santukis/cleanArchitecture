package com.santukis.cleanarchitecture.artwork.ui.components

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.databinding.ElementArtworkItemBinding

class ArtworkViewHolder(binding: ElementArtworkItemBinding): ItemsAdapter.ItemBindingViewHolder<Artwork>(binding) {

    override fun bind(value: Artwork, position: Int) {
        (binding as? ElementArtworkItemBinding)?.apply {
            artwork = value
            Glide.with(this.root).load(value.image).diskCacheStrategy(DiskCacheStrategy.ALL).into(image)
        }
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: Artwork, position: Int) {
        binding.root.setOnClickListener { onItemClickListener?.onItemClick(it, item) }
    }
}