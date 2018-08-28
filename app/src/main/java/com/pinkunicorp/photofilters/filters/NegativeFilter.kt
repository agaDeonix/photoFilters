package com.pinkunicorp.photofilters.filters

import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.pinkunicorp.photofilters.R
import android.graphics.ColorFilter
import android.graphics.ColorMatrix



class NegativeFilter(override val nameResId: Int = R.string.filter_negative) : IFilter {
    override fun applyFilter(imageView: ImageView?) {
        val negative = floatArrayOf(
                -1.0f, 0f, 0f, 0f, 255f, // red
                0f, -1.0f, 0f, 0f, 255f, // green
                0f, 0f, -1.0f, 0f, 255f, // blue
                0f, 0f, 0f, 1.0f, 0f  // alpha
        )
        val colorFilter = ColorMatrixColorFilter(negative)
        imageView?.setColorFilter(colorFilter)
    }
}