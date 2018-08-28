package com.pinkunicorp.photofilters.extensions

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.content.FileProvider
import java.io.*


fun Activity.getImageUri(inImage: Bitmap, name: String = "temp.jpg"): Uri {
    try {
        val cachePath = File(getCacheDir(), "images")
        cachePath.mkdirs() // don't forget to make the directory
        val stream = FileOutputStream("${cachePath.toString()}/$name") // overwrites this image every time
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
        val newFile = File(cachePath, name)
        return FileProvider.getUriForFile(this, "com.pinkunicorp.photofilters.fileprovider", newFile)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return Uri.EMPTY
}