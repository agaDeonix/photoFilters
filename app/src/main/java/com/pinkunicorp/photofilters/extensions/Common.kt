package com.pinkunicorp.photofilters.extensions

import android.app.Activity
import android.content.Context
import android.provider.MediaStore.Images
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream


fun Activity.getImageUri(inImage: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(contentResolver, inImage, "Photo Filters Temp", null);
    return Uri.parse(path)
}