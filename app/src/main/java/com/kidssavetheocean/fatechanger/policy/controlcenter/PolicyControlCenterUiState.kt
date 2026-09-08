package com.kidssavetheocean.fatechanger.policy.controlcenter

import com.kidssavetheocean.fatechanger.firebase.repository.HijackPolicy
import com.kidssavetheocean.fatechanger.firebase.repository.HijackPolicyLocation

data class PolicyControlCenterUiState(
    val isLoadingPolicyData: Boolean = false,
    val isLoadingLocationsData: Boolean = false,
    val selectedPolicy: HijackPolicy? = null,
    val policyLocations: List<HijackPolicyLocation> = emptyList(),
    val selectedLocation: HijackPolicyLocation? = null,
    val plannedSignatures: Int = 0
)
