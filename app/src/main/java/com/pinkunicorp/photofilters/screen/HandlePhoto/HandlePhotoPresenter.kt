package com.pinkunicorp.photofilters.screen.HandlePhoto

import android.os.Bundle

class HandlePhotoPresenter(private var mModel: HandlePhotoModel) {

    private var mView: HandlePhotoView? = null
    private var mCurrentFilter = 0

    fun attachView(view: HandlePhotoView?){
        mView = view
    }

    fun initFiltersList() {
        mView?.initFiltersList(mModel.getFilters())
    }

    fun onRestoreState(savedInstanceState: Bundle?) {
        mCurrentFilter = savedInstanceState?.getInt(KEY_CURRENT_FILTER) ?: 0
    }

    fun onSaveState(outState: Bundle?) {
        outState?.putInt(KEY_CURRENT_FILTER, mCurrentFilter)
    }

    fun onResume() {
        mView?.applyFilter(mModel.getFilters()[mCurrentFilter])
    }

    fun changeFilter(index: Int) {
        mCurrentFilter = index
        onResume()
    }

    companion object {
        private const val KEY_CURRENT_FILTER = "KEY_CURRENT_FILTER"
    }
}