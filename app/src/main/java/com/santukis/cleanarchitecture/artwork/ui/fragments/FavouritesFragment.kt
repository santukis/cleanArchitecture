package com.santukis.cleanarchitecture.artwork.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.ViewHolderProvider
import com.frikiplanet.proteo.decorations.MarginItemDecoration
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.artwork.ui.binding.FavouriteViewHolder
import com.santukis.cleanarchitecture.core.domain.model.Response
import com.santukis.cleanarchitecture.core.ui.fragments.BaseFragment
import com.santukis.cleanarchitecture.databinding.ElementFavouriteItemBinding
import com.santukis.cleanarchitecture.databinding.FragmentFavouritesBinding

class FavouritesFragment: BaseFragment<FragmentFavouritesBinding>() {

    private val artworksAdapter: ItemsAdapter<Artwork> by lazy {
        ItemsAdapter(ViewHolderProvider { parent, viewType -> FavouriteViewHolder(ElementFavouriteItemBinding.inflate(layoutInflater, parent, false)) })
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavouritesBinding =
        FragmentFavouritesBinding.inflate(inflater, container, false)

    override fun initializeViewComponents(binding: FragmentFavouritesBinding) {
        super.initializeViewComponents(binding)
        binding.recycler.adapter = artworksAdapter
        binding.recycler.addItemDecoration(MarginItemDecoration(bottom = 20))
    }

    override fun initializeViewListeners(binding: FragmentFavouritesBinding) {
        super.initializeViewListeners(binding)

        artworkViewModel?.favourites?.observe(this) { response ->

            when(response) {
                is Response.Success -> artworksAdapter.showItems(response.data)
                is Response.Error -> Toast.makeText(binding.root.context, "No Favourites", Toast.LENGTH_SHORT).show()
            }
        }
    }
}