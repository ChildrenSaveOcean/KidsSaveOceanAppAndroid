package com.fatechanger.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import androidx.appcompat.widget.Toolbar
import com.fatechanger.app.databinding.ActivityWebviewBinding
import com.fatechanger.app.presentation.mvvm.activity.AbstractActivity
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_webview.*

@AndroidEntryPoint
class WebViewActivity : AbstractActivity<ActivityWebviewBinding, EmptyViewModel>() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<Toolbar>(R.id.toolbar).setOnClickListener {
            onBackPressed()
        }
        var loadUrl = ""
        if (intent != null) {
            val url = intent.getStringExtra("URL")
            url?.let {
                if (!TextUtils.isEmpty(it)) {
                    loadUrl = it
                }
            }
        }

        val settings = webView.settings
        settings.javaScriptEnabled = true
        if (!TextUtils.isEmpty(loadUrl))
            webView.loadUrl(loadUrl)
    }

    override fun getLayoutId(): Int = R.layout.activity_webview

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        if (webView != null) {
            webView.clearHistory()
            webView.destroy()
        }
        super.onDestroy()
    }
}