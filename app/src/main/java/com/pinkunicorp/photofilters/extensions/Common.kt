package com.pinkunicorp.photofilters.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore.Images
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.pinkunicorp.photofilters.R
import java.io.*


fun Activity.getImageUri(inImage: Bitmap): Uri {
    try {
        val cachePath = File(getCacheDir(), "images")
        cachePath.mkdirs() // don't forget to make the directory
        val stream = FileOutputStream(cachePath.toString() + "/temp.jpg") // overwrites this image every time
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()

        val newFile = File(cachePath, "temp.jpg")
        val contentUri = FileProvider.getUriForFile(this, "com.pinkunicorp.photofilters.fileprovider", newFile)
        return contentUri
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return Uri.EMPTY
}