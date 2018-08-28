package com.pinkunicorp.photofilters.screen.HandlePhoto

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinkunicorp.photofilters.R
import com.pinkunicorp.photofilters.extensions.getImageUri
import com.pinkunicorp.photofilters.filters.*
import kotlinx.android.synthetic.main.fragment_handle_photo.*


class HandlePhotoFragment : Fragment(), FiltersAdapter.FilterSelectListener {

    private var mSelectableFilter = 0
    private var mFiltersListState: Parcelable? = null
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
        }
        btnBack?.setOnClickListener {
            activity?.onBackPressed()
        }

        val filtersAdapter = FiltersAdapter(mFilters, this, context)
        initFiltersListOrientation(activity?.getResources()?.getConfiguration()?.orientation
                ?: Configuration.ORIENTATION_PORTRAIT)
        rvFilters?.setAdapter(filtersAdapter)
    }

    private fun shareImage(bitmap: Bitmap?) {
        bitmap?.let {
            val contentUri = activity?.getImageUri(it, "image.jpg")
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, activity?.getContentResolver()?.getType(contentUri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            startActivity(Intent.createChooser(shareIntent, activity?.getString(R.string.share_via)))
        }
    }

    override fun select(index: Int, filter: IFilter) {
        mSelectableFilter = index
        filter.applyFilter(ivPhoto)
    }

    override fun onResume() {
        super.onResume()
        mFiltersListState?.let {
            rvFilters?.layoutManager?.onRestoreInstanceState(it);
        }
        mFilters[mSelectableFilter].applyFilter(ivPhoto)
    }

    fun initFiltersListOrientation(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvFilters?.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))
        } else {
            rvFilters?.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mFiltersListState = rvFilters?.layoutManager?.onSaveInstanceState()
        outState.putParcelable(KEY_FILTERS_LIST_STATE, mFiltersListState)
        outState.putInt(KEY_SELECTABLE_FILTER, mSelectableFilter)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mFiltersListState = savedInstanceState?.getParcelable(KEY_FILTERS_LIST_STATE)
        mSelectableFilter = savedInstanceState?.getInt(KEY_SELECTABLE_FILTER) ?: 0
    }

    companion object {
        private const val KEY_IMAGE = "KEY_IMAGE"
        private const val KEY_FILTERS_LIST_STATE = "KEY_FILTERS_LIST_STATE"
        private const val KEY_SELECTABLE_FILTER = "KEY_SELECTABLE_FILTER"
        fun newInstance(image: Uri): HandlePhotoFragment {
            val arguments = Bundle()
            arguments.putParcelable(KEY_IMAGE, image)
            val fragment = HandlePhotoFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

}