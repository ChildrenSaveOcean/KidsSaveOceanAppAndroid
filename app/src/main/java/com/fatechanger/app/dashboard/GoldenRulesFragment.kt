package com.fatechanger.app.dashboard

import com.fatechanger.app.BR

import com.fatechanger.app.R
import com.fatechanger.app.databinding.FragmentGoldenRulesBinding
import com.fatechanger.app.presentation.mvvm.fragment.AbstractFragment
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoldenRulesFragment : AbstractFragment<FragmentGoldenRulesBinding, EmptyViewModel>() {
    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_golden_rules

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}
