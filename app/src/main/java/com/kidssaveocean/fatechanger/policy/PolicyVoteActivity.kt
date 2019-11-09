package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssaveocean.fatechanger.firebase.repository.HijackPoliciesRepo
import kotlinx.android.synthetic.main.activity_policy_vote.*
import kotlinx.android.synthetic.main.view_toolbar.*

class PolicyVoteActivity : BaseActivity() {
    private lateinit var policies: MutableList<Pair<String, HijackPoliciesModel>>
    private var votes: Int = 0
    private lateinit var policyName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_vote)

        toolbar.setOnClickListener{
            onBackPressed()
        }

        disposableOnDestroy(HijackPoliciesRepo.getDataObservable().subscribe {
            policies = mutableListOf()
            policies.addAll(it)

            val policyDes = policies.map { policy -> policy.second.description }.toList()
            wPickerPolicyDes?.apply {
                data = policyDes
                visibility = View.VISIBLE
                setOnItemSelectedListener { _, _, position ->
                   tvSummaryContent.text = policies[position].second.summary
                   votes = policies[position].second.votes
                   policyName = policies[position].first
                }
            }
            policyName = policies[0].first
            tvSummaryContent.text = policies[0].second.summary
            btnVote.isEnabled = true
        })

        btnVote?.apply {
            isEnabled = false
            setOnClickListener {
                AlertDialog.Builder(this@PolicyVoteActivity)
                        .setMessage("Are you sure you want to vote for this policy?")
                        .setPositiveButton("Yes!") { dialog, _ ->
                            HijackPoliciesRepo.setValue(policyName,"votes", votes + 1)
                            dialog.dismiss()
                        }
                        .setNegativeButton("No"){ dialog, _ ->
                            dialog.dismiss()
                        }.create().show()
            }
        }
    }
}