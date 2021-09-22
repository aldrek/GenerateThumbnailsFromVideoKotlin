package com.aldrek.generatethumbnailsfromvideokotlin.adapter

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aldrek.generatethumbnailsfromvideokotlin.R
import com.aldrek.generatethumbnailsfromvideokotlin.util.inflate

class ThumbnailsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var photos: MutableList<Uri>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        photos = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflatedView = parent.inflate(R.layout.photo_row, false)
        return PhotoHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.imageView).setImageURI(photos!![position])
    }

    override fun getItemCount(): Int {
        return photos?.size!!
    }

    class PhotoHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View = v

        companion object {
            private val PHOTO_KEY = "PHOTO"
        }

    }

}