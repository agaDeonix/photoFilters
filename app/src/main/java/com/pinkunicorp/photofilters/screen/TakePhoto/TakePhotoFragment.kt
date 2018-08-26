package com.pinkunicorp.photofilters.screen.TakePhoto

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pinkunicorp.photofilters.R
import kotlinx.android.synthetic.main.fragment_take_photo.*
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.pinkunicorp.photofilters.extensions.getImageUri
import com.pinkunicorp.photofilters.screen.Navigator
import java.io.IOException


class TakePhotoFragment : Fragment() {

    private val GALLERY: Int = 1000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_take_photo, container, false)
    }

    override fun onResume() {
        super.onResume()
        cameraView?.start()
        btnTakePhoto?.setOnClickListener {
            // cameraView is a custom View which provides camera preview
            cameraView.captureImage { cameraKitImage ->
                // Get the Bitmap from the captured shot and use it to make the API call
                Toast.makeText(activity, "photo taked", Toast.LENGTH_SHORT)?.show()
                handleImage(cameraKitImage.bitmap)
            }
        }
        btnGallery?.setOnClickListener {
            choosePhotoFromGallary()
        }
    }

    override fun onPause() {
        cameraView?.stop()
        super.onPause()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
//                    val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
                    Toast.makeText(activity, "Image Saved!", Toast.LENGTH_SHORT)?.show()
                    (activity as? Navigator)?.showHandlePhoto(contentURI)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    private fun handleImage(image: Bitmap?){
        image?.let {
            askPermission {
                (activity as? Navigator)?.showHandlePhoto(activity!!.getImageUri(image))
            }.onDeclined { e ->
                Toast.makeText(activity, R.string.need_setup_permissions, Toast.LENGTH_SHORT).show()
            }
        }
    }
}