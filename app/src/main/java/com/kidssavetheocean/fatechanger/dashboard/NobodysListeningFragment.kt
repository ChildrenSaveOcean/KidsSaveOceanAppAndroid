package com.kidssavetheocean.fatechanger.dashboard

import android.os.Bundle
import android.view.View
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentNobodysListeningBinding
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssavetheocean.fatechanger.resources.ResourcesFragment
import com.kidssavetheocean.fatechanger.webview.WebViewFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_nobodys_listening.activist_toolkit_button
import kotlinx.android.synthetic.main.fragment_nobodys_listening.golden_rules_button

@AndroidEntryPoint
class NobodysListeningFragment : AbstractFragment<FragmentNobodysListeningBinding, EmptyViewModel>() {

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_nobodys_listening

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        golden_rules_button?.setOnClickListener {
            navigateToView(GoldenRulesFragment::class)
        }

        activist_toolkit_button?.setOnClickListener {
            val args = Bundle()
            args.putString(WebViewFragment.URL_KEY, WebViewFragment.WEB_VIEW_STUDENT_RESOURCES_URL)
            navigateToView(ResourcesFragment::class, args)
        }
    }
}
