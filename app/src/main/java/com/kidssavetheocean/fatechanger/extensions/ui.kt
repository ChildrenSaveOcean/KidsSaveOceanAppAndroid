package com.kidssavetheocean.fatechanger.extensions


import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat

//todo get rid of this as well... the min sdk is way above 16....
fun View.setBg(context: Context, resId : Int, alpha: Float = 1F) {
    val sdk = android.os.Build.VERSION.SDK_INT
    val drawable = ContextCompat.getDrawable(context, resId)
    drawable?.alpha = (255 * alpha).toInt()
    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
        this.setBackgroundDrawable(drawable);
    } else {
        this.setBackground(drawable);
    }
}

//todo get rid of this
fun ImageView.setImage(context: Context, resId : Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.setImageDrawable(getResources().getDrawable(resId, context.theme));
    } else {
        this.setImageDrawable(getResources().getDrawable(resId));
    }
}