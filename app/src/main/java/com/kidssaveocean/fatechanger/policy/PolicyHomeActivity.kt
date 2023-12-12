package com.kidssaveocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.WebViewActivity
import com.kidssaveocean.fatechanger.common.AbstractActivity
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import kotlinx.android.synthetic.main.activity_policy_home.*
import kotlinx.android.synthetic.main.view_toolbar.*

class PolicyHomeActivity : AbstractActivity() {
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
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(Constants.INTENT_URL, Constants.URL_POLICY_VIDEO)
            startActivity(intent)
        }

        imgFollowStep.setOnClickListener{
            startActivity(Intent(this, PolicyStepsActivity::class.java))
        }

        imgPolicyPush.setOnClickListener{
            startActivityForResult(Intent(this, PolicyVoteActivity::class.java), Constants.REQUEST_POLICY_VOTE)
        }

        imgImpact.setOnClickListener{
            startActivity(Intent(this, PolicyImpactActivity::class.java))
        }

        imgSignatures.setOnClickListener {
            val intent = Intent(this, PolicyControlCenterActivity::class.java)
            if (policyValue != null) {
                intent.putExtra(Constants.INTENT_POLICY_VALUE, policyValue)
                intent.putExtra(Constants.INTENT_POLICY_NAME, policyName)
            }
            startActivityForResult(intent, Constants.REQUEST_POLICY_CONTROL_CENTER)
        }

        imgTrackTheHijack.setOnClickListener{
            val intent = Intent(this, TrackCampaignActivity::class.java)
            if (campaign != null){
                intent.putExtra(Constants.INTENT_CAMPAIGN_NAME, campaignName)
                intent.putExtra(Constants.INTENT_CAMPAIGN_VALUE, campaign)
            }
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                Constants.REQUEST_POLICY_VOTE -> {
                    data?.apply {
                        policyValue = getParcelableExtra(Constants.INTENT_POLICY_VALUE)
                        policyName = getStringExtra(Constants.INTENT_POLICY_NAME)?: ""
                    }
                }
                Constants.REQUEST_POLICY_CONTROL_CENTER -> {
                    data?.apply {
                        campaign = getParcelableExtra(Constants.INTENT_CAMPAIGN_VALUE)
                        campaignName = getStringExtra(Constants.INTENT_CAMPAIGN_NAME)?: ""
                    }
                }
            }

        }
    }
}