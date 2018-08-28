package com.pinkunicorp.photofilters.screen.HandlePhoto

import com.pinkunicorp.photofilters.filters.*

class HandlePhotoModel {
    private val mFilters = arrayListOf(
            NoneFilter(),
            GrayscaleFilter(),
            SepiaFilter(),
            NegativeFilter(),
            BinaryFilter()
    )

    fun getFilters(): List<IFilter> = mFilters
}