package com.santukis.cleanarchitecture.artwork.ui.binding

import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.artwork.domain.model.Color
import com.santukis.cleanarchitecture.databinding.ElementColorItemBinding

class ColorViewHolder(binding: ElementColorItemBinding): ItemsAdapter.ItemBindingViewHolder<Color, ElementColorItemBinding>(binding) {

    override fun bind(value: Color, position: Int) {
        binding.color = value
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: Color, position: Int) {
        binding.root.setOnClickListener {

        }
    }
}