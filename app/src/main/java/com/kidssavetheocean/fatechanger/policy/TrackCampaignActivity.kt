package com.kidssavetheocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.WebViewActivity
import com.kidssavetheocean.fatechanger.databinding.ActivityTrackCampaignBinding
import com.kidssavetheocean.fatechanger.firebase.model.CampaignsModel
import com.kidssavetheocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssavetheocean.fatechanger.firebase.model.HijackPolicyLocationModel
import com.kidssavetheocean.fatechanger.firebase.repository.UsersRepo
import com.kidssavetheocean.fatechanger.firebase.viewmodel.PoliciesViewModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint

//TODO i removed all the synthetics but I need to fix the UI, it's a mess
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
        binding.trackCampaignToolbarView.toolbar.setOnClickListener {
            onBackPressed()
        }

        initData()


        binding.lytLived.btnLiveSpread.setOnClickListener(this)
        binding.btnSpread.setOnClickListener(this)

        binding.lytLived.btnMoreInfo.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(Constants.INTENT_URL, Constants.URL_LEARN_MORE)
            startActivity(intent)
        }

        binding.lytBottom.btnUpdate.setOnClickListener {
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
        binding.lytMain.visibility = View.VISIBLE
        if (isLive) {
            with(binding) {

                groupLive.visibility = View.VISIBLE
                groupNotLive.visibility = View.INVISIBLE
            }
            with(binding.lytBottom) {
                tvYourPlannedNum.text = UsersRepo.userModel?.second?.signatures_pledged.toString()
                tvYourCollectedNum.text = campaignModel?.signatures_collected.toString()
            }
            with(binding.lytLived){
                tvTrackChosenContent.text = policyValue?.description
                tvSignaturesRequired.text = campaignModel?.signatures_required.toString()
                tvTotalCollected.text = campaignModel?.signatures_collected.toString()
                tvCampaignLoc.text = policyLocation?.second?.location.toString()
            }
        } else {
            with(binding) {
                groupLive.visibility = View.INVISIBLE
                groupNotLive.visibility = View.VISIBLE
            }
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