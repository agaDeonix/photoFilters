package com.pinkunicorp.photofilters.filters

import android.widget.ImageView

interface IFilter {
    val nameResId:Int
    fun applyFilter(imageView: ImageView?)
}