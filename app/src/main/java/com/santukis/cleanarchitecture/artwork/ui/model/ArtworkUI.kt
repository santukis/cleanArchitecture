package com.santukis.cleanarchitecture.artwork.ui.model

import android.graphics.Color

fun toRGB(color: String?): Int = try {
    when (color.isNullOrEmpty()) {
        false -> Color.parseColor(color)
        true -> Color.BLACK
    }
} catch (exception: Exception) {
    Color.BLACK
}