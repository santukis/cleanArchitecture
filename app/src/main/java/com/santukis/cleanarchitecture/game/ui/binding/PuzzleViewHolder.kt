package com.santukis.cleanarchitecture.game.ui.binding

import androidx.navigation.findNavController
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.databinding.ElementPuzzleItemBinding
import com.santukis.cleanarchitecture.game.domain.model.Difficulty
import com.santukis.cleanarchitecture.game.domain.model.Puzzle
import com.santukis.cleanarchitecture.game.ui.fragments.PuzzlesFragmentDirections

class PuzzleViewHolder(binding: ElementPuzzleItemBinding): ItemsAdapter.ItemBindingViewHolder<Puzzle, ElementPuzzleItemBinding>(binding) {

    override fun bind(value: Puzzle, position: Int) {
        binding.puzzle = value
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: Puzzle, position: Int) {
        super.setOnItemClickListener(onItemClickListener, item, position)
        binding.root.setOnClickListener {
            it.findNavController().navigate(PuzzlesFragmentDirections.openPuzzleGame(item.id, Difficulty.Medium.ordinal))
        }
    }
}