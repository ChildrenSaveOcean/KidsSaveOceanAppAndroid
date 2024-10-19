package com.kidssavetheocean.fatechanger.dagger.component

import com.kidssavetheocean.fatechanger.MainApplication
import com.kidssavetheocean.fatechanger.dagger.module.HijackPoliciesRepoModule
import com.kidssavetheocean.fatechanger.firebase.viewmodel.PoliciesViewModel
import com.kidssavetheocean.fatechanger.firebase.viewmodel.PolicyStepsViewModel
import com.kidssavetheocean.fatechanger.policy.PolicyControlCenterActivity
import com.kidssavetheocean.fatechanger.policy.PolicyStepsActivity
import com.kidssavetheocean.fatechanger.policy.PolicyVoteActivity
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