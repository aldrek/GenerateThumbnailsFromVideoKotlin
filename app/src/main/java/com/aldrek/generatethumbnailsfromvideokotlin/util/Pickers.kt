package com.aldrek.generatethumbnailsfromvideokotlin.util

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aldrek.generatethumbnailsfromvideokotlin.activity.MainActivity
import com.fxn.pix.Options
import com.fxn.pix.Pix


fun AppCompatActivity.startCameraForVideo(request: Int , filePath :String) {

    val options = Options.init()
        .setRequestCode(request)
        .setMode(Options.Mode.Video)
        .setCount(1)
        .setVideoDurationLimitinSeconds(15)
        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
        .setPath("$filePath") //Custom Path For media Storage

    Pix.start(this, options)

}