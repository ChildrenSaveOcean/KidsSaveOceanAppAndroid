package com.kidssavetheocean.fatechanger.dashboard

import com.kidssavetheocean.fatechanger.BR

import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentGoldenRulesBinding
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoldenRulesFragment : AbstractFragment<FragmentGoldenRulesBinding, EmptyViewModel>() {
    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_golden_rules

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}
