package com.kidssaveocean.fatechanger.policy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import java.text.DecimalFormat

class PolicyVoteActivity : BaseActivity() {
    private var votes: Int = 0
    private lateinit var policyName: String
    private var policyValue: HijackPoliciesModel? = null
    private val decimalFormat = DecimalFormat("0.0")

    private var policies: MutableList<Pair<String, HijackPoliciesModel>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_vote)

        toolbar.setOnClickListener{
            onBackPressed()
        }

        DaggerHijackPolicyComponent.builder().build().inject(this)

        val policesViewModel = ViewModelProviders.of(this).get(PoliciesViewModel::class.java)
        val temproaryData = initTemporaryData()
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
                        val impact =  temproaryData.get(position)[0]
                        val difficulty = temproaryData.get(position)[1]
                        tvImpactValue.text = decimalFormat.format(impact)
                        tvDifficultyValue.text = decimalFormat.format(difficulty)
                        tvImpactDivDifficultyValue.text = decimalFormat.format(impact/difficulty)
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
            intent.putExtra(Constants.INTENT_POLICY_NAME, policyName)
            intent.putExtra(Constants.INTENT_POLICY_VALUE, policyValue)
            setResult(Activity.RESULT_OK, intent)
        }
        this.finish()
    }

    private fun initTemporaryData(): MutableList<FloatArray>{
        val temporaryData = mutableListOf<FloatArray>()
        temporaryData.add(floatArrayOf(7.2f, 5.5f))
        temporaryData.add(floatArrayOf(8.8f, 6.0f))
        temporaryData.add(floatArrayOf(6.8f, 5.2f))
        temporaryData.add(floatArrayOf(8.8f, 7.8f))
        temporaryData.add(floatArrayOf(8.0f, 5.3f))
        temporaryData.add(floatArrayOf(8.5f, 4.0f))
        temporaryData.add(floatArrayOf(9.2f, 5.3f))
        temporaryData.add(floatArrayOf(7.2f, 5.7f))
        temporaryData.add(floatArrayOf(8.2f, 6.0f))
        return temporaryData
    }
}