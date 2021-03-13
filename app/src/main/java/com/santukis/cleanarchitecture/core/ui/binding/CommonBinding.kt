package com.santukis.cleanarchitecture.core.ui.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.santukis.cleanarchitecture.R

object CommonBinding {

    @BindingAdapter("app:showImage", "app:multiplier", requireAll = false)
    @JvmStatic
    fun showImage(view: AppCompatImageView, url: String?, multiplier: Float?) {
        url?.apply {
            Glide.with(view.context)
                .load(this)
                .placeholder(
                    CircularProgressDrawable(view.context).apply {
                        strokeWidth = 10f
                        centerRadius = 50f
                        setColorSchemeColors(ContextCompat.getColor(view.context, R.color.colorAccent))
                        start()
                    })
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .sizeMultiplier(multiplier ?: 1f)
                .placeholder(R.color.light_gray)
                .into(view)
        }
    }

    @BindingAdapter("app:setTextResource")
    @JvmStatic
    fun setTextResource(view: AppCompatTextView, textResource: Int?) {
        textResource?.let { resource ->
            if (resource != 0) view.setText(resource)
        }
    }
}