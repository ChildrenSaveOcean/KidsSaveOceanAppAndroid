package com.kidssaveocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.WebViewActivity
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPolicyLocationModel
import com.kidssaveocean.fatechanger.firebase.viewmodel.PoliciesViewModel
import kotlinx.android.synthetic.main.activity_policy_control_center.*
import kotlinx.android.synthetic.main.policy_center_control_bottom.*
import kotlinx.android.synthetic.main.policy_control_center_location.*
import kotlinx.android.synthetic.main.policy_control_center_requirement.*
import kotlinx.android.synthetic.main.view_toolbar.*


class PolicyControlCenterActivity : BaseActivity() {
    var policyLocations: List<Pair<String, HijackPolicyLocationModel>>? = null
    private var campaigns: List<Pair<String, CampaignsModel>>? = null
    private var campaign: CampaignsModel? = null
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
        setContentView(R.layout.activity_policy_control_center)

        toolbar.setOnClickListener {
            onBackPressed()
        }

        initView()

        val data = intent.getParcelableExtra<HijackPoliciesModel>(Constants.intentPolicyValue)

        val policiesViewModel = ViewModelProviders.of(this).get(PoliciesViewModel::class.java)

        policiesViewModel.getPolicyCombineData().observe(this, Observer {
            if (data == null) {
                policyName = it.policies[0].first
                policyValue = it.policies[0].second
            } else {
                policyValue = data
                policyName = intent.getStringExtra(Constants.intentPolicyName)
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
            checkDataReturn()
        })

        lytSpinner.setOnClickListener {
            LocationsDialogFragment().show(supportFragmentManager, "policy_location")
        }

        btnChooseLocation.setOnClickListener {
            AlertDialog.Builder(this)
                    .setMessage(resources.getString(R.string.campaign_dialog_message))
                    .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                        campaign = policyLocation?.first?.let { locationId ->
                            CampaignsModel(policyName, false, locationId, 0, 0, 0)
                        }
                        campaignName = "campaign_${campaigns?.size?.plus(1)}"
                        policiesViewModel.campaignCreated(campaign, campaignName)
                        checkDataReturn()
                        dialog.dismiss()
                    }
                    .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
        }

        btnPlannedUpdate.setOnClickListener {
            if (situation == notLived) {
                policiesViewModel.setSignatureRequest(campaignName, etPlannedSign.text.toString().toInt())
                val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
        }

        btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, Constants.shareText)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        btnLearnMore.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }
    }

    fun selectedLocation(position: Int) {
        policyLocation = policyLocations?.get(position)
        tvYourLocation.text = policyLocation?.second?.location ?: ""
        checkDataReturn()
    }

    private fun checkDataReturn() {
        when {
            policyValue != null && campaigns.isNullOrEmpty() -> {
                situation = chooseLocation
                tvPolicyChosenContent.text = policyValue?.description
            }
            policyValue != null && !campaigns.isNullOrEmpty() && !policyLocations.isNullOrEmpty() -> {
                campaigns?.map {
                    if (policyName == it.second.hijack_policy && policyLocation?.first == it.second.location_id) {
                        campaign = it.second
                        campaignName = it.first
                    }
                }
                if (campaign != null) {
                    when (campaign?.live) {
                        true -> situation = lived
                        false -> situation = notLived
                    }
                } else {
                    situation = chooseLocation
                }
                tvPolicyChosenContent.text = policyValue?.description
            }
        }
        setViews(situation)
    }

    private fun initView() {
        progressBar.visibility = View.VISIBLE
        groupTop.visibility = View.INVISIBLE
        lytBottom.visibility = View.INVISIBLE
        lytRequirement.visibility = View.INVISIBLE
        lytChooseLocation.visibility = View.INVISIBLE
    }

    private fun setViews(situation: Int) {
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
                tvCollectedSign.isEnabled = false
                lytSpinner.isEnabled = false
                tvLocation.text = resources.getString(R.string.policy_location_campaigns)
                tvLocationContent.text = resources.getString(R.string.policy_location_not_live)
            }
            lived -> {
                lytChooseLocation.visibility = View.GONE
                lytRequirement.visibility = View.VISIBLE
                groupRequirement.visibility = View.VISIBLE
                tvNotLive.visibility = View.GONE
                lytBottom.visibility = View.VISIBLE
                tvPlannedSign.visibility = View.VISIBLE
                etPlannedSign.visibility = View.INVISIBLE
                tvLivedNotice.visibility = View.VISIBLE
                btnCollectedUpdate.isEnabled = true
                tvCollectedSign.isEnabled = true
                tvLocation.text = resources.getString(R.string.policy_location_live)
                tvLocationContent.text = policyLocation?.second?.location
                tvCollectedSign.text = campaign?.signatures_collected.toString()
                tvSignaturesRequired.text = campaign?.signatures_required.toString()
                tvPlannedSign.text = campaign?.signatures_required.toString()
            }
        }
    }

    override fun onBackPressed() {
        if (campaign != null){
            intent.putExtra(Constants.intentCampaignValue, campaign)
            intent.putExtra(Constants.intentCampaignName, campaignName)
        }
        setResult(Activity.RESULT_OK, intent)
        this.finish()
    }
}