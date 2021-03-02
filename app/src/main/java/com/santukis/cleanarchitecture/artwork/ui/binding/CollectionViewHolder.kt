package com.santukis.cleanarchitecture.artwork.ui.binding

import androidx.navigation.findNavController
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.santukis.cleanarchitecture.artwork.domain.model.ArtworkCollection
import com.santukis.cleanarchitecture.artwork.ui.fragments.CollectionsFragmentDirections
import com.santukis.cleanarchitecture.databinding.ElementCollectionBinding

class CollectionViewHolder(binding: ElementCollectionBinding): ItemsAdapter.ItemBindingViewHolder<ArtworkCollection, ElementCollectionBinding>(binding) {

    override fun bind(value: ArtworkCollection, position: Int) {
        binding.collection = value
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener?, item: ArtworkCollection, position: Int) {
        binding.name.setOnClickListener {
            it.findNavController().navigate(CollectionsFragmentDirections.openCollectionDetail(item.collection.name))
        }
    }
}