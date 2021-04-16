package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import androidx.appcompat.widget.Toolbar
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.views.ErrorWebViewClient
import kotlinx.android.synthetic.main.view_toolbar.*

class PolicyVideoActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        findViewById<Toolbar>(R.id.toolbar).setOnClickListener{
            onBackPressed()
        }
        var loadUrl = URL
        if (intent != null) {
            val url = intent.getStringExtra("URL")
            if (!TextUtils.isEmpty(url)){
                loadUrl = url
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
        private const val URL = "https://www.kidssaveocean.com/hijackpolicy"
    }
}