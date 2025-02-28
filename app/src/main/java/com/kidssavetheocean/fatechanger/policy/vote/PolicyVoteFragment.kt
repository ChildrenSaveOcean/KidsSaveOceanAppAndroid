package com.kidssavetheocean.fatechanger.policy.vote

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentPolicyVoteBinding
import com.kidssavetheocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssavetheocean.fatechanger.firebase.repository.UsersRepo
import com.kidssavetheocean.fatechanger.firebase.viewmodel.PoliciesViewModel
import com.kidssavetheocean.fatechanger.policy.PolicyHomeFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class PolicyVoteFragment : AbstractFragment<FragmentPolicyVoteBinding, PoliciesViewModel>() {
    private var votes: Int = 0
    private lateinit var policyName: String
    private var policyValue: HijackPoliciesModel? = null
    private val decimalFormat = DecimalFormat("0.0")

    private var policies: MutableList<Pair<String, HijackPoliciesModel>> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.policyVoteToolbar.toolbar.setOnClickListener {
            navigateBack()
        }

        val temproaryData = initTemporaryData()
        viewModel.getLiveDataPolicies().observe(this.viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                policies.addAll(it)

                val policyDes = policies.map { policy -> policy.second.description }.toList()
                val viewAdapter = PolicyRecyclerViewAdapter(policyDes)
                binding.policyNamePicker.apply {
                    adapter = viewAdapter
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.pbPolicies.visibility = View.INVISIBLE
//                    minValue = 0
//                    maxValue = policyDes.size - 1
//                    displayedValues = policyDes.toTypedArray()
//                    visibility = View.VISIBLE
//                    pbPolicies.visibility = View.INVISIBLE
//                    setOnValueChangedListener { _, _, position ->
//                        policyValue = policies[position].second
//                        policyName = policies[position].first
//                        tvSummaryContent.text = policyValue?.summary
//                        val impact = temproaryData.get(position)[0]
//                        val difficulty = temproaryData.get(position)[1]
//                        binding.tvImpactValue.text = decimalFormat.format(impact)
//                        binding.tvImpactValue.text = decimalFormat.format(difficulty)
//                        binding.tvImpactDivDifficultyValue.text =
//                            decimalFormat.format(impact / difficulty)
//                        votes = policyValue?.votes ?: 0
//                    }

                }

                policyValue = policies[0].second
                policyName = policies[0].first
                votes = policyValue?.votes ?: 0
                binding.tvSummaryContent.text = policyValue?.summary
                if (!TextUtils.isEmpty(UsersRepo.userModel?.second?.hijack_policy_selected)) {
                    binding.btnVote.isEnabled = false
                    AlertDialog.Builder(requireContext())
                        .setMessage(resources.getString(R.string.policy_vote_already))
                        .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                            dialog.dismiss()
                        }.create().show()

                } else {
                    binding.btnVote.isEnabled = true
                }
            }
        })


        binding.btnVote.apply {
            isEnabled = false
            setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setMessage(resources.getString(R.string.policy_vote_dialog_message))
                    .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                        viewModel.policyVote(policyName, "votes", votes + 1)
                        UsersRepo.userModel?.second?.apply {
                            hijack_policy_selected = policyName
                            UsersRepo.updateOrCreateUser(this)
                        }
                        dialog.dismiss()
                        navigateBack()
                    }
                    .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
            }
        }

        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val bundle = Bundle()
                bundle.putString(Constants.INTENT_POLICY_NAME, policyName)
                bundle.putParcelable(Constants.INTENT_POLICY_VALUE, policyValue)
                navigateToView(PolicyHomeFragment::class, bundle)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, callback)

    }

    override fun getViewModelResId(): Int = BR.policyVoteViewModel

    override fun getLayoutResId(): Int = R.layout.fragment_policy_vote

    override fun getViewModelClass(): Class<PoliciesViewModel> = PoliciesViewModel::class.java

    private fun initTemporaryData(): MutableList<FloatArray> {
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