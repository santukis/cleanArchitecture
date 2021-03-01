package com.santukis.cleanarchitecture.artwork.ui.binding

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.frikiplanet.proteo.ItemsAdapter
import com.frikiplanet.proteo.ViewHolderProvider
import com.frikiplanet.proteo.decorations.MarginItemDecoration
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.santukis.cleanarchitecture.R
import com.santukis.cleanarchitecture.artwork.domain.model.Color
import com.santukis.cleanarchitecture.artwork.domain.model.Material
import com.santukis.cleanarchitecture.artwork.domain.model.Technique
import com.santukis.cleanarchitecture.databinding.ElementColorItemBinding

object ArtworkBinding {

    @BindingAdapter("app:showColors")
    @JvmStatic
    fun showColors(view: RecyclerView, colors: List<Color>?) {
        colors?.let {
            if (view.adapter == null) {
                view.adapter = ItemsAdapter(ViewHolderProvider { parent, viewType ->
                    ColorViewHolder(ElementColorItemBinding.inflate(LayoutInflater.from(view.context), parent, false))
                })
                view.addItemDecoration(MarginItemDecoration(left = 10))
            }

            (view.adapter as? ItemsAdapter<Color>)?.showItems(colors)
        }
    }

    @BindingAdapter("app:showMaterials")
    @JvmStatic
    fun showMaterials(view: ChipGroup, materials: List<Material>?) {
        materials?.let {
            view.removeAllViews()
            materials.forEach { view.addView(view.context.createChipFor(it.material)) }
        }
    }

    @BindingAdapter("app:showTechniques")
    @JvmStatic
    fun showTechniques(view: ChipGroup, techniques: List<Technique>?) {
        techniques?.let {
            view.removeAllViews()
            techniques.forEach { view.addView(view.context.createChipFor(it.technique)) }
        }
    }

    @SuppressWarnings("NewApi")
    @BindingAdapter("app:showHtmlText")
    @JvmStatic
    fun showHtmlText(view: AppCompatTextView, text: String?) {
        text?.let { html ->
            view.text = when(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                true -> Html.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
                else -> Html.fromHtml(html)
            }
        }
    }

    private fun Context.createChipFor(text: String) = Chip(this).apply {
        isCheckable = false
        chipStrokeColor = ContextCompat.getColorStateList(this@createChipFor, R.color.colorPrimary)
        chipStrokeWidth = 2f
        chipBackgroundColor = ContextCompat.getColorStateList(this@createChipFor, android.R.color.transparent)
        shapeAppearanceModel = ShapeAppearanceModel.Builder().setAllCorners(CornerFamily.ROUNDED, resources.getDimension(R.dimen.large_radius)).build()
        this.text = text
    }
}