package com.pinkunicorp.photofilters.filters

import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.pinkunicorp.photofilters.R

class GrayscaleFilter(override val nameResId: Int = R.string.filter_grayscale) : IFilter {
    override fun applyFilter(imageView: ImageView?) {
        val brightness = 10f // change values to suite your need
        val colorMatrix = floatArrayOf(0.33f, 0.33f, 0.33f, 0f, brightness, 0.33f, 0.33f, 0.33f, 0f, brightness, 0.33f, 0.33f, 0.33f, 0f, brightness, 0f, 0f, 0f, 1f, 0f)
        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        imageView?.setColorFilter(colorFilter)
    }
}