package com.kidssaveocean.fatechanger.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

import com.kidssaveocean.fatechanger.R

class NewsFragment : Fragment() {

    lateinit var webView: WebView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        webView = inflater.inflate(R.layout.fragment_news, container, false) as WebView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        return webView
    }

    override fun onResume() {
        super.onResume()
        webView.loadUrl(url)
    }

    companion object {
        private const val url = "https://www.kidssaveocean.com/updates"
    }

}
