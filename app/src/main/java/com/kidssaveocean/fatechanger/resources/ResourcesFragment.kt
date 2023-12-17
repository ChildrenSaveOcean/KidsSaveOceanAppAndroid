package com.kidssaveocean.fatechanger.resources


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.extensions.loadSetting

//todo this is the maybe the third fragment that is just a web view, consolidate them
class ResourcesFragment (private val url : String = "https://pederhill.wixsite.com/kids-save-ocean/fatechanger-resources") : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_resources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webView: WebView = view.findViewById(R.id.webview)
        webView.loadSetting()
        webView.loadUrl(url)
    }
}
