package com.aldrek.generatethumbnailsfromvideokotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldrek.generatethumbnailsfromvideokotlin.databinding.ActivityMainBinding
import com.fxn.pix.Pix
import android.app.Activity

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.aldrek.generatethumbnailsfromvideokotlin.R
import com.aldrek.generatethumbnailsfromvideokotlin.adapter.ThumbnailsAdapter
import com.aldrek.generatethumbnailsfromvideokotlin.util.*
import com.aldrek.generatethumbnailsfromvideokotlin.util.Thumbnail.Companion.FILENAME
import com.aldrek.generatethumbnailsfromvideokotlin.util.Thumbnail.Companion.FILEPATH
import com.aldrek.generatethumbnailsfromvideokotlin.util.Thumbnail.Companion.REQUEST
import com.aldrek.generatethumbnailsfromvideokotlin.util.extention.hideView
import com.aldrek.generatethumbnailsfromvideokotlin.util.extention.showView
import com.aldrek.generatethumbnailsfromvideokotlin.util.extention.showViewWithBooleanInVisable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    //
    // compose way of doing this stuff
    var mAdapter: ThumbnailsAdapter? = null
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private var sliceType: SliceType = SliceType.SEQUENTIAL
    private var sliceSequentialType: SliceTypeSequential = SliceTypeSequential.START
    private var videoUri:String =""
    var values: MutableList<Uri> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mAdapter = ThumbnailsAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = mAdapter
        }

        binding.tvPick.setOnClickListener {
            this@MainActivity.startCameraForVideo(REQUEST, FILEPATH)
            binding.progress.showView()
        }

        binding.tvDelete.setOnClickListener {
            FileUtil.deleteWholeDirectory(FILENAME)
        }

        binding.spinnerSliceSequentialType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sliceSequentialType =
                    SliceTypeSequential.valueOf(parent?.getItemAtPosition(position) as String)
                sliceVideo()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding.spinnerSliceType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sliceType = SliceType.valueOf(parent?.getItemAtPosition(position) as String)
                binding.spinnerSliceSequentialType.showViewWithBooleanInVisable(sliceType == SliceType.SEQUENTIAL)
                sliceVideo()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        ArrayAdapter.createFromResource(
            this,
            R.array.sice_array_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerSliceType.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.sice_array_sequential_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerSliceSequentialType.adapter = adapter
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST) {

            val returnValue = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            videoUri = returnValue?.get(0)!!
            sliceVideo()

        } else {
            binding.progress.hideView()
        }

    }

    private fun sliceVideo() {
        if(videoUri.isNullOrEmpty()) return
        binding.progress.showView()
        GlobalScope.launch {

            withContext(Dispatchers.Default) {
                when (sliceType) {
                    SliceType.CUSTOM_SEQUENTIAL -> values = generatePhotos(
                        videoUri,
                        sliceType = SliceType.CUSTOM_TIME,
                        sliceCustom = mutableListOf(0.0, 0.25, 0.5, 0.75)
                    )

                    SliceType.SEQUENTIAL -> values = generatePhotos(
                        videoUri,
                        sliceType = SliceType.SEQUENTIAL,
                        sliceSequentialType = sliceSequentialType
                    )

                    SliceType.CUSTOM_TIME -> values = generatePhotosWithSpecificTime(
                        videoUri,
                        mutableListOf(10.0, 15.0, 20.0)
                    )

                }


            }

            withContext(Dispatchers.Main) {
                mAdapter?.photos = values
                binding.progress.hideView()
            }

        }

    }


}


