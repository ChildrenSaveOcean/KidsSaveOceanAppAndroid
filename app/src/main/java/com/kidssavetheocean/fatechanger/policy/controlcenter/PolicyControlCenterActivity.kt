package com.kidssavetheocean.fatechanger.policy.controlcenter

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.ActivityPolicyControlCenterBinding
import com.kidssavetheocean.fatechanger.policy.controlcenter.view.PolicyControlCenterScreen
import com.kidssavetheocean.fatechanger.presentation.KstoTheme
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyControlCenterActivity :
    AbstractActivity<ActivityPolicyControlCenterBinding, PolicyControlCenterViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.policyControlCenterToolbarView.toolbar.setOnClickListener {
            onBackPressedCompat()
        }

        viewModel.loadLocationsData()
        viewModel.loadPolicyData()

        setContent {
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            KstoTheme {
                PolicyControlCenterScreen(state) {
                    viewModel.onEvent(it)
                }
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_policy_control_center

    override fun getViewModelClass(): Class<PolicyControlCenterViewModel> = PolicyControlCenterViewModel::class.java

    override fun onBackPressedCompat() {
        setResult(RESULT_OK, intent)
        this.finish()
    }
}
