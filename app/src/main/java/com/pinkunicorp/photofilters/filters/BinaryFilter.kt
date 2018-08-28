package com.pinkunicorp.photofilters.filters

import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.pinkunicorp.photofilters.R
import android.graphics.ColorMatrix



class BinaryFilter(override val nameResId: Int = R.string.filter_binary) : IFilter {
    override fun applyFilter(imageView: ImageView?) {
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)

        val m = 255f
        val t = -255 * 128f
        val threshold = ColorMatrix(floatArrayOf(m, 0f, 0f, 1f, t, 0f, m, 0f, 1f, t, 0f, 0f, m, 1f, t, 0f, 0f, 0f, 1f, 0f))
        // Convert to grayscale, then scale and clamp
        colorMatrix.postConcat(threshold)
        imageView?.setColorFilter(ColorMatrixColorFilter(colorMatrix))
    }
}