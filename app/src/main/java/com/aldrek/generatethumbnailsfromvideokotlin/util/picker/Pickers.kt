package com.aldrek.generatethumbnailsfromvideokotlin.util.picker

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aldrek.generatethumbnailsfromvideokotlin.activity.MainActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix


fun AppCompatActivity.startCameraForVideo(request: Int) {

    val options = Options.init()
        .setRequestCode(request)
        .setMode(Options.Mode.Video)   //Option to select only pictures or videos or both
        .setCount(1) //Number of images to restrict selection count
        .setVideoDurationLimitinSeconds(15)                            //Duration for video recording
        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT) //Orientaion
        .setPath("/pix/images") //Custom Path For media Storage

    Pix.start(this, options)

}