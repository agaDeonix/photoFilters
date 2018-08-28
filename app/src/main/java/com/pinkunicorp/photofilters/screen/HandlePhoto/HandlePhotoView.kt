package com.pinkunicorp.photofilters.screen.HandlePhoto

import com.pinkunicorp.photofilters.filters.IFilter

interface HandlePhotoView {

    fun initFiltersList(filters: List<IFilter>)
    fun applyFilter(filter: IFilter)
}