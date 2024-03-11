package com.kidssaveocean.fatechanger.dashboard

import android.os.Bundle
import android.view.View
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentEngageLocalGovernmentBinding
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssaveocean.fatechanger.resources.ResourcesFragment
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
