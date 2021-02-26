package com.santukis.cleanarchitecture.core.ui.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.santukis.cleanarchitecture.R

object CommonBinding {

    @BindingAdapter("app:showThumbnail")
    @JvmStatic
    fun showThumbnail(view: AppCompatImageView, url: String?) {
        url?.apply {
            Glide.with(view.context)
                .load(this)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerInside()
                .thumbnail(0.1f)
                .override(200)
                .into(view)
        }
    }

    @BindingAdapter("app:showImage")
    @JvmStatic
    fun showImage(view: AppCompatImageView, url: String?) {
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
                .error(R.drawable.no_image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(512)
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