package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.AbstractActivity
import kotlinx.android.synthetic.main.view_toolbar.*

class PolicyImpactActivity: AbstractActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_impact)
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
}