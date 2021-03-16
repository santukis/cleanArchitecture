package com.santukis.cleanarchitecture.game.ui.binding

import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.databinding.ElementOngoingPuzzleItemBinding
import com.santukis.cleanarchitecture.game.domain.model.Puzzle

class OngoingPuzzleViewHolder(binding: ElementOngoingPuzzleItemBinding): ItemsAdapter.ItemBindingViewHolder<Puzzle, ElementOngoingPuzzleItemBinding>(binding) {

    override fun bind(value: Puzzle, position: Int) {
        binding.puzzle = value
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: Puzzle, position: Int) {
        binding.root.setOnClickListener { onItemClickListener?.onItemClick(it, item) }
    }
}