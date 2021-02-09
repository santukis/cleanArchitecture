package com.santukis.cleanarchitecture.core.ui.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object CommonBinding {

    @BindingAdapter("app:showImage")
    @JvmStatic
    fun showImage(view: AppCompatImageView, url: String?) {
        url?.apply {
            Glide.with(view.context)
                .load(this)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.3f)
                .into(view)
        }
    }
}