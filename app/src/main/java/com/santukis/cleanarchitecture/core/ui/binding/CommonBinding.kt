package com.santukis.cleanarchitecture.core.ui.binding

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.santukis.cleanarchitecture.R

object CommonBinding {

    @BindingAdapter("app:showImage", "app:multiplier", requireAll = false)
    @JvmStatic
    fun showImage(view: AppCompatImageView, url: String?, multiplier: Float? = 1.0f) {
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

    @BindingAdapter("app:showImageResource")
    @JvmStatic
    fun showImageResource(view: AppCompatImageView, imageResource: Int?) {
        imageResource?.let { resource ->
            if (resource != 0) view.setImageResource(resource)
        }
    }

    fun changeViewVisibility(vararg views: View?, shouldBeShown: (() -> Boolean)? = { true }) {
        shouldBeShown?.let {
            views.forEach { view -> view?.isVisible = it.invoke() }
        }
    }

    fun toggleVisibility(binding: ViewBinding?) {
        binding?.root?.apply {
            isVisible = !isVisible
        }
    }
}