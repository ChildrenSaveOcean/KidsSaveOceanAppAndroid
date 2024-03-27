package com.fatechanger.app.dashboard

import android.os.Bundle
import android.view.View
import com.fatechanger.app.BR
import com.fatechanger.app.R
import com.fatechanger.app.databinding.FragmentStartActivistCampaignBinding
import com.fatechanger.app.presentation.mvvm.fragment.AbstractFragment
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import com.fatechanger.app.resources.ResourcesFragment
import com.fatechanger.app.webview.WebViewFragment
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
