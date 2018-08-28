package com.pinkunicorp.photofilters.filters

import android.graphics.PorterDuff
import android.widget.ImageView
import com.pinkunicorp.photofilters.R

class NoneFilter(override val nameResId: Int = R.string.filter_none) : IFilter {
    override fun applyFilter(imageView: ImageView?) {
        imageView?.setColorFilter(null)
    }
}