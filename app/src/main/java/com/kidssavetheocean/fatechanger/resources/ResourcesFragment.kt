package com.kidssavetheocean.fatechanger.resources

import com.kidssavetheocean.fatechanger.webview.WebViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResourcesFragment : WebViewFragment() {

    override fun getUrl(): String = arguments?.getString(URL_KEY, WEB_VIEW_RESOURCES_URL) ?: WEB_VIEW_RESOURCES_URL
}
