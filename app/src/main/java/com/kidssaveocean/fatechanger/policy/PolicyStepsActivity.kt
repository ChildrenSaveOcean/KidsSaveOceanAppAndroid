package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import kotlinx.android.synthetic.main.activity_policy_step.*

class PolicyStepsActivity: BaseActivity() {

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

        val list = mutableListOf<String>()
        list.add(resources.getString(R.string.step_one))
        list.add(resources.getString(R.string.step_two))
        list.add(resources.getString(R.string.step_three))
        list.add(resources.getString(R.string.step_four))
        list.add(resources.getString(R.string.step_five))
        list.add(resources.getString(R.string.step_six))
        list.add(resources.getString(R.string.step_seven))

        rlvSteps.layoutManager = LinearLayoutManager(this)
        rlvSteps.adapter = PolicyStepsAdapter(list)
    }
}