package com.santukis.cleanarchitecture.artwork.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.ViewHolderProvider
import com.frikiplanet.proteo.addEndlessScrollListener
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.ui.components.ArtworkViewHolder
import com.santukis.cleanarchitecture.artwork.ui.viewmodels.ArtworkViewModel
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.databinding.ElementArtworkItemBinding
import com.santukis.cleanarchitecture.databinding.FragmentArtworksBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class ArtworksFragment: Fragment(), DIAware {

    override val di: DI by di()

    private val viewModelFactory: ViewModelProvider.Factory by instance()

    private val artworkViewModel: ArtworkViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory).get(ArtworkViewModel::class.java)
    }

    private lateinit var binding: FragmentArtworksBinding

    private val artworksAdapter: ItemsAdapter<Artwork> by lazy {
        ItemsAdapter(ViewHolderProvider { parent, viewType -> ArtworkViewHolder(ElementArtworkItemBinding.inflate(layoutInflater, parent, false)) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentArtworksBinding.inflate(inflater, container, false)
        binding.recycler.adapter = artworksAdapter
        binding.recycler.addEndlessScrollListener(threshold = 3) { lastItemPosition ->
            artworkViewModel?.notifyLastVisible(lastItemPosition)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artworkViewModel?.artworks?.observe(viewLifecycleOwner) { response ->
            binding.progress.isVisible = response is Response.Loading

            when (response) {
                is Response.Success -> artworksAdapter.showItems(response.data) { a1, a2 -> a1.id == a2.id }
                is Response.Error -> {}
            }
        }

        artworkViewModel?.loadArtworks()
    }
}