package com.fatechanger.app.dashboard

import android.os.Bundle
import android.view.View
import com.fatechanger.app.BR
import com.fatechanger.app.R
import com.fatechanger.app.databinding.FragmentEngageLocalGovernmentBinding
import com.fatechanger.app.presentation.mvvm.fragment.AbstractFragment
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import com.fatechanger.app.resources.ResourcesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_start_activist_campaign.tools_button

@AndroidEntryPoint
class EngageLocalGovernmentFragment : AbstractFragment<FragmentEngageLocalGovernmentBinding, EmptyViewModel>() {

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_engage_local_government

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tools_button?.setOnClickListener {
            navigateToView(ResourcesFragment::class)
        }
    }
}
