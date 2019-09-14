package com.kidssaveocean.fatechanger.extensions

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/*
* Helps to add fragments to the navigation stack
* */
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

