package com.pinkunicorp.photofilters.screen.TakePhoto

import android.graphics.Bitmap
import android.net.Uri

class TakePhotoPresenter {

    private var mView: TakePhotoView? = null

    fun attachView(view: TakePhotoView?){
        mView = view
    }

    fun requestPhoto() {
        mView?.showProgress()
        mView?.takePhoto()
    }

    fun setPhoto(bitmap: Bitmap?) {
        mView?.hideProgress()
        if (bitmap != null) {
            mView?.showPhotoHandler(bitmap)
        } else {
            mView?.showErrorPhotoIsEmpty()
        }
    }

    fun setError(error: Int) {
        when(error){
            ERROR_PERMISSIONS -> mView?.showErrorPermissions()
        }
    }

    fun loadPhoto() {
        mView?.showProgress()
        mView?.loadPhoto()
    }

    fun setPhoto(bitmap: Uri?) {
        mView?.hideProgress()
        if (bitmap != null) {
            mView?.showPhotoHandler(bitmap)
        } else {
            mView?.showErrorLoadPhoto()
        }
    }

    companion object {
        const val ERROR_PERMISSIONS = 1
    }


}