package com.kidssaveocean.fatechanger.news

import com.kidssaveocean.fatechanger.webview.WebViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : WebViewFragment() {
    override fun getUrl(): String = arguments?.getString(URL_KEY, WEB_VIEW_UPDATES_URL) ?: WEB_VIEW_UPDATES_URL
}
