package com.kidssaveocean.fatechanger.dashboard

import android.os.Bundle
import android.view.View
import com.kidssaveocean.fatechanger.BR
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.databinding.FragmentNobodysListeningBinding
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssaveocean.fatechanger.resources.ResourcesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_nobodys_listening.activist_toolkit_button
import kotlinx.android.synthetic.main.fragment_nobodys_listening.golden_rules_button

@AndroidEntryPoint
class NobodysListeningFragment : AbstractFragment<FragmentNobodysListeningBinding, EmptyViewModel>() {

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.fragment_nobodys_listening

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //todo fix
        super.onViewCreated(view, savedInstanceState)

        val bottomActivity = activity as BottomNavigationActivity
        golden_rules_button?.setOnClickListener {
            GoldenRulesFragment().addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container)
        }

        activist_toolkit_button?.setOnClickListener {
            ResourcesFragment("https://pederhill.wixsite.com/kids-save-ocean/studentresources").addToNavigationStack(
                    bottomActivity.supportFragmentManager,
                    R.id.fragment_container)
        }
    }
}
