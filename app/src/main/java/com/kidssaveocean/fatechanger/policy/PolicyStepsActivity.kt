package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import kotlinx.android.synthetic.main.activity_policy_step.*
import kotlinx.android.synthetic.main.view_toolbar.*

class PolicyStepsActivity: BaseActivity() {
    val stepsViewModel= StepsViewModel()

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

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        rlvSteps?.run {
            layoutManager = LinearLayoutManager(this@PolicyStepsActivity)
            val policyStepsAdapter = PolicyStepsAdapter()
            adapter = policyStepsAdapter
            subscribeUi(policyStepsAdapter)
        }

    }

    fun subscribeUi(adapter: PolicyStepsAdapter) {
        stepsViewModel.steps.observe(this, Observer{
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }
}