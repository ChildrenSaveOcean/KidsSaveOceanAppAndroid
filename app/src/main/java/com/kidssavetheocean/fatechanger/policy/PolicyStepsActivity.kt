package com.kidssavetheocean.fatechanger.policy

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.ActivityPolicyStepBinding
import com.kidssavetheocean.fatechanger.firebase.viewmodel.PolicyStepsViewModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyStepsActivity : AbstractActivity<ActivityPolicyStepBinding, PolicyStepsViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.policyStepToolbarView.toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        binding.policyStepToolbarView.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        binding.rlvSteps.run {
            layoutManager = LinearLayoutManager(this@PolicyStepsActivity)
            adapter = PolicyStepsAdapter()
        }

        viewModel.getLiveDataPolicySteps(this).observe(this, Observer {
            if (it.isNotEmpty()){
                (binding.rlvSteps.adapter as PolicyStepsAdapter).setData(it)
            }
        })


    }

    override fun getLayoutId(): Int = R.layout.activity_policy_step

    override fun getViewModelClass(): Class<PolicyStepsViewModel> = PolicyStepsViewModel::class.java
}