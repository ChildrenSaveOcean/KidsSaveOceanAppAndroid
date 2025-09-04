package com.kidssavetheocean.fatechanger.dashboard

import android.os.Bundle
import android.view.View
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentEngageLocalGovernmentBinding
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssavetheocean.fatechanger.resources.ResourcesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EngageLocalGovernmentFragment : AbstractFragment<FragmentEngageLocalGovernmentBinding, EmptyViewModel>() {

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_engage_local_government

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolsButton.setOnClickListener {
            navigateToView(ResourcesFragment::class)
        }
    }
}
