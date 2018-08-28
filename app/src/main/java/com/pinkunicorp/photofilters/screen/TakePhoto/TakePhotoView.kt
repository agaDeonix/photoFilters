package com.pinkunicorp.photofilters.screen.TakePhoto

import android.graphics.Bitmap
import android.net.Uri

interface TakePhotoView {
    fun showToast(message: String?)
    fun showProgress()
    fun hideProgress()

    fun takePhoto()
    fun loadPhoto()

    fun showErrorPhotoIsEmpty()
    fun showErrorPermissions()
    fun showErrorLoadPhoto()

    fun showPhotoHandler(bitmap: Bitmap)
    fun showPhotoHandler(bitmap: Uri)
}
