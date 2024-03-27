package com.fatechanger.app.dashboard

import android.os.Bundle
import android.view.View
import com.fatechanger.app.BR
import com.fatechanger.app.R
import com.fatechanger.app.databinding.FragmentNobodysListeningBinding
import com.fatechanger.app.presentation.mvvm.fragment.AbstractFragment
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import com.fatechanger.app.resources.ResourcesFragment
import com.fatechanger.app.webview.WebViewFragment
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
