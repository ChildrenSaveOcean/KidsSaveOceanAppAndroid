package com.fatechanger.app.policy

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.widget.Toolbar
import com.fatechanger.app.R
import com.fatechanger.app.databinding.ActivityWebviewBinding
import com.fatechanger.app.presentation.mvvm.activity.AbstractActivity
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import com.fatechanger.app.views.ErrorWebViewClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyVideoActivity: AbstractActivity<ActivityWebviewBinding, EmptyViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val webView = binding.webView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        webView.webViewClient = ErrorWebViewClient()
        webView.loadUrl(loadUrl)
    }

    override fun getLayoutId(): Int = R.layout.activity_webview

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    companion object {
        private const val URL = "https://pederhill.wixsite.com/kids-save-ocean/hijackpolicy"
    }
}