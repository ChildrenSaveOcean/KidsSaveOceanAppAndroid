package com.fatechanger.app.dagger.component

import com.fatechanger.app.MainApplication
import com.fatechanger.app.dagger.module.HijackPoliciesRepoModule
import com.fatechanger.app.firebase.viewmodel.PoliciesViewModel
import com.fatechanger.app.firebase.viewmodel.PolicyStepsViewModel
import com.fatechanger.app.policy.PolicyControlCenterActivity
import com.fatechanger.app.policy.PolicyStepsActivity
import com.fatechanger.app.policy.PolicyVoteActivity
import dagger.Component

@Component(modules = [HijackPoliciesRepoModule::class])

interface HijackPolicyComponent {
    fun inject(policyVoteActivity: PolicyVoteActivity)
    fun inject(policyStepsActivity: PolicyStepsActivity)
    fun inject(policyControlCenterActivity: PolicyControlCenterActivity)

    fun inject(policiesViewModel: PoliciesViewModel)
    fun inject(policyStepsViewModel: PolicyStepsViewModel)
    fun inject(mainApplication: MainApplication)
}