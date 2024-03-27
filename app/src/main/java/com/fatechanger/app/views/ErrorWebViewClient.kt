package com.fatechanger.app.views

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class ErrorWebViewClient: WebViewClient() {
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        view?.loadUrl("file:///android_res/drawable/no_internet.jpg")
        super.onReceivedError(view, request, error)
    }
}