package com.kidssaveocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import kotlinx.android.synthetic.main.activity_policy_home.*
import kotlinx.android.synthetic.main.view_toolbar.*

class PolicyHomeActivity : BaseActivity() {
    private var policyValue: HijackPoliciesModel? = null
    private lateinit var policyName: String
    private var campaign: CampaignsModel? = null
    private lateinit var campaignName: String

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
            startActivityForResult(intent, Constants.requestPolicyCentrolCenter)
        }

        imgTrackTheHijack.setOnClickListener{
            val intent = Intent(this, TrackCampaignActivity::class.java)
            if (campaign != null){
                intent.putExtra(Constants.intentCampaignName, campaignName)
                intent.putExtra(Constants.intentCampaignValue, campaign)
            }
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                Constants.requestPolicyVote -> {
                    data?.apply {
                        policyValue = getParcelableExtra(Constants.intentPolicyValue)
                        policyName = getStringExtra(Constants.intentPolicyName)?: ""
                    }
                }
                Constants.requestPolicyCentrolCenter -> {
                    data?.apply {
                        campaign = getParcelableExtra(Constants.intentCampaignValue)
                        campaignName = getStringExtra(Constants.intentCampaignName)?: ""
                    }
                }
            }

        }
    }
}