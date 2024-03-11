package com.kidssaveocean.fatechanger.dashboard

import android.os.Bundle
import android.view.View
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentStartActivistCampaignBinding
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssaveocean.fatechanger.resources.ResourcesFragment
import com.kidssaveocean.fatechanger.webview.WebViewFragment
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
