package com.santukis.cleanarchitecture.game.ui.fragments

import android.os.Bundle
import android.view.*
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
import com.santukis.cleanarchitecture.databinding.ElementPuzzleItemBinding
import com.santukis.cleanarchitecture.databinding.FragmentPuzzlesBinding
import com.santukis.cleanarchitecture.game.domain.model.Difficulty
import com.santukis.cleanarchitecture.game.domain.model.Puzzle
import com.santukis.cleanarchitecture.game.ui.binding.PuzzleViewHolder

class PuzzlesFragment : BaseFragment<FragmentPuzzlesBinding>(), OnItemClickListener {

    private val puzzleAdapter: ItemsAdapter<Puzzle> by lazy {
        ItemsAdapter(ViewHolderProvider(itemViewHolder = { parent, viewType ->
            PuzzleViewHolder(ElementPuzzleItemBinding.inflate(layoutInflater, parent, false))
        }))
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentPuzzlesBinding =
        FragmentPuzzlesBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentPuzzlesBinding) {
        super.initializeViewComponents(binding)
        binding.viewmodel = gameViewModel
        binding.recycler.layoutManager = StaggeredGridLayoutManager(2, VERTICAL).apply { gapStrategy = GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS }
        binding.recycler.addItemDecoration(MarginItemDecoration(left = 10, right = 10, top = 10, bottom = 10))
        binding.recycler.adapter = puzzleAdapter
        binding.medium.isSelected = true
        binding.mediumText.isSelected = true

        setHasOptionsMenu(true)
    }

    override fun initializeViewListeners(binding: FragmentPuzzlesBinding) {
        super.initializeViewListeners(binding)
        puzzleAdapter.addOnItemClickListener(this)
        gameViewModel?.difficulty?.observe(this) { difficulty ->
            binding.easy.isSelected = difficulty == Difficulty.Easy
            binding.easyText.isSelected = difficulty == Difficulty.Easy
            binding.medium.isSelected = difficulty == Difficulty.Medium
            binding.mediumText.isSelected = difficulty == Difficulty.Medium
            binding.hard.isSelected = difficulty == Difficulty.Hard
            binding.hardText.isSelected = difficulty == Difficulty.Hard
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.ongoing_puzzle, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.ongoing_puzzles -> {
                findNavController().navigate(R.id.openOngoingPuzzles)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        gameViewModel?.loadPuzzles()?.observe(this) { response ->
            binding.progress.isVisible = response is Response.Loading

            when (response) {
                is Response.Success -> puzzleAdapter.showItems(response.data) { p1, p2 -> p1.id == p2.id }
                is Response.Error -> Toast.makeText(binding.root.context, "Unable to load Puzzles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(view: View, item: Any) {
        if (item is Puzzle) {
            findNavController().navigate(
                PuzzlesFragmentDirections.openPuzzleGame(
                    item.id,
                    gameViewModel?.difficulty?.value?.ordinal ?: Difficulty.Easy.ordinal
                )
            )
        }
    }
}