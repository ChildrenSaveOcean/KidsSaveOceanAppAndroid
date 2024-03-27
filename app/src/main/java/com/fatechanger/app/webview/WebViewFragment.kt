package com.fatechanger.app.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fatechanger.app.BR
import com.fatechanger.app.R
import com.fatechanger.app.databinding.FragmentWebViewBinding
import com.fatechanger.app.presentation.mvvm.fragment.AbstractFragment
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import com.fatechanger.app.utility.getSerializableCompat

open class WebViewFragment : AbstractFragment<FragmentWebViewBinding, EmptyViewModel>() {

    private lateinit var activeUrl: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        activeUrl = getUrl()
    }

    override fun onResume() {
        super.onResume()
        binding.newsWebview.loadUrl(activeUrl)
    }

    //todo figure out why javascript is enabled and if we can disable it
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.newsWebview.apply {

            val webSetting = this.settings
            webSetting.useWideViewPort = true
            webSetting.loadWithOverviewMode = true
            webSetting.javaScriptEnabled = true

            this.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    view?.loadUrl("file:///android_res/drawable/no_internet.png")
                    super.onReceivedError(view, request, error)
                }
            }

            this.setOnKeyListener { _, keycode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                    if (keycode == KeyEvent.KEYCODE_BACK && this.canGoBack()) {
                        this.goBack()
                        return@setOnKeyListener true
                    }
                }

                return@setOnKeyListener false
            }
        }
    }

    open fun getUrl(): String = requireArguments().getSerializableCompat(URL_KEY)
        ?: throw IllegalStateException("WebViewFragment cannot initialize without a correct url")

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_web_view

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    companion object {
        const val WEB_VIEW_LETTER_URL = "https://pederhill.wixsite.com/kids-save-ocean/copy-of-write-letters-with-your-kid"
        const val WEB_VIEW_RESOURCES_URL = "https://pederhill.wixsite.com/kids-save-ocean/fatechanger-resources"
        const val WEB_VIEW_STUDENT_RESOURCES_URL = "https://pederhill.wixsite.com/kids-save-ocean/studentresources"
        const val WEB_VIEW_UPDATES_URL = "https://pederhill.wixsite.com/kids-save-ocean/updates"
        const val URL_KEY = "web_view_url_key"
    }
}