package com.santukis.cleanarchitecture.artwork.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.ViewHolderProvider
import com.frikiplanet.proteo.addEndlessScrollListener
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.ui.binding.ArtworkViewHolder
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.ElementArtworkItemBinding
import com.santukis.cleanarchitecture.databinding.FragmentArtworksBinding
import kotlinx.coroutines.flow.collect
import java.util.concurrent.atomic.AtomicInteger

class ArtworksFragment: BaseFragment<FragmentArtworksBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentArtworksBinding =
        FragmentArtworksBinding.inflate(inflater, container, false)

    private val artworksAdapter: ItemsAdapter<Artwork> by lazy {
        ItemsAdapter(ViewHolderProvider { parent, viewType -> ArtworkViewHolder(ElementArtworkItemBinding.inflate(layoutInflater, parent, false)) })
    }

    override fun initializeViewComponents(binding: FragmentArtworksBinding) {
        super.initializeViewComponents(binding)
        binding.recycler.adapter = artworksAdapter
    }

    val counter = AtomicInteger(0)

    override fun initializeViewListeners(binding: FragmentArtworksBinding) {
        super.initializeViewListeners(binding)
        lifecycleScope.launchWhenStarted {
            binding.recycler.addEndlessScrollListener(threshold = 3).collect { lastItemPosition ->
                artworkViewModel?.loadArtworks(lastItemPosition)
            }
        }

        artworkViewModel?.artworks?.observe(viewLifecycleOwner) { response ->
            binding.progress.isVisible = response is Response.Loading

            context?.apply {
                Toast.makeText(this, "Response: ${response::class.java.simpleName} ${counter.incrementAndGet()}", Toast.LENGTH_SHORT).show()
            }

            when (response) {
                is Response.Success -> artworksAdapter.showItems(response.data) { a1, a2 -> a1.id == a2.id }
                is Response.Error -> {}
            }
        }
    }
}