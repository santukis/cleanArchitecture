package com.santukis.cleanarchitecture.game.ui.binding

import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.OnItemClickListener
import com.frikiplanet.proteo.ViewHolderProvider
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.artwork.domain.model.Artwork
import com.santukis.cleanarchitecture.databinding.ElementGameAnswerBinding
import com.santukis.cleanarchitecture.game.domain.model.Answer

object GameBinding {

    @BindingAdapter("app:showAnswers", "app:onAnswerClick")
    @JvmStatic
    fun showAnswers(view: RecyclerView, artworks: List<Answer>?, onItemClickListener: OnItemClickListener?) {
        if (view.adapter == null) {
            view.adapter = ItemsAdapter(ViewHolderProvider { parent, viewType ->
                AnswerViewHolder(ElementGameAnswerBinding.inflate(LayoutInflater.from(view.context), parent, false))
            })
            view.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        }

        (view.adapter as? ItemsAdapter<Answer>)?.apply {
            addOnItemClickListener(onItemClickListener)
            artworks?.apply { showItems(artworks) }
        }
    }

    @BindingAdapter("app:setAnwserIcon")
    @JvmStatic
    fun setAnswerIcon(view: AppCompatImageView, isRightAnswer: Boolean?) {
        when(isRightAnswer) {
            true -> view.setImageResource(R.drawable.ic_right)
            else -> view.setImageResource(R.drawable.ic_wrong)
        }
    }


}