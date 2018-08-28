package com.pinkunicorp.photofilters.filters

import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.pinkunicorp.photofilters.R
import android.graphics.ColorFilter
import android.graphics.ColorMatrix



class SepiaFilter(override val nameResId: Int = R.string.filter_sepia) : IFilter {
    override fun applyFilter(imageView: ImageView?) {
        val colorMatrix_Sepia = ColorMatrix()
        colorMatrix_Sepia.setSaturation(0f)
        val colorScale = ColorMatrix()
        colorScale.setScale(1f, 1f, 0.8f, 1f)
        colorMatrix_Sepia.postConcat(colorScale)
        val colorFilter = ColorMatrixColorFilter(colorMatrix_Sepia)
        imageView?.setColorFilter(colorFilter)
    }
}