package com.fatechanger.app.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.fatechanger.app.Constants
import com.fatechanger.app.R
import com.fatechanger.app.WebViewActivity
import com.fatechanger.app.databinding.ActivityTrackCampaignBinding
import com.fatechanger.app.firebase.model.CampaignsModel
import com.fatechanger.app.firebase.model.HijackPoliciesModel
import com.fatechanger.app.firebase.model.HijackPolicyLocationModel
import com.fatechanger.app.firebase.repository.UsersRepo
import com.fatechanger.app.firebase.viewmodel.PoliciesViewModel
import com.fatechanger.app.presentation.mvvm.activity.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_track_campaign.btnSpread
import kotlinx.android.synthetic.main.activity_track_campaign.groupLive
import kotlinx.android.synthetic.main.activity_track_campaign.groupNotLive
import kotlinx.android.synthetic.main.activity_track_campaign.lytMain
import kotlinx.android.synthetic.main.track_campaign_bottom.btnUpdate
import kotlinx.android.synthetic.main.track_campaign_bottom.tvYourCollectedNum
import kotlinx.android.synthetic.main.track_campaign_bottom.tvYourPlannedNum
import kotlinx.android.synthetic.main.track_campaign_live.btnLiveSpread
import kotlinx.android.synthetic.main.track_campaign_live.btnMoreInfo
import kotlinx.android.synthetic.main.track_campaign_live.tvCampaignLoc
import kotlinx.android.synthetic.main.track_campaign_live.tvSignaturesRequired
import kotlinx.android.synthetic.main.track_campaign_live.tvTotalCollected
import kotlinx.android.synthetic.main.track_campaign_live.tvTrackChosenContent
import kotlinx.android.synthetic.main.view_toolbar.toolbar

@AndroidEntryPoint
class TrackCampaignActivity : AbstractActivity<ActivityTrackCampaignBinding, PoliciesViewModel>(), View.OnClickListener {

    private var campaignModel: CampaignsModel? = null
    private var policyValue: HijackPoliciesModel? = null
    private lateinit var policyName: String
    private lateinit var campaignName: String
    private var isLive = false

    private var policyLocation: Pair<String, HijackPolicyLocationModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setOnClickListener {
            onBackPressed()
        }

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

    override fun getLayoutId(): Int = R.layout.activity_track_campaign

    override fun getViewModelClass(): Class<PoliciesViewModel> = PoliciesViewModel::class.java

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
            viewModel.getPolicyCombineData().observe(this, Observer {
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