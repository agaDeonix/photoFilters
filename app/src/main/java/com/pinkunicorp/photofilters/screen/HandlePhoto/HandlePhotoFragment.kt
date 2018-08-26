package com.pinkunicorp.photofilters.screen.HandlePhoto

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinkunicorp.photofilters.R
import kotlinx.android.synthetic.main.fragment_handle_photo.*

class HandlePhotoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_handle_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            ivPhoto?.setImageURI(it.getParcelable(KEY_IMAGE))
        }
    }

    override fun onResume() {
        super.onResume()
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