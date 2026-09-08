package com.kidssavetheocean.fatechanger.policy.controlcenter

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.WebViewActivity
import com.kidssavetheocean.fatechanger.databinding.ActivityPolicyControlCenterBinding
import com.kidssavetheocean.fatechanger.firebase.model.CampaignsModel
import com.kidssavetheocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssavetheocean.fatechanger.firebase.model.HijackPolicyLocationModel
import com.kidssavetheocean.fatechanger.firebase.repository.CampaignsRepo
import com.kidssavetheocean.fatechanger.firebase.repository.UsersRepo
import com.kidssavetheocean.fatechanger.policy.LocationsDialogFragment
import com.kidssavetheocean.fatechanger.policy.controlcenter.view.PolicyControlCenterScreen
import com.kidssavetheocean.fatechanger.presentation.KstoTheme
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyControlCenterActivity : AbstractActivity<ActivityPolicyControlCenterBinding, PolicyControlCenterViewModel>() {
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
        binding.policyControlCenterToolbarView.toolbar.setOnClickListener {
            onBackPressedCompat()
        }

        initView()

        viewModel.loadControlCenterData()

        setContent {
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            KstoTheme {
                PolicyControlCenterScreen(state) {
                    viewModel.onEvent(it)
                }
            }
        }

//        val data = intent.getParcelableExtra<HijackPoliciesModel>(Constants.intentPolicyValue)


//        viewModel.getPolicyCombineData().observe(this, Observer {
//            //            if (data == null) {
////                policyName = it.policies[0].first
////                policyValue = it.policies[0].second
////            } else {
////                policyValue = data
////                policyName = intent.getStringExtra(Constants.intentPolicyName)
////            }
//            UsersRepo.userModel?.second?.apply {
//                policyName = this.hijack_policy_selected
//            }
//            if (!TextUtils.isEmpty(policyName)) {
//                it.policies.forEach { policy ->
//                    if (policy.first == policyName) {
//                        policyValue = policy.second
//                    }
//                }
//            } else {
//                policyName = it.policies[0].first
//                policyValue = it.policies[0].second
//            }
//            campaigns = it.campaigns
//            policyLocations = it.policyLocations
//            binding.lytChooseLocation.lytSpinner.isEnabled = true
//            policyLocation = it.policyLocations[0]
//            with(binding) {
//                lytChooseLocation.lytSpinner.isEnabled = true
//                lytChooseLocation.tvYourLocation.text = it.policyLocations[0].second.location
//                progressBar.visibility = View.GONE
//                if (groupTop.isInvisible) {
//                    groupTop.visibility = View.VISIBLE
//                }
//            }
//            UsersRepo.userModel?.second?.campaign?.apply {
//                campaignName = campaign_id
//                if (!TextUtils.isEmpty(campaign_id)) {
//                    checkDataReturn(true)
//                } else checkDataReturn(false)
//            }
//        })

        binding.lytChooseLocation.lytSpinner.setOnClickListener {
            LocationsDialogFragment().show(supportFragmentManager, "policy_location")
        }

        binding.lytChooseLocation.btnChooseLocation.setOnClickListener {
            AlertDialog.Builder(this)
                    .setMessage(resources.getString(R.string.campaign_dialog_message))
                    .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                        //                        campaign = policyLocation?.first?.let { locationId ->
//                            CampaignsModel(policyName, false, locationId, 0, 0)
//                        }
//                        campaignName = "campaign_${campaigns?.size?.plus(1)}"
//                        policiesViewModel.campaignCreated(campaign, campaignName)
                        UsersRepo.userModel.second.apply {
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
        with(binding) {
            with(lytBottom) {
                btnPlannedUpdate.setOnClickListener {
                    UsersRepo.userModel?.second?.apply {
                        signatures_pledged = etPlannedSign.text.toString().toInt()
                        UsersRepo.updateOrCreateUser(this)
                    }
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                }

                btnCollectedUpdate.setOnClickListener {
                    UsersRepo.userModel?.second?.apply {
                        campaign?.signatures_collected = etCollectedSign.text.toString().toInt()
                        UsersRepo.updateOrCreateUser(this)
                    }
                    CampaignsRepo.setValue(
                        campaignName,
                        "signatures_collected",
                        etCollectedSign.text.toString().toInt()
                    )
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                }
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
                val intent = Intent(this@PolicyControlCenterActivity, WebViewActivity::class.java)
                intent.putExtra(Constants.INTENT_URL, Constants.URL_POLICY_VIDEO)
                startActivity(intent)
            }
        }
    }

    fun selectedLocation(position: Int) {
        policyLocation = policyLocations?.get(position)
        binding.lytChooseLocation.tvYourLocation.text = policyLocation?.second?.location ?: ""
        checkDataReturn(false)
    }

    private fun checkDataReturn(isChoose: Boolean) {
        when {
            policyValue != null && campaigns.isNullOrEmpty() -> {
                situation = chooseLocation
                binding.tvPolicyChosenContent.text = policyValue?.description
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
        binding.tvPolicyChosenContent.text = policyValue?.description

        setViews(situation, isChoose)
    }

    private fun initView() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            groupTop.visibility = View.INVISIBLE

            lytBottom.root.visibility = View.INVISIBLE
            lytChooseLocation.root.visibility = View.INVISIBLE
        }
    }

    private fun setViews(situation: Int, isChoose: Boolean) {
        if (isChoose) {
            when (situation) {
                chooseLocation -> {
                    with(binding) {
                        lytChooseLocation.root.visibility = View.VISIBLE
                        lytBottom.root.visibility = View.GONE
                        tvLocation.text = resources.getString(R.string.policy_location_campaigns)
                        tvLocationContent.text =
                            resources.getString(R.string.policy_location_not_live)
                        lytChooseLocation.lytSpinner.isEnabled = true
                        lytChooseLocation.btnChooseLocation.visibility = View.VISIBLE
                    }
                }
                notLived -> {
                    with(binding) {
                        lytChooseLocation.root.visibility = View.VISIBLE
                        lytBottom.root.visibility = View.VISIBLE
                        tvLocation.text = resources.getString(R.string.policy_location_campaigns)
                        tvLocationContent.text =
                            resources.getString(R.string.policy_location_not_live)
                        with(lytChooseLocation) {
                            imgTriangle.visibility = View.INVISIBLE
                            btnChooseLocation.visibility = View.GONE
                            lytSpinner.isEnabled = false
                        }
                        with(lytBottom) {
                            tvPlannedSign.visibility = View.INVISIBLE
                            etPlannedSign.visibility = View.VISIBLE
                            tvLivedNotice.visibility = View.GONE
                            btnCollectedUpdate.isEnabled = false
                            etCollectedSign.isEnabled = false
                            etPlannedSign.setText(UsersRepo.userModel?.second?.signatures_pledged.toString())
                        }
                    }
                }
                lived -> {
                    with(binding) {
                        lytChooseLocation.root.visibility = View.GONE
                        lytBottom.root.visibility = View.VISIBLE
                        tvLocation.text = resources.getString(R.string.policy_location_live)
                        tvLocationContent.text = policyLocation?.second?.location
                        with(lytBottom) {
                            tvPlannedSign.visibility = View.INVISIBLE
                            etPlannedSign.visibility = View.VISIBLE
                            tvLivedNotice.visibility = View.VISIBLE
                            btnCollectedUpdate.isEnabled = true
                            etCollectedSign.isEnabled = true
                            etCollectedSign.setText(campaignModel?.signatures_collected.toString())
//                tvPlannedSign.text = UsersRepo.userModel?.second?.signatures_pledged.toString()
                            etPlannedSign.setText(UsersRepo.userModel?.second?.signatures_pledged.toString())
                        }
                    }
                }
            }
        } else {
            with(binding) {
                lytChooseLocation.root.visibility = View.VISIBLE
                lytBottom.root.visibility = View.GONE
                when (situation) {
                    lived -> {
                        tvLocation.text = resources.getString(R.string.policy_location_live)
                        tvLocationContent.text = policyLocation?.second?.location
                    }

                    else -> {
                        tvLocation.text = resources.getString(R.string.policy_location_campaigns)
                        tvLocationContent.text =
                            resources.getString(R.string.policy_location_not_live)
                    }
                }
                binding.lytChooseLocation.btnChooseLocation.visibility = View.VISIBLE
                binding.lytChooseLocation.lytSpinner.isEnabled = true
            }
        }
    }


    override fun getLayoutId(): Int = R.layout.activity_policy_control_center

    override fun getViewModelClass(): Class<PolicyControlCenterViewModel> = PolicyControlCenterViewModel::class.java

    override fun onBackPressedCompat() {
        if (campaignModel != null) {
            intent.putExtra(Constants.INTENT_CAMPAIGN_VALUE, campaignModel)
            intent.putExtra(Constants.INTENT_CAMPAIGN_NAME, campaignName)
        }
        setResult(RESULT_OK, intent)
        this.finish()
    }
}
