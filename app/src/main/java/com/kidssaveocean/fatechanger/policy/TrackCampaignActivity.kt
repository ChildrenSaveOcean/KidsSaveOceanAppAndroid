package com.kidssaveocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.WebViewActivity
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPolicyLocationModel
import com.kidssaveocean.fatechanger.firebase.repository.UsersRepo
import com.kidssaveocean.fatechanger.firebase.viewmodel.PoliciesViewModel
import kotlinx.android.synthetic.main.activity_track_campaign.*
import kotlinx.android.synthetic.main.track_campaign_bottom.*
import kotlinx.android.synthetic.main.track_campaign_live.*
import kotlinx.android.synthetic.main.view_toolbar.*

class TrackCampaignActivity : BaseActivity(), View.OnClickListener {

    private var campaignModel: CampaignsModel? = null
    private var policyValue: HijackPoliciesModel? = null
    private lateinit var policyName: String
    private lateinit var campaignName: String
    private lateinit var policyViewModel: PoliciesViewModel
    private var isLive = false

    private var policyLocation: Pair<String, HijackPolicyLocationModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_campaign)
        toolbar.setOnClickListener {
            onBackPressed()
        }

        policyViewModel = ViewModelProviders.of(this).get(PoliciesViewModel::class.java)

        initData()


        btnLiveSpread.setOnClickListener(this)
        btnSpread.setOnClickListener(this)

        btnMoreInfo.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(Constants.INTENT_URL, Constants.URL_LEARN_MORE)
            startActivity(intent)
        }

        btnUpdate.setOnClickListener {
            startActivityForResult(Intent(this, PolicyControlCenterActivity::class.java), Constants.REQUEST_POLICY_CONTROL_CENTER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.REQUEST_POLICY_CONTROL_CENTER) {
            initData()
        }
    }

    private fun setView() {
        lytMain.visibility = View.VISIBLE
        if (isLive) {
            groupLive.visibility = View.VISIBLE
            groupNotLive.visibility = View.INVISIBLE
            tvTrackChosenContent.text = policyValue?.description
            tvYourPlannedNum.text = UsersRepo.userModel?.second?.signatures_pledged.toString()
            tvYourCollectedNum.text = campaignModel?.signatures_collected.toString()
            tvSignaturesRequired.text = campaignModel?.signatures_required.toString()
            tvTotalCollected.text = campaignModel?.signatures_collected.toString()
            tvCampaignLoc.text = policyLocation?.second?.location.toString()
        } else {
            groupLive.visibility = View.INVISIBLE
            groupNotLive.visibility = View.VISIBLE
        }
    }

    private fun initData() {

//        campaign = intent.getParcelableExtra(Constants.intentCampaignValue)
        campaignName = UsersRepo.userModel?.second?.campaign?.campaign_id.toString()
        if (TextUtils.isEmpty(campaignName)) {
            isLive = false
            setView()
        } else {
//            campaignName = intent.getStringExtra(Constants.intentCampaignName)
//            isLive = campaign!!.live
            policyViewModel.getPolicyCombineData().observe(this, Observer {
                it.campaigns.map { campaign ->
                    if (campaign.first == campaignName)
                        campaignModel = campaign.second
                }
                it.policies.map { policy ->
                    if (campaignModel?.hijack_policy == policy.first) {
                        policyName = policy.first
                        policyValue = policy.second
                    }
                }
                it.policyLocations.map { location ->
                    if (campaignModel?.location_id == location.first) {
                        policyLocation = location
                    }
                }
                isLive = campaignModel?.live ?: false
                setView()
            })
        }
    }

    override fun onClick(v: View?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, Constants.URL_SHARE_TEXT)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }
}