package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.dagger.component.DaggerHijackPolicyComponent
import com.kidssaveocean.fatechanger.firebase.viewmodel.PolicyStepsViewModel
import kotlinx.android.synthetic.main.activity_policy_step.*
import kotlinx.android.synthetic.main.view_toolbar.*

class PolicyStepsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_step)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        DaggerHijackPolicyComponent.builder().build().inject(this)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val policyStepsViewModel = ViewModelProviders.of(this).get(PolicyStepsViewModel::class.java)

        rlvSteps?.run {
            layoutManager = LinearLayoutManager(this@PolicyStepsActivity)
            adapter = PolicyStepsAdapter()
        }

        policyStepsViewModel.getLiveDataPolicySteps(this).observe(this, Observer {
            if (it.isNotEmpty()){
                (rlvSteps.adapter as PolicyStepsAdapter).setData(it)
            }
        })


    }
}