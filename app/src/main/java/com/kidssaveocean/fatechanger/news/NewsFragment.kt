package com.kidssaveocean.fatechanger.news

import android.view.View
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentNewsBinding
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssaveocean.fatechanger.views.ErrorWebViewClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : AbstractFragment<FragmentNewsBinding, EmptyViewModel>() {

    override fun onPrepareLayout(layoutView: View?) {
        //todo fix
        super.onPrepareLayout(layoutView)
        val settings = binding.newsWebview.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        binding.newsWebview.webViewClient = ErrorWebViewClient()
    }

    override fun onResume() {
        super.onResume()
        binding.newsWebview.loadUrl(url)
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_news

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    companion object {

        private const val url = "https://pederhill.wixsite.com/kids-save-ocean/updates"
    }
}
