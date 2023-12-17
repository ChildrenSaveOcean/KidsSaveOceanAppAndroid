package com.kidssaveocean.fatechanger.dashboard

import com.kidssaveocean.fatechanger.BR

import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.FragmentGoldenRulesBinding
import com.kidssaveocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoldenRulesFragment : AbstractFragment<FragmentGoldenRulesBinding, EmptyViewModel>() {
    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_golden_rules

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}
