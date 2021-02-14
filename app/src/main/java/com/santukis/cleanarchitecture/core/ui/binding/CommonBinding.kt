package com.santukis.cleanarchitecture.core.ui.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object CommonBinding {

    @BindingAdapter("app:showThumbnail")
    @JvmStatic
    fun showTumbnail(view: AppCompatImageView, url: String?) {
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
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(512)
                .into(view)
        }
    }
}