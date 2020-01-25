package com.kidssaveocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.TextureView
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.dagger.component.DaggerHijackPolicyComponent
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssaveocean.fatechanger.firebase.repository.UsersRepo
import com.kidssaveocean.fatechanger.firebase.viewmodel.PoliciesViewModel
import kotlinx.android.synthetic.main.activity_policy_vote.*
import kotlinx.android.synthetic.main.view_toolbar.*

class PolicyVoteActivity : BaseActivity() {
    private var votes: Int = 0
    private lateinit var policyName: String
    private var policyValue: HijackPoliciesModel? = null

    private var policies: MutableList<Pair<String, HijackPoliciesModel>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_vote)

        toolbar.setOnClickListener{
            onBackPressed()
        }

        DaggerHijackPolicyComponent.builder().build().inject(this)

        val policesViewModel = ViewModelProviders.of(this).get(PoliciesViewModel::class.java)

        policesViewModel.getLiveDataPolicies().observe(this, Observer {
            if (!it.isNullOrEmpty()){
                policies.addAll(it)

                val policyDes = policies.map { policy -> policy.second.description }.toList()
                wPickerPolicyDes?.apply {
                    data = policyDes
                    visibility = View.VISIBLE
                    pbPolicies.visibility = View.INVISIBLE
                    setOnItemSelectedListener { _, _, position ->
                        policyValue = policies[position].second
                        policyName = policies[position].first
                        tvSummaryContent.text = policyValue?.summary
                        votes = policyValue?.votes ?: 0
                    }
                }
                policyValue = policies[0].second
                policyName = policies[0].first
                votes = policyValue?.votes ?: 0
                tvSummaryContent.text = policyValue?.summary
                if (!TextUtils.isEmpty(UsersRepo.userModel?.second?.hijack_policy_selected)){
                    btnVote.isEnabled = false
                    AlertDialog.Builder(this@PolicyVoteActivity)
                            .setMessage(resources.getString(R.string.policy_vote_already))
                            .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                                dialog.dismiss()
                            }.create().show()

                }else{
                    btnVote.isEnabled = true
                }
            }
        })



        btnVote?.apply {
            isEnabled = false
            setOnClickListener {
                AlertDialog.Builder(this@PolicyVoteActivity)
                        .setMessage(resources.getString(R.string.policy_vote_dialog_message))
                        .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                            policesViewModel.policyVote(policyName,"votes", votes + 1)
                            UsersRepo.userModel?.second?.apply {
                                hijack_policy_selected = policyName
                                UsersRepo.updateOrCreateUser(this)
                            }
                            dialog.dismiss()
                            onBackPressed()
                        }
                        .setNegativeButton(resources.getString(R.string.no)){ dialog, _ ->
                            dialog.dismiss()
                        }.create().show()
            }
        }
    }

    override fun onBackPressed() {
        if (policyValue != null) {
            val intent = Intent()
            intent.putExtra(Constants.intentPolicyName, policyName)
            intent.putExtra(Constants.intentPolicyValue, policyValue)
            setResult(Activity.RESULT_OK, intent)
        }
        this.finish()
    }
}