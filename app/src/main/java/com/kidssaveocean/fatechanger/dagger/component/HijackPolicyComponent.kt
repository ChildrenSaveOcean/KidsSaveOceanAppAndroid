package com.kidssaveocean.fatechanger.dagger.component

import com.kidssaveocean.fatechanger.MainApplication
import com.kidssaveocean.fatechanger.dagger.module.HijackPoliciesRepoModule
import com.kidssaveocean.fatechanger.firebase.viewmodel.PoliciesViewModel
import com.kidssaveocean.fatechanger.firebase.viewmodel.PolicyStepsViewModel
import com.kidssaveocean.fatechanger.policy.PolicyControlCenterActivity
import com.kidssaveocean.fatechanger.policy.PolicyStepsActivity
import com.kidssaveocean.fatechanger.policy.PolicyVoteActivity
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