package com.pinkunicorp.photofilters.screen.TakePhoto

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.pinkunicorp.photofilters.R
import com.pinkunicorp.photofilters.extensions.getImageUri
import com.pinkunicorp.photofilters.screen.Navigator
import kotlinx.android.synthetic.main.fragment_take_photo.*
import kotlinx.android.synthetic.main.part_progress.*
import java.io.IOException


class TakePhotoFragment : Fragment(), TakePhotoView {

    companion object {
        private const val GALLERY: Int = 1000
    }

    private val mPresenter: TakePhotoPresenter? = TakePhotoPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_take_photo, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter?.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        cameraView?.start()
        btnTakePhoto?.setOnClickListener {
            mPresenter?.requestPhoto()
        }
        btnGallery?.setOnClickListener {
            mPresenter?.loadPhoto()
        }
        btnToggleCamera?.setOnClickListener {
            cameraView?.toggleFacing()
        }
    }

    override fun onPause() {
        cameraView?.stop()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                mPresenter?.setPhoto(contentURI)
            }

        }
    }

    override fun showErrorLoadPhoto() {
        Toast.makeText(activity, activity?.getString(R.string.error_load_image), Toast.LENGTH_SHORT).show()
    }

    override fun showPhotoHandler(bitmap: Uri) {
        (activity as? Navigator)?.showHandlePhoto(bitmap)
    }

    override fun loadPhoto() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }

    override fun showToast(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progress?.visibility = View.VISIBLE
        btnToggleCamera?.isEnabled = false
        btnTakePhoto?.isEnabled = false
        btnGallery?.isEnabled = false
    }

    override fun hideProgress() {
        Handler(Looper.getMainLooper()).post{
            progress?.visibility = View.GONE
            btnToggleCamera?.isEnabled = true
            btnTakePhoto?.isEnabled = true
            btnGallery?.isEnabled = true
        }
    }

    override fun takePhoto() {
        cameraView.captureImage { cameraKitImage ->
            mPresenter?.setPhoto(cameraKitImage.bitmap)
        }
    }

    override fun showPhotoHandler(bitmap: Bitmap) {
        askPermission {
            (activity as? Navigator)?.showHandlePhoto(activity!!.getImageUri(bitmap))
        }.onDeclined { e ->
            mPresenter?.setError(TakePhotoPresenter.ERROR_PERMISSIONS)
        }
    }

    override fun showErrorPhotoIsEmpty() {
        Toast.makeText(activity, activity?.getString(R.string.error_take_photo), Toast.LENGTH_SHORT).show()
    }
    override fun showErrorPermissions() {
        Toast.makeText(activity, activity?.getString(R.string.need_setup_permissions), Toast.LENGTH_SHORT).show()
    }

}