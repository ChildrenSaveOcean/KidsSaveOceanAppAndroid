package com.kidssavetheocean.fatechanger.dashboard

import android.os.Bundle
import android.view.View
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentStartActivistCampaignBinding
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssavetheocean.fatechanger.resources.ResourcesFragment
import com.kidssavetheocean.fatechanger.webview.WebViewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivistCampaignFragment : AbstractFragment<FragmentStartActivistCampaignBinding, EmptyViewModel>() {

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.fragment_start_activist_campaign

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolsButton.setOnClickListener {
            val args = Bundle().apply {
                putString(WebViewFragment.URL_KEY, WebViewFragment.WEB_VIEW_STUDENT_RESOURCES_URL)
            }
            navigateToView(ResourcesFragment::class, args)
        }
    }
}
