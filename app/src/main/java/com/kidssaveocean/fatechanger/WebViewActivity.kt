package com.kidssaveocean.fatechanger

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import com.kidssaveocean.fatechanger.common.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.view_toolbar.*

class WebViewActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webView.loadUrl(Constants.learnMoreUrl)
        val settings = webView.settings
        settings.javaScriptEnabled = true

        toolbar.setOnClickListener {
            onBackPressed()
        }
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