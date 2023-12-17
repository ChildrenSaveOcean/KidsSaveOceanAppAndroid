package com.kidssaveocean.fatechanger.countryContacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.views.ErrorWebViewClient


//todo another web view fragment
class WebViewFragment : Fragment() {

    lateinit var webView: WebView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        webView = inflater.inflate(R.layout.fragment_news, container, false) as WebView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        webView.webViewClient = ErrorWebViewClient()
        return webView
    }

    override fun onResume() {
        super.onResume()
        webView.loadUrl(url)
    }

    companion object {
        private const val url = "https://pederhill.wixsite.com/kids-save-ocean/copy-of-write-letters-with-your-kid"
    }

}