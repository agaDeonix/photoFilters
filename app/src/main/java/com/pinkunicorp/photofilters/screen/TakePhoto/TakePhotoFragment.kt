package com.pinkunicorp.photofilters.screen.TakePhoto

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.pinkunicorp.photofilters.R
import com.pinkunicorp.photofilters.extensions.getImageUri
import com.pinkunicorp.photofilters.screen.Navigator
import kotlinx.android.synthetic.main.fragment_take_photo.*
import java.io.IOException


class TakePhotoFragment : Fragment() {

    private val GALLERY: Int = 1000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_take_photo, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()
        cameraView?.start()
        btnTakePhoto?.setOnClickListener {
            cameraView.captureImage { cameraKitImage ->
                handleImage(cameraKitImage.bitmap)
            }
        }
        btnGallery?.setOnClickListener {
            choosePhotoFromGallary()
        }
        btnToggleCamera?.setOnClickListener {
            cameraView?.toggleFacing()
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
                    (activity as? Navigator)?.showHandlePhoto(contentURI)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, activity?.getString(R.string.error_load_image), Toast.LENGTH_SHORT).show()
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

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        newConfig?.let {
            cameraView?.stop()
            cameraView?.start()
        }
    }
}