package com.aldrek.generatethumbnailsfromvideokotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldrek.generatethumbnailsfromvideokotlin.databinding.ActivityMainBinding
import com.fxn.pix.Pix
import android.app.Activity

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.aldrek.generatethumbnailsfromvideokotlin.adapter.ThumbnailsAdapter
import com.aldrek.generatethumbnailsfromvideokotlin.util.*
import com.aldrek.generatethumbnailsfromvideokotlin.util.Thumbnail.Companion.FILEPATH
import com.aldrek.generatethumbnailsfromvideokotlin.util.Thumbnail.Companion.REQUEST


public class MainActivity : AppCompatActivity() {

    // TODO: 9/22/21 MVVM with compose
    // compose way of doing this stuff
    var mAdapter: ThumbnailsAdapter? = null
    private var returnValue: String = ""
    val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Delete generated folder
   //     FileUtil.deleteWholeDirectory(Thumbnail.FILEPATH)

        mAdapter = ThumbnailsAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = mAdapter
        }

        binding.tvPick.setOnClickListener {
            this@MainActivity.startCameraForVideo(REQUEST , FILEPATH)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST) {

            val returnValue = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            var videoUri = returnValue?.get(0)!!
            mAdapter?.photos =   generatePhotos(videoUri , mutableListOf(0.0, 0.25, 0.5, 0.75))

        }

    }

}


fun main() {
//  print( getConcatenation(intArrayOf(10 , 20)).toString())
    print(reversePrefix("abedee" , 'e'))
}

fun reversePrefix(word: String, ch: Char) {
    var position = word.indexOf(ch)
    var reversedString = word.substring(0,position+1).reversed()
    var notReversedString = word.substring(position+1,word.length)
//    print(reversedString)

    print(reversedString( word ,position ) + notReversedString(word , position ))

}

fun reversedString(word :String ,position : Int): String {
    return    word.substring(0,position+1).reversed()
}

fun notReversedString(word :String ,position : Int): String {
    return word.substring(position+1,word.length)
}

fun reverseArray(){

}


fun getConcatenation(nums: IntArray): IntArray {
    var concatenatedList =  nums.plus(nums)
    return concatenatedList
}


