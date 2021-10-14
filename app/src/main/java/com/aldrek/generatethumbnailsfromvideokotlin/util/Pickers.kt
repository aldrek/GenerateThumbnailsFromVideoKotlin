package com.aldrek.generatethumbnailsfromvideokotlin.util

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aldrek.generatethumbnailsfromvideokotlin.activity.MainActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix


fun AppCompatActivity.startCameraForVideo(request: Int , filePath :String) {

    val options = Options.init()
        .setRequestCode(request) //Request code for activity results
        .setCount(1) //Number of images to restict selection count
        .setMode(Options.Mode.Video)
        .setFrontfacing(false) //Front Facing camera on start
        .setSpanCount(4) //Span count for gallery min 1 & max 5
        .setVideoDurationLimitinSeconds(30) //Duration for video recording
        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT) //Orientaion
        .setPath("/pix/images") //Custom Path For media Storage



    Pix.start(this, options)

}