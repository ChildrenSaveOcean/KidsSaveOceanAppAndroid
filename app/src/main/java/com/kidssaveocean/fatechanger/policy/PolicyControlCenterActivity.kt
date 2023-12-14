package com.kidssaveocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.WebViewActivity
import com.kidssaveocean.fatechanger.databinding.ActivityPolicyControlCenterBinding
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPolicyLocationModel
import com.kidssaveocean.fatechanger.firebase.repository.CampaignsRepo
import com.kidssaveocean.fatechanger.firebase.repository.UsersRepo
import com.kidssaveocean.fatechanger.firebase.viewmodel.PoliciesViewModel
import com.kidssaveocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_policy_control_center.btnLearnMore
import kotlinx.android.synthetic.main.activity_policy_control_center.btnShare
import kotlinx.android.synthetic.main.activity_policy_control_center.groupTop
import kotlinx.android.synthetic.main.activity_policy_control_center.lytBottom
import kotlinx.android.synthetic.main.activity_policy_control_center.lytChooseLocation
import kotlinx.android.synthetic.main.activity_policy_control_center.lytRequirement
import kotlinx.android.synthetic.main.activity_policy_control_center.progressBar
import kotlinx.android.synthetic.main.activity_policy_control_center.tvLocation
import kotlinx.android.synthetic.main.activity_policy_control_center.tvLocationContent
import kotlinx.android.synthetic.main.activity_policy_control_center.tvPolicyChosenContent
import kotlinx.android.synthetic.main.policy_center_control_bottom.btnCollectedUpdate
import kotlinx.android.synthetic.main.policy_center_control_bottom.btnPlannedUpdate
import kotlinx.android.synthetic.main.policy_center_control_bottom.etCollectedSign
import kotlinx.android.synthetic.main.policy_center_control_bottom.etPlannedSign
import kotlinx.android.synthetic.main.policy_center_control_bottom.tvLivedNotice
import kotlinx.android.synthetic.main.policy_center_control_bottom.tvPlannedSign
import kotlinx.android.synthetic.main.policy_control_center_location.btnChooseLocation
import kotlinx.android.synthetic.main.policy_control_center_location.imgTriangle
import kotlinx.android.synthetic.main.policy_control_center_location.lytSpinner
import kotlinx.android.synthetic.main.policy_control_center_location.tvYourLocation
import kotlinx.android.synthetic.main.policy_control_center_requirement.groupRequirement
import kotlinx.android.synthetic.main.policy_control_center_requirement.tvNotLive
import kotlinx.android.synthetic.main.policy_control_center_requirement.tvSignaturesRequired
import kotlinx.android.synthetic.main.policy_control_center_requirement.tvTotalCollected
import kotlinx.android.synthetic.main.view_toolbar.toolbar

@AndroidEntryPoint
class PolicyControlCenterActivity : AbstractActivity<ActivityPolicyControlCenterBinding, PoliciesViewModel>() {
    var policyLocations: List<Pair<String, HijackPolicyLocationModel>>? = null
    private var campaigns: List<Pair<String, CampaignsModel>>? = null
    private var campaignModel: CampaignsModel? = null
    private lateinit var campaignName: String
    private var policyValue: HijackPoliciesModel? = null
    private lateinit var policyName: String

    private var policyLocation: Pair<String, HijackPolicyLocationModel>? = null

    private val chooseLocation = 0
    private val notLived = 1
    private val lived = 2
    private var situation: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.setOnClickListener {
            onBackPressed()
        }

        initView()

//        val data = intent.getParcelableExtra<HijackPoliciesModel>(Constants.intentPolicyValue)


