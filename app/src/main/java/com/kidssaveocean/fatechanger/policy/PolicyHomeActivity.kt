package com.kidssaveocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import kotlinx.android.synthetic.main.activity_policy_home.*
import kotlinx.android.synthetic.main.view_toolbar.*

class PolicyHomeActivity : BaseActivity() {
    var policyValue: HijackPoliciesModel? = null
    lateinit var policyName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_home)
        supportActionBar?.run{
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        imgHowToWork.setOnClickListener{
            startActivity(Intent(this, PolicyVideoActivity::class.java))
        }

        imgFollowStep.setOnClickListener{
            startActivity(Intent(this, PolicyStepsActivity::class.java))
        }

        imgPolicyPush.setOnClickListener{
            startActivityForResult(Intent(this, PolicyVoteActivity::class.java), Constants.requestPolicyVote)
        }

        imgImpact.setOnClickListener{
            startActivity(Intent(this, PolicyImpactActivity::class.java))
        }

        imgSignatures.setOnClickListener {
            val intent = Intent(this, PolicyControlCenterActivity::class.java)
            if (policyValue != null) {
                intent.putExtra(Constants.intentPolicyValue, policyValue)
                intent.putExtra(Constants.intentPolicyName, policyName)
            }
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.requestPolicyVote){
            data?.apply {
                policyValue = getParcelableExtra(Constants.intentPolicyValue)
                policyName = getStringExtra(Constants.intentPolicyName)
            }
        }
    }
}