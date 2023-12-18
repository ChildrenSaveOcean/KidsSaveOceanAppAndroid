package com.kidssaveocean.fatechanger.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.KeyEvent
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/*
* Helps to add fragments to the navigation stack
* */
//todo remove this method
fun Fragment.addToNavigationStack(manager: FragmentManager, containerId: Int, tag: String = "") {
    val fragmentTransaction = manager.beginTransaction()
    when (tag) {
        "" -> fragmentTransaction.replace(containerId, this)
        else -> fragmentTransaction.replace(containerId, this, tag)
    }
    fragmentTransaction.addToBackStack(tag)
    fragmentTransaction.commit()
}


/*
* Extracting common settings of WebView to prevent code duplication in the use of WebView
* */
@SuppressLint("SetJavaScriptEnabled")
fun WebView.loadSetting() {

    val webSetting = this.settings
    webSetting.useWideViewPort = true
    webSetting.loadWithOverviewMode = true
    webSetting.javaScriptEnabled = true

    this.webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            view?.loadUrl("file:///android_res/drawable/no_internet.png")
            super.onReceivedError(view, request, error)
        }
    }

    this.setOnKeyListener { view, keycode, keyEvent ->
        if (keyEvent.action == KeyEvent.ACTION_DOWN) {
            if (keycode == KeyEvent.KEYCODE_BACK && this.canGoBack()) {
                this.goBack()
                return@setOnKeyListener true
            }
        }

        return@setOnKeyListener false
    }
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

fun ImageView.setImage(context: Context, resId : Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.setImageDrawable(getResources().getDrawable(resId, context.theme));
    } else {
        this.setImageDrawable(getResources().getDrawable(resId));
    }
}