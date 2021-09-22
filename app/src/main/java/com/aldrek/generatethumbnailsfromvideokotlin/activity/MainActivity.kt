package com.aldrek.generatethumbnailsfromvideokotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aldrek.generatethumbnailsfromvideokotlin.databinding.ActivityMainBinding
import com.aldrek.generatethumbnailsfromvideokotlin.util.binding.viewBinding
import com.fxn.pix.Pix
import android.app.Activity

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.aldrek.generatethumbnailsfromvideokotlin.adapter.ThumbnailsAdapter
import com.aldrek.generatethumbnailsfromvideokotlin.util.picker.startCameraForVideo
import generatePhotos


public class MainActivity : AppCompatActivity() {

    // TODO: 9/22/21 MVVM with compose
    // compose way of doing this stuff
    var mAdapter: ThumbnailsAdapter? = null

    final val REQUEST = 101
    private var returnValue: String = ""
    val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mAdapter = ThumbnailsAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = mAdapter
        }

        binding.tvPick.setOnClickListener {
            this@MainActivity.startCameraForVideo(REQUEST)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST) {

            val returnValue = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            var videoUri = returnValue?.get(0)!!
            mAdapter?.photos =   generatePhotos(videoUri)

        }

    }

}


