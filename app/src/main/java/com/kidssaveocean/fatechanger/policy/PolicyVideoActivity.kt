package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import android.text.TextUtils
import android.webkit.WebView
import androidx.appcompat.widget.Toolbar
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.AbstractActivity
import com.kidssaveocean.fatechanger.views.ErrorWebViewClient

class PolicyVideoActivity: AbstractActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        findViewById<Toolbar>(R.id.toolbar).setOnClickListener{
            onBackPressed()
        }
        var loadUrl = URL
        if (intent != null) {
            val url = intent.getStringExtra("URL")
            url?.let {
                if (!TextUtils.isEmpty(url)){
                    loadUrl = url
                }
            }
        }
        val webView = this.findViewById<WebView>(R.id.webView)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        webView.webViewClient = ErrorWebViewClient()
        webView.loadUrl(loadUrl)
    }


    companion object {
        private const val URL = "https://pederhill.wixsite.com/kids-save-ocean/hijackpolicy"
    }
}