package com.kidssaveocean.fatechanger.dashboard

import android.os.Bundle
import android.view.View
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.databinding.FragmentStartActivistCampaignBinding
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssaveocean.fatechanger.resources.ResourcesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivistCampaignFragment : AbstractFragment<FragmentStartActivistCampaignBinding, EmptyViewModel>() {

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
    override fun getLayoutResId(): Int = R.layout.fragment_start_activist_campaign

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //todo fix
        binding.toolsButton.setOnClickListener {
            (activity as? BottomNavigationActivity)?.supportFragmentManager?.let { fragmentManager ->
                ResourcesFragment("https://pederhill.wixsite.com/kids-save-ocean/studentresources").addToNavigationStack(
                    fragmentManager,
                    R.id.fragment_container
                )
            }
        }
    }
}
