package com.kidssavetheocean.fatechanger.policy

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.ActivityPolicyStepBinding
import com.kidssavetheocean.fatechanger.firebase.viewmodel.PolicyStepsViewModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_policy_step.rlvSteps
import kotlinx.android.synthetic.main.view_toolbar.toolbar

@AndroidEntryPoint
class PolicyStepsActivity : AbstractActivity<ActivityPolicyStepBinding, PolicyStepsViewModel>() {

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


        rlvSteps?.run {
            layoutManager = LinearLayoutManager(this@PolicyStepsActivity)
            adapter = PolicyStepsAdapter()
        }

        viewModel.getLiveDataPolicySteps(this).observe(this, Observer {
            if (it.isNotEmpty()){
                (rlvSteps.adapter as PolicyStepsAdapter).setData(it)
            }
        })


    }

    override fun getLayoutId(): Int = R.layout.activity_policy_step

    override fun getViewModelClass(): Class<PolicyStepsViewModel> = PolicyStepsViewModel::class.java
}