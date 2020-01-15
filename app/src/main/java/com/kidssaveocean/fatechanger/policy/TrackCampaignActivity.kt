package com.kidssaveocean.fatechanger.policy

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPolicyLocationModel
import com.kidssaveocean.fatechanger.firebase.viewmodel.PoliciesViewModel
import kotlinx.android.synthetic.main.activity_track_campaign.*
import kotlinx.android.synthetic.main.track_campaign_bottom.*
import kotlinx.android.synthetic.main.track_campaign_live.*
import kotlinx.android.synthetic.main.view_toolbar.*

class TrackCampaignActivity: BaseActivity() {
    private var campaign: CampaignsModel? = null
    private var policyValue: HijackPoliciesModel? = null
    private lateinit var policyName: String
    private lateinit var campaignName: String
    private var isLive = false

    private var policyLocation: Pair<String, HijackPolicyLocationModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_campaign)
        toolbar.setOnClickListener {
            onBackPressed()
        }

        val policyViewModel = ViewModelProviders.of(this).get(PoliciesViewModel::class.java)

        campaign = intent.getParcelableExtra(Constants.intentCampaignValue)

        if (campaign == null){
            isLive = false
        }else{
            campaignName = intent.getStringExtra(Constants.intentCampaignName)
            isLive = campaign!!.live
            policyViewModel.getPolicyCombineData().observe(this, Observer {
                it.policies.map {policy->
                    if (campaign!!.hijack_policy == policy.first){
                        policyName = policy.first
                        policyValue = policy.second
                    }
                }
                it.policyLocations.map { location ->
                    if (campaign!!.location_id == location.first){
                        policyLocation = location
                    }
                }
                setView()
            })
        }
        setView()

        btnLiveSpread.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, Constants.shareText)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun setView(){
        if (isLive){
            groupLive.visibility = View.VISIBLE
            groupNotLive.visibility = View.INVISIBLE
            tvTrackChosenContent.text = policyValue?.description
            tvYourPlannedNum.text = campaign?.signatures_required.toString()
            tvYourCollectedNum.text = campaign?.signatures_collected.toString()
            tvCampaignLoc.text = policyLocation?.second?.location.toString()
        }else{
            groupLive.visibility = View.INVISIBLE
            groupNotLive.visibility = View.VISIBLE
        }
    }
}