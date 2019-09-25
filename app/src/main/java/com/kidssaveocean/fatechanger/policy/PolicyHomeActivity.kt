package com.kidssaveocean.fatechanger.policy

import android.content.Intent
import android.os.Bundle
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import kotlinx.android.synthetic.main.activity_policy_home.*

class PolicyHomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_home)

        imgHowToWork.setOnClickListener{
            startActivity(Intent(this, PolicyVideoActivity::class.java))
        }

        imgFollowStep.setOnClickListener{
            startActivity(Intent(this, PolicyStepsActivity::class.java))
        }

        imgImpact.setOnClickListener{
            startActivity(Intent(this, PolicyImpactActivity::class.java))
        }
    }
}