        viewModel.getPolicyCombineData().observe(this, Observer {
            //            if (data == null) {
//                policyName = it.policies[0].first
//                policyValue = it.policies[0].second
//            } else {
//                policyValue = data
//                policyName = intent.getStringExtra(Constants.intentPolicyName)
//            }
            UsersRepo.userModel?.second?.apply {
                policyName = this.hijack_policy_selected
            }
            if (!TextUtils.isEmpty(policyName)) {
                it.policies.forEach { policy ->
                    if (policy.first == policyName) {
                        policyValue = policy.second
                    }
                }
            } else {
                policyName = it.policies[0].first
                policyValue = it.policies[0].second
            }
            campaigns = it.campaigns
            policyLocations = it.policyLocations
            lytSpinner.isEnabled = true
            policyLocation = it.policyLocations[0]
            tvYourLocation.text = it.policyLocations[0].second.location
            progressBar.visibility = View.GONE
            if (groupTop.visibility == View.INVISIBLE) {
                groupTop.visibility = View.VISIBLE
            }
            UsersRepo.userModel?.second?.campaign?.apply {
                campaignName = campaign_id
                if (!TextUtils.isEmpty(campaign_id)) {
                    checkDataReturn(true)
                } else
                    checkDataReturn(false)
            }
        })

        lytSpinner.setOnClickListener {
            LocationsDialogFragment().show(supportFragmentManager, "policy_location")
        }

