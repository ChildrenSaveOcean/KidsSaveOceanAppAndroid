package com.kidssavetheocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.WebViewActivity
import com.kidssavetheocean.fatechanger.databinding.FragmentPolicyHomeBinding
import com.kidssavetheocean.fatechanger.firebase.model.CampaignsModel
import com.kidssavetheocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssavetheocean.fatechanger.policy.vote.PolicyVoteFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyHomeFragment : AbstractFragment<FragmentPolicyHomeBinding, EmptyViewModel>() {

    private var policyValue: HijackPoliciesModel? = null
    private lateinit var policyName: String
    private var campaign: CampaignsModel? = null
    private lateinit var campaignName: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        supportActionBar?.run {
//            setHomeButtonEnabled(true)
//            setDisplayShowHomeEnabled(true)
//            setDisplayShowTitleEnabled(false)
//            setDisplayHomeAsUpEnabled(true)
//        }

        binding.policyHomeToolbar.toolbar.setNavigationOnClickListener {
            navigateBack()

        }

        binding.imgHowToWork.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra(Constants.INTENT_URL, Constants.URL_POLICY_VIDEO)
            startActivity(intent)
        }

        binding.imgFollowStep.setOnClickListener {
            startActivity(Intent(requireContext(), PolicyStepsActivity::class.java))
        }

        binding.imgPolicyPush.setOnClickListener {
            navigateToView(PolicyVoteFragment::class)
//            startActivityForResult(Intent(requireContext(), PolicyVoteFragment::class.java), Constants.REQUEST_POLICY_VOTE)
        }

        binding.imgImpact.setOnClickListener {
            startActivity(Intent(requireContext(), PolicyImpactActivity::class.java))
        }

        binding.imgSignatures.setOnClickListener {
            val intent = Intent(requireContext(), PolicyControlCenterActivity::class.java)
            if (policyValue != null) {
                intent.putExtra(Constants.INTENT_POLICY_VALUE, policyValue)
                intent.putExtra(Constants.INTENT_POLICY_NAME, policyName)
            }
            startActivityForResult(intent, Constants.REQUEST_POLICY_CONTROL_CENTER)
        }

        binding.imgTrackTheHijack.setOnClickListener {
            val intent = Intent(requireContext(), TrackCampaignActivity::class.java)
            if (campaign != null) {
                intent.putExtra(Constants.INTENT_CAMPAIGN_NAME, campaignName)
                intent.putExtra(Constants.INTENT_CAMPAIGN_VALUE, campaign)
            }
            startActivity(intent)
        }

        arguments?.let {
            policyName = it.getString(Constants.INTENT_POLICY_NAME) ?: ""
            policyValue = it.getParcelable(Constants.INTENT_POLICY_VALUE)
        }
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_policy_home

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constants.REQUEST_POLICY_VOTE -> {
                    data?.apply {
                        policyValue = getParcelableExtra(Constants.INTENT_POLICY_VALUE)
                        policyName = getStringExtra(Constants.INTENT_POLICY_NAME) ?: ""
                    }
                }

                Constants.REQUEST_POLICY_CONTROL_CENTER -> {
                    data?.apply {
                        campaign = getParcelableExtra(Constants.INTENT_CAMPAIGN_VALUE)
                        campaignName = getStringExtra(Constants.INTENT_CAMPAIGN_NAME) ?: ""
                    }
                }
            }
        }
    }
}