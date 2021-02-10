package com.santukis.cleanarchitecture.artwork.ui.binding

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.ViewHolderProvider
import com.frikiplanet.proteo.decorations.MarginItemDecoration
import com.santukis.cleanarchitecture.artwork.domain.model.Color
import com.santukis.cleanarchitecture.databinding.ElementColorItemBinding

object ArtworkBinding {

    @BindingAdapter("app:showColors")
    @JvmStatic
    fun showColors(view: RecyclerView, colors: List<Color>?) {
        if (colors.isNullOrEmpty()) return

        if (view.adapter == null) {
            view.adapter = ItemsAdapter(ViewHolderProvider { parent, viewType ->
                ColorViewHolder(ElementColorItemBinding.inflate(LayoutInflater.from(view.context), parent, false))
            })
            view.addItemDecoration(MarginItemDecoration(left = 10))
        }

        (view.adapter as? ItemsAdapter<Color>)?.showItems(colors)
    }
}