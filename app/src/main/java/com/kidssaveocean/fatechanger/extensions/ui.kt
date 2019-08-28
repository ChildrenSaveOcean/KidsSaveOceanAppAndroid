package com.kidssaveocean.fatechanger.extensions

import android.app.Activity
import android.content.res.Resources
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Size
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/*
* Helps to add fragments to the navigation stack
* */
fun Fragment.addToNavigationStack(manager : FragmentManager, containerId : Int, tag : String = "") {
    val fragmentTransaction = manager.beginTransaction()
    when (tag) {
        "" -> fragmentTransaction.replace(containerId, this)
        else -> fragmentTransaction.replace(containerId, this , tag)
    }
    fragmentTransaction.addToBackStack(tag)
    fragmentTransaction.commit()
}

/*
* Helps get a screen resolution
* */
fun Activity.getScreenSize() : Size {
    val displayMetrics = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(displayMetrics)
    val width = displayMetrics.widthPixels
    val height = displayMetrics.heightPixels
    return Size(width, height)
}

fun View.getLocationOnScreen(): Point
{
    val location = IntArray(2)
    this.getLocationInWindow(location)
    return Point(location[0],location[1])
}

fun Int.dpToPx(): Int {
    return this * Resources.getSystem().getDisplayMetrics().density.toInt();
}

fun Int.pxToDp() : Int {
    return this / Resources.getSystem().getDisplayMetrics().density.toInt();
}

fun Float.dpToPx(): Float {
    return this * Resources.getSystem().getDisplayMetrics().density;
}

fun Float.pxToDp() : Float {
    return this / Resources.getSystem().getDisplayMetrics().density
}