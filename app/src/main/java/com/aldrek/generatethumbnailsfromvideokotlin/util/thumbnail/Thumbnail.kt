import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.concurrent.TimeUnit


/**
 *
 *  Generate images at specific periods
 *
 */
fun AppCompatActivity.generatePhotos(returnValue: String?): MutableList<Uri> {

    var list: MutableList<Uri> = mutableListOf()
    var intervals = mutableListOf(0.0, 0.25, 0.5, 0.75)

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

        val duration = timeInmillisec / 1000
        val hours = duration / 3600
        val minutes = (duration - hours * 3600) / 60
        val seconds = duration - (hours * 3600 + minutes * 60)

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
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path: String =
        MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, name, null)
    return Uri.parse(path)
}

