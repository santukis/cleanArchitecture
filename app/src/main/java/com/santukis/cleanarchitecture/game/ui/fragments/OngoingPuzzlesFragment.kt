package com.santukis.cleanarchitecture.game.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.*
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.frikiplanet.proteo.ViewHolderProvider
import com.frikiplanet.proteo.decorations.MarginItemDecoration
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.ElementOngoingPuzzleItemBinding
import com.santukis.cleanarchitecture.databinding.ElementPuzzleItemBinding
import com.santukis.cleanarchitecture.databinding.FragmentOngoingPuzzlesBinding
import com.santukis.cleanarchitecture.game.domain.model.Puzzle
import com.santukis.cleanarchitecture.game.ui.binding.OngoingPuzzleViewHolder
import com.santukis.cleanarchitecture.game.ui.binding.PuzzleViewHolder

class OngoingPuzzlesFragment: BaseFragment<FragmentOngoingPuzzlesBinding>(), OnItemClickListener {

    private val puzzleAdapter: ItemsAdapter<Puzzle> by lazy {
        ItemsAdapter(ViewHolderProvider(itemViewHolder = { parent, viewType ->
            OngoingPuzzleViewHolder(ElementOngoingPuzzleItemBinding.inflate(layoutInflater, parent, false))
        }))
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentOngoingPuzzlesBinding =
        FragmentOngoingPuzzlesBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentOngoingPuzzlesBinding) {
        super.initializeViewComponents(binding)
        binding.recycler.layoutManager = StaggeredGridLayoutManager(2, VERTICAL).apply { gapStrategy = GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS }
        binding.recycler.addItemDecoration(MarginItemDecoration(left = 10, right = 10, top = 10, bottom = 10))
        binding.recycler.adapter = puzzleAdapter
    }

    override fun initializeViewListeners(binding: FragmentOngoingPuzzlesBinding) {
        super.initializeViewListeners(binding)
        puzzleAdapter.addOnItemClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        gameViewModel?.loadOngoingPuzzles()?.observe(this) { response ->
            binding.progress.isVisible = response is Response.Loading

            when (response) {
                is Response.Success -> puzzleAdapter.showItems(response.data) { p1, p2 -> p1.id == p2.id }
                is Response.Error -> Toast.makeText(binding.root.context, "No saved Puzzles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(view: View, item: Any) {
        if (item is Puzzle) {
            findNavController().navigate(
                PuzzlesFragmentDirections.openPuzzleGame(item.id, item.difficulty.ordinal)
            )
        }
    }
}