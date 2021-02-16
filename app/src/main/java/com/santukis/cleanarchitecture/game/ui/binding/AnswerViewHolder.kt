package com.santukis.cleanarchitecture.game.ui.binding

import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.databinding.ElementGameAnswerBinding

class AnswerViewHolder(binding: ElementGameAnswerBinding): ItemsAdapter.ItemBindingViewHolder<Artwork, ElementGameAnswerBinding>(binding) {

    override fun bind(value: Artwork, position: Int) {
        binding.answer = value
        binding.option.text = listOf("A", "B", "C").getOrNull(position) ?: ""
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: Artwork, position: Int) {
        binding.root.setOnClickListener { onItemClickListener?.onItemClick(binding.root, item) }
    }
}