        btnChooseLocation.setOnClickListener {
            AlertDialog.Builder(this)
                    .setMessage(resources.getString(R.string.campaign_dialog_message))
                    .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                        //                        campaign = policyLocation?.first?.let { locationId ->
//                            CampaignsModel(policyName, false, locationId, 0, 0)
//                        }
//                        campaignName = "campaign_${campaigns?.size?.plus(1)}"
//                        policiesViewModel.campaignCreated(campaign, campaignName)
                        UsersRepo.userModel?.second?.apply {
                            campaign?.campaign_id = campaignName
                            campaign?.signatures_collected = campaignModel?.signatures_collected
                                    ?: 0
                            UsersRepo.updateOrCreateUser(this)
                        }

                        checkDataReturn(true)
                        dialog.dismiss()
                    }
                    .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                        AlertDialog.Builder(this)
                                .setMessage(resources.getString(R.string.policy_location_not_choose))
                                .setPositiveButton(resources.getString(R.string.yes)) { dialog1, _ ->
                                    dialog1.dismiss()
                                }.create().show()
                    }.create().show()
        }

        btnPlannedUpdate.setOnClickListener {
            UsersRepo.userModel?.second?.apply {
                signatures_pledged = etPlannedSign.text.toString().toInt()
                UsersRepo.updateOrCreateUser(this)
            }
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }

        btnCollectedUpdate.setOnClickListener {
            UsersRepo.userModel?.second?.apply {
                campaign?.signatures_collected = etCollectedSign.text.toString().toInt()
                UsersRepo.updateOrCreateUser(this)
            }
            CampaignsRepo.setValue(campaignName, "signatures_collected", etCollectedSign.text.toString().toInt())
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }

        btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, Constants.URL_SHARE_TEXT)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        btnLearnMore.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(Constants.INTENT_URL, Constants.URL_POLICY_VIDEO)
            startActivity(intent)
        }
    }

    fun selectedLocation(position: Int) {
        policyLocation = policyLocations?.get(position)
        tvYourLocation.text = policyLocation?.second?.location ?: ""
        checkDataReturn(false)
    }

    private fun checkDataReturn(isChoose: Boolean) {
        when {
            policyValue != null && campaigns.isNullOrEmpty() -> {
                situation = chooseLocation
                tvPolicyChosenContent.text = policyValue?.description
            }
            policyValue != null && !campaigns.isNullOrEmpty() && !policyLocations.isNullOrEmpty() -> {

                if (TextUtils.isEmpty(campaignName)) {
                    campaigns?.map {
                        if (policyName == it.second.hijack_policy && policyLocation?.first == it.second.location_id) {
                            campaignModel = it.second
                            campaignName = it.first
                        }
                    }
                } else {
                    campaigns?.map {
                        if (campaignName == it.first) {
                            campaignModel = it.second
                        }
                    }
                }
            }
        }

        if (campaignModel != null) {
            when (campaignModel?.live) {
                true -> situation = lived
                false -> situation = notLived
                null -> notLived
                else -> notLived
            }
        } else {
            situation = chooseLocation
        }
        tvPolicyChosenContent.text = policyValue?.description

        setViews(situation, isChoose)
    }

    private fun initView() {
        progressBar.visibility = View.VISIBLE
        groupTop.visibility = View.INVISIBLE
        lytBottom.visibility = View.INVISIBLE
        lytRequirement.visibility = View.INVISIBLE
        lytChooseLocation.visibility = View.INVISIBLE
    }

    private fun setViews(situation: Int, isChoose: Boolean) {
        if (isChoose) {
            when (situation) {
                chooseLocation -> {
                    lytChooseLocation.visibility = View.VISIBLE
                    btnChooseLocation.visibility = View.VISIBLE
                    lytRequirement.visibility = View.INVISIBLE
                    lytBottom.visibility = View.GONE
                    lytSpinner.isEnabled = true
                    tvLocation.text = resources.getString(R.string.policy_location_campaigns)
                    tvLocationContent.text = resources.getString(R.string.policy_location_not_live)
                }
                notLived -> {
                    lytChooseLocation.visibility = View.VISIBLE
                    btnChooseLocation.visibility = View.GONE
                    imgTriangle.visibility = View.INVISIBLE
                    lytRequirement.visibility = View.VISIBLE
                    groupRequirement.visibility = View.INVISIBLE
                    lytBottom.visibility = View.VISIBLE
                    tvNotLive.visibility = View.VISIBLE
                    tvPlannedSign.visibility = View.INVISIBLE
                    etPlannedSign.visibility = View.VISIBLE
                    tvLivedNotice.visibility = View.GONE
                    btnCollectedUpdate.isEnabled = false
                    etCollectedSign.isEnabled = false
                    lytSpinner.isEnabled = false
                    tvLocation.text = resources.getString(R.string.policy_location_campaigns)
                    tvLocationContent.text = resources.getString(R.string.policy_location_not_live)
                    etPlannedSign.setText(UsersRepo.userModel?.second?.signatures_pledged.toString())
                }
                lived -> {
                    lytChooseLocation.visibility = View.GONE
                    lytRequirement.visibility = View.VISIBLE
                    groupRequirement.visibility = View.VISIBLE
                    tvNotLive.visibility = View.GONE
                    lytBottom.visibility = View.VISIBLE
                    tvPlannedSign.visibility = View.INVISIBLE
                    etPlannedSign.visibility = View.VISIBLE
                    tvLivedNotice.visibility = View.VISIBLE
                    btnCollectedUpdate.isEnabled = true
                    etCollectedSign.isEnabled = true
                    tvLocation.text = resources.getString(R.string.policy_location_live)
                    tvLocationContent.text = policyLocation?.second?.location
                    etCollectedSign.setText(campaignModel?.signatures_collected.toString())
                    tvSignaturesRequired.text = campaignModel?.signatures_required.toString()
//                tvPlannedSign.text = UsersRepo.userModel?.second?.signatures_pledged.toString()
                    etPlannedSign.setText(UsersRepo.userModel?.second?.signatures_pledged.toString())
                    tvTotalCollected.text = campaignModel?.signatures_collected.toString()
                }
            }
        } else {
            lytChooseLocation.visibility = View.VISIBLE
            btnChooseLocation.visibility = View.VISIBLE
            lytRequirement.visibility = View.INVISIBLE
            lytBottom.visibility = View.GONE
            lytSpinner.isEnabled = true
            when (situation) {
                lived -> {
                    tvLocation.text = resources.getString(R.string.policy_location_live)
                    tvLocationContent.text = policyLocation?.second?.location
                }
                else -> {
                    tvLocation.text = resources.getString(R.string.policy_location_campaigns)
                    tvLocationContent.text = resources.getString(R.string.policy_location_not_live)
                }
            }
        }
    }


    override fun getLayoutId(): Int = R.layout.activity_policy_control_center

    override fun getViewModelClass(): Class<PoliciesViewModel> = PoliciesViewModel::class.java

    override fun onBackPressed() {
        if (campaignModel != null) {
            intent.putExtra(Constants.INTENT_CAMPAIGN_VALUE, campaignModel)
            intent.putExtra(Constants.INTENT_CAMPAIGN_NAME, campaignName)
        }
        setResult(Activity.RESULT_OK, intent)
        this.finish()
    }
}