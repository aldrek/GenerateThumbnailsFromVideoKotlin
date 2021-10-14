package com.aldrek.generatethumbnailsfromvideokotlin.util

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import java.io.File

val Any.TAG: String
    get() = this::class.java.simpleName

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.setImage(url: String) = Glide.with(this.context).load(url).into(this)

fun ImageView.setImageWithRadius(uri: Uri, radiusValue: Int) {

    val file = File(uri.toString())
    val imageUri = Uri.fromFile(file)

    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions()
        .transform(CenterCrop(), RoundedCorners(radiusValue)))
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                @Nullable e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                // log exception
                Log.e("TAG", "Error loading image", e)
                return false // important to return false so the error placeholder can be placed
            }


            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false

            }
        })
        .into(this)

}
