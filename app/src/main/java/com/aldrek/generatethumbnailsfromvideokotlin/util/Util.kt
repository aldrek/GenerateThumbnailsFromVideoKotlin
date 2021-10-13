package com.aldrek.generatethumbnailsfromvideokotlin.util

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

val Any.TAG: String
    get() = this::class.java.simpleName

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.setImage(url: String) = Glide.with(this.context).load(url).into(this)

fun ImageView.setImageWithRadius(url: Uri, radiusValue: Int) {
    Glide.with(this.context).load(url).apply(RequestOptions().transform(CenterCrop(), RoundedCorners(radiusValue))).into(this)
}
