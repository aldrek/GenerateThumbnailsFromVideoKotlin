package com.aldrek.generatethumbnailsfromvideokotlin.util.extention

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun View.showView(){
    this.visibility = View.VISIBLE
}
fun View.hideView(){
    this.visibility = View.GONE
}

fun View.hideInvisableVeiw(){
    this.visibility = View.INVISIBLE
}

fun View.setBackgroundTint(color : String){
    this.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color));
}

fun View.setBackgroundTint(color : Int){
    this!!.backgroundTintList = ContextCompat.getColorStateList(this!!.context, color);

}

 fun View.showViewWithBoolean(isShow: Boolean) {
    if(isShow){
        showView()
    }else{
        hideView()
    }

}

fun View.showViewWithBooleanInVisable(isShow: Boolean) {
    if(isShow){
        showView()
    }else{
        hideInvisableVeiw()
    }

}

