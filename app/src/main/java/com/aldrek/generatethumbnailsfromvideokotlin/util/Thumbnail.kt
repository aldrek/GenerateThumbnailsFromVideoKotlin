package com.aldrek.generatethumbnailsfromvideokotlin.util

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aldrek.generatethumbnailsfromvideokotlin.util.Thumbnail.Companion.GENERATEDIMAGESIZE
import com.aldrek.generatethumbnailsfromvideokotlin.util.Thumbnail.Companion.GENERATEDIMAGETYPE
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.TimeUnit

// We delete the cash of files plus add full screen view
// Make a perfect github and article
// Compose

/**
 *
 *  Generate images at specific periods
 *
 */
class Thumbnail {

    companion object {
        val GENERATEDIMAGETYPE: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG
        const val GENERATEDIMAGESIZE: Int = 100
        const val FILEPATH: String = "/alderk/temp"
        const val FILENAME: String = "/aldrek"
        val REQUEST = 101
        var intervalStartList: MutableList<Double> = mutableListOf(0.0)
        var intervalStartToMiddleList: MutableList<Double> = mutableListOf(0.0, 0.25, 0.5)
        var intervalStartToEnd: MutableList<Double> = mutableListOf(0.0, 0.25, 0.5, 0.75, 1.0)
        var intervalStartToEndWithoutBetween: MutableList<Double> = mutableListOf(0.0, 0.5, 1.0)

        fun pickDependToType(type : SliceTypeSequential) = when(type){
            SliceTypeSequential.START->intervalStartList
            SliceTypeSequential.START_TO_MIDDLE->intervalStartToMiddleList
            SliceTypeSequential.START_TO_END->intervalStartToEnd
            SliceTypeSequential.START_TO_END_WITHOUT_BETWEEN_MIDDLE->intervalStartToEndWithoutBetween
        }

    }

}

// slice with specific array of times
fun AppCompatActivity.generatePhotosWithSpecificTime( returnValue: String?,  sliceCustom :MutableList<Double> ): MutableList<Uri> {

    // two kinds of cutting either a sequential order or at specific times

    var list: MutableList<Uri> = mutableListOf()

    for (period in sliceCustom) {
        generateSinglePhotoWithSpecificTime(returnValue, period)?.let {
            list.add(it)
        }
    }

    return list

}

fun AppCompatActivity.generatePhotos( returnValue: String?, sliceType: SliceType ,  sliceSequentialType :SliceTypeSequential = SliceTypeSequential.START , sliceCustom :MutableList<Double> = mutableListOf()): MutableList<Uri> {

    // two kinds of cutting either a sequential order or at specific times

    var list: MutableList<Uri> = mutableListOf()
    var interval = when (sliceType){
        SliceType.SEQUENTIAL -> Thumbnail.pickDependToType(sliceSequentialType)
        SliceType.CUSTOM_SEQUENTIAL -> sliceCustom
        else -> sliceCustom
    }

    for (period in interval) {
        generateSinglePhotoSequential(returnValue, period)?.let {
            list.add(it)
        }
    }

    return list

}

fun AppCompatActivity.generateSinglePhotoSequential(returnValue: String?, period: Double): Uri? {

    var retriever = MediaMetadataRetriever()

    try {
        retriever.setDataSource(returnValue)
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val timeInmillisec = time!!.toLong()

        val seconds = ((timeInmillisec / 1000) % 60)
        var slice = TimeUnit.MICROSECONDS.convert((seconds * period).toLong(), TimeUnit.SECONDS)

        var myBitmap = retriever.getFrameAtTime(
            slice, MediaMetadataRetriever.OPTION_CLOSEST_SYNC
        )

        return if (myBitmap != null) {
            Uri.parse(saveBitmap( myBitmap , Date().toString()))
        } else {
            null
        }

    } catch (ex: Exception) {
        return null
    }

}

fun AppCompatActivity.generateSinglePhotoWithSpecificTime(returnValue: String?, sliceSecond: Double): Uri? {

    var retriever = MediaMetadataRetriever()

    try {
        retriever.setDataSource(returnValue)
        var slice = TimeUnit.MICROSECONDS.convert((sliceSecond).toLong(), TimeUnit.SECONDS)

        var myBitmap = retriever.getFrameAtTime(
            slice, MediaMetadataRetriever.OPTION_CLOSEST_SYNC
        )

        return if (myBitmap != null) {
            Uri.parse(saveBitmap( myBitmap , Date().toString()))
        } else {
            null
        }

    } catch (ex: Exception) {
        return null
    }

}

fun getImageUri(inContext: Context, inImage: Bitmap, name: String): Uri? {
    val bytes = ByteArrayOutputStream()

    inImage.compress(GENERATEDIMAGETYPE, GENERATEDIMAGESIZE, bytes)
    val path: String =
        MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, name, null)
    return Uri.parse(path)

}

fun saveBitmap(bmp: Bitmap, name: String): String {
    val bytes = ByteArrayOutputStream()
    bmp.compress(GENERATEDIMAGETYPE, GENERATEDIMAGESIZE, bytes)
    val file = File(
        (Environment.getExternalStorageDirectory().absolutePath+"/aldrek/temp/${name.filter  { !it.isWhitespace() }}.png")
    )

    try {
        file.parentFile.mkdirs()
        file.createNewFile()
    }catch (e:Exception){
        Log.d("TAG", "saveBitmap: $e")
    }

    val fo = FileOutputStream(file)
    fo.write(bytes.toByteArray())
    fo.close()
    return file.path
}


enum class SliceType() {
    SEQUENTIAL, CUSTOM_SEQUENTIAL , CUSTOM_TIME
}

enum class SliceTypeSequential() {
    START , START_TO_MIDDLE, START_TO_END, START_TO_END_WITHOUT_BETWEEN_MIDDLE
}

