package com.kidssaveocean.fatechanger

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import androidx.appcompat.widget.Toolbar
import com.kidssaveocean.fatechanger.common.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity: BaseActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        findViewById<Toolbar>(R.id.toolbar).setOnClickListener{
            onBackPressed()
        }
        var loadUrl = ""
        if (intent != null) {
            val url = intent.getStringExtra("URL")
            if (!TextUtils.isEmpty(url)){
                loadUrl = url
            }
        }

        val settings = webView.settings
        settings.javaScriptEnabled = true
        if (!TextUtils.isEmpty(loadUrl))
        webView.loadUrl(loadUrl)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_BACK && webView.canGoBack()){
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        if (webView != null){
            webView.clearHistory()
            webView.destroy()
        }
        super.onDestroy()
    }
}