package com.santukis.cleanarchitecture.artwork.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.ViewHolderProvider
import com.santukis.cleanarchitecture.artwork.domain.model.ArtworkCollection
import com.santukis.cleanarchitecture.artwork.ui.binding.CollectionViewHolder
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.ElementCollectionBinding
import com.santukis.cleanarchitecture.databinding.FragmentCollectionsBinding

class CollectionsFragment: BaseFragment<FragmentCollectionsBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCollectionsBinding =
        FragmentCollectionsBinding.inflate(inflater, container, false)

    private val collectionsAdapter: ItemsAdapter<ArtworkCollection> by lazy {
        ItemsAdapter(ViewHolderProvider { parent, viewType -> CollectionViewHolder(ElementCollectionBinding.inflate(layoutInflater, parent, false)) })
    }

    override fun initializeViewComponents(binding: FragmentCollectionsBinding) {
        super.initializeViewComponents(binding)
        binding.recycler.adapter = collectionsAdapter
    }

    override fun initializeViewListeners(binding: FragmentCollectionsBinding) {
        super.initializeViewListeners(binding)

        artworkViewModel?.collections?.observe(this) { response ->
            when(response) {
                is Response.Success -> collectionsAdapter.showItems(response.data)
                is Response.Error -> {}
            }
        }
    }
}