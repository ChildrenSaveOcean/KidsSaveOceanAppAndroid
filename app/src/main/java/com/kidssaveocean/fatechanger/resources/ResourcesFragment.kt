package com.kidssaveocean.fatechanger.resources

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentResourcesBinding
import com.kidssaveocean.fatechanger.extensions.loadSetting
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

//todo this is the maybe the third fragment that is just a web view, consolidate them
@AndroidEntryPoint
class ResourcesFragment(private val url: String = "https://pederhill.wixsite.com/kids-save-ocean/fatechanger-resources") :
    AbstractFragment<FragmentResourcesBinding, EmptyViewModel>() {

    override fun getViewModelResId(): Int = BR.emptyVM
    override fun getLayoutResId(): Int = R.layout.fragment_resources
    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webView: WebView = view.findViewById(R.id.webview)
        webView.loadSetting()
        webView.loadUrl(url)
    }
}
