package com.aldrek.generatethumbnailsfromvideokotlin.util

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.aldrek.generatethumbnailsfromvideokotlin.util.Thumbnail.Companion.GENERATEDIMAGESIZE
import com.aldrek.generatethumbnailsfromvideokotlin.util.Thumbnail.Companion.GENERATEDIMAGETYPE
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 *  Generate images at specific periods
 *
 */
class Thumbnail{

    companion object{
        val GENERATEDIMAGETYPE: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG
        const val GENERATEDIMAGESIZE: Int = 100
        const val FILEPATH: String = "pix/thumbnail"
        val REQUEST = 101
    }

}

fun AppCompatActivity.generatePhotos(returnValue: String?, intervalList: MutableList<Double>): MutableList<Uri> {

    var list: MutableList<Uri> = mutableListOf()
    var intervals = intervalList

    for (period in intervals) {
        generateSinglePhoto(returnValue, period)?.let {
            list.add(it)
        }
    }

    return list

}

/**
 * Generate single image at specific time
 *
 * @param returnValue
 * @param period
 * @return uri of the thumbnail
 */
fun AppCompatActivity.generateSinglePhoto(returnValue: String?, period: Double): Uri? {

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

        if (myBitmap != null) {
            return getImageUri(this, myBitmap, Date().toString())
        } else {
            return null
        }

    } catch (ex: Exception) {
        return null
    }

}

/**
 * Bitmap to Uri
 *
 * @param inContext
 * @param inImage
 * @param name
 * @return Uri value
 */
fun getImageUri(inContext: Context, inImage: Bitmap, name: String): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(GENERATEDIMAGETYPE, GENERATEDIMAGESIZE, bytes)
    val path: String =
        MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, name, null)
    return Uri.parse(path)
}

