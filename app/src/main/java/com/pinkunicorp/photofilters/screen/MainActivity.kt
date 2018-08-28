package com.pinkunicorp.photofilters.screen

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import com.pinkunicorp.photofilters.R
import com.pinkunicorp.photofilters.screen.HandlePhoto.HandlePhotoFragment
import com.pinkunicorp.photofilters.screen.TakePhoto.TakePhotoFragment
import android.view.animation.DecelerateInterpolator



class MainActivity : AppCompatActivity(), Navigator {

    override fun showHandlePhoto(image: Uri) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, HandlePhotoFragment.newInstance(image))
                .addToBackStack("HandlePhotoFragment")
                .commitAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, TakePhotoFragment())
                .commitAllowingStateLoss()
        }
    }


}
