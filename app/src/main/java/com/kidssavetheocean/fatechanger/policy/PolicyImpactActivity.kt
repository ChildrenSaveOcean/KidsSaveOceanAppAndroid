package com.kidssavetheocean.fatechanger.policy

import android.os.Bundle
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.ActivityPolicyImpactBinding
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_toolbar.toolbar

@AndroidEntryPoint
class PolicyImpactActivity : AbstractActivity<ActivityPolicyImpactBinding, EmptyViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_policy_impact

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}