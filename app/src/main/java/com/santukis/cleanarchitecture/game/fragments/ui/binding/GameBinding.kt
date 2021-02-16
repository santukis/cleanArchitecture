package com.santukis.cleanarchitecture.game.fragments.ui.binding

import android.view.LayoutInflater
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.ViewHolderProvider
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.databinding.ElementGameAnswerBinding

object GameBinding {

    @BindingAdapter("app:showAnswers")
    @JvmStatic
    fun showAnswers(view: RecyclerView, artworks: List<Artwork>?) {
        if (artworks.isNullOrEmpty()) return

        if (view.adapter == null) {
            view.adapter = ItemsAdapter(ViewHolderProvider { parent, viewType ->
                AnswerViewHolder(ElementGameAnswerBinding.inflate(LayoutInflater.from(view.context), parent, false))
            })
            view.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        }

        (view.adapter as? ItemsAdapter<Artwork>)?.showItems(artworks)
    }
}