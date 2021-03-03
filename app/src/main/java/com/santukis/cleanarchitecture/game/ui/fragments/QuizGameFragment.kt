package com.santukis.cleanarchitecture.game.ui.fragments

import android.view.*
import androidx.navigation.fragment.findNavController
import com.frikiplanet.proteo.FragmentsAdapter
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.FragmentQuizGameBinding

class QuizGameFragment: BaseFragment<FragmentQuizGameBinding>() {

    private var fragmentsAdapter: FragmentsAdapter? = null

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentQuizGameBinding =
        FragmentQuizGameBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentQuizGameBinding) {
        super.initializeViewComponents(binding)

        fragmentsAdapter = FragmentsAdapter(this)
        binding.fragmentContainer.adapter = fragmentsAdapter
        binding.fragmentContainer.isUserInputEnabled = false
        fragmentsAdapter?.showFragments(listOf(QuizQuestionFragment(), QuizAnswerFragment()))

        setHasOptionsMenu(true)
    }

    override fun initializeViewListeners(binding: FragmentQuizGameBinding) {
        super.initializeViewListeners(binding)
        gameViewModel?.screen?.observe(viewLifecycleOwner) { screen ->
            binding.fragmentContainer.setCurrentItem(screen, true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.game_score, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.game_score -> {
                findNavController().navigate(R.id.action_gameFragment_to_gameScoreFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}