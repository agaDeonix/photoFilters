package com.pinkunicorp.photofilters.screen.HandlePhoto

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinkunicorp.photofilters.R
import com.pinkunicorp.photofilters.filters.*
import kotlinx.android.synthetic.main.fragment_handle_photo.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class HandlePhotoFragment : Fragment(), FiltersAdapter.FilterSelectListener {

    private val mFilters = arrayListOf(
            NoneFilter(),
            GrayscaleFilter(),
            SepiaFilter(),
            NegativeFilter(),
            BinaryFilter()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_handle_photo, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            ivPhoto?.setImageURI(it.getParcelable(KEY_IMAGE))
        }
        btnShare?.setOnClickListener {
            ivPhoto?.buildDrawingCache(true)
            shareImage(ivPhoto?.getDrawingCache(true))
//            shareImage((ivPhoto?.drawable as? BitmapDrawable)?.bitmap)
        }
        btnBack?.setOnClickListener {
            activity?.onBackPressed()
        }

        val filtersAdapter = FiltersAdapter(mFilters, this, context)
        initFiltersListOrientation(activity?.getResources()?.getConfiguration()?.orientation ?: Configuration.ORIENTATION_PORTRAIT)
        rvFilters?.setAdapter(filtersAdapter)
    }

    private fun shareImage(bitmap: Bitmap?) {
        context?.let {
            try {
                val cachePath = File(it.getCacheDir(), "images")
                cachePath.mkdirs() // don't forget to make the directory
                val stream = FileOutputStream(cachePath.toString() + "/image.jpg") // overwrites this image every time
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.close()

                val newFile = File(cachePath, "image.jpg")
                val contentUri = FileProvider.getUriForFile(it, "com.pinkunicorp.photofilters.fileprovider", newFile)

//                val share = Intent(Intent.ACTION_SEND)
//                share.type = "image/*"
//                share.putExtra(Intent.EXTRA_STREAM, contentUri)
//                startActivity(Intent.createChooser(share, activity?.getString(R.string.share_via)))

                if (contentUri != null) {

                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
                    shareIntent.setDataAndType(contentUri, it.getContentResolver().getType(contentUri))
                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                    startActivity(Intent.createChooser(shareIntent, activity?.getString(R.string.share_via)))

                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun select(filter: IFilter) {
        filter.applyFilter(ivPhoto)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        newConfig?.let {
            initFiltersListOrientation(it.orientation)
        }
    }

    fun initFiltersListOrientation(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvFilters?.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))
        } else {
            rvFilters?.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false))
        }
    }

    companion object {
        private val KEY_IMAGE = "KEY_IMAGE"
        fun newInstance(image: Uri): HandlePhotoFragment {
            val arguments = Bundle()
            arguments.putParcelable(KEY_IMAGE, image)
            val fragment = HandlePhotoFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

}