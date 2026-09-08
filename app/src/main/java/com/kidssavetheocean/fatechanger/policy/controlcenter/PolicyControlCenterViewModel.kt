package com.kidssavetheocean.fatechanger.policy.controlcenter

import androidx.lifecycle.viewModelScope
import com.kidssavetheocean.fatechanger.firebase.model.UsersModel
import com.kidssavetheocean.fatechanger.firebase.repository.HijackPoliciesRepo
import com.kidssavetheocean.fatechanger.firebase.repository.HijackPolicyLocation
import com.kidssavetheocean.fatechanger.firebase.repository.HijackPolicyLocationRepo
import com.kidssavetheocean.fatechanger.firebase.repository.UsersRepo
import com.kidssavetheocean.fatechanger.firebase.repository.fromPair
import com.kidssavetheocean.fatechanger.policy.controlcenter.viewmodel.ControlCenterUiEvent
import com.kidssavetheocean.fatechanger.presentation.AbstractViewModel
import com.kidssavetheocean.fatechanger.service.coroutines.ICoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PolicyControlCenterViewModel @Inject constructor(
    contextProvider: ICoroutineContextProvider,
    private val hijackPoliciesRepo: HijackPoliciesRepo,
    private val policyLocationRepo: HijackPolicyLocationRepo,
) : AbstractViewModel(contextProvider) {

    val userModel: Pair<String, UsersModel> = UsersRepo.userModel

    private val _uiState = MutableStateFlow(
        PolicyControlCenterUiState(isLoadingData = true),
    )

    val uiState: StateFlow<PolicyControlCenterUiState> = _uiState.asStateFlow()

    fun onEvent(uiEvent: ControlCenterUiEvent) {
        when (uiEvent) {
            is ControlCenterUiEvent.LocationChosen -> {
                _uiState.update {
                    it.copy(isLoadingData = true)
                }
                UsersRepo.userModel.second.apply {
                    location_id = uiEvent.location.locationId
                    UsersRepo.updateOrCreateUser(this)
                }
                _uiState.update {
                    it.copy(selectedLocation = uiEvent.location, isLoadingData = false)
                }
            }

            is ControlCenterUiEvent.PlannedSignaturesUpdated -> {
                val signatures = uiEvent.amount
                UsersRepo.userModel.second.apply {
                    signatures_pledged = signatures
                    UsersRepo.updateOrCreateUser(this)
                }
            }
        }
    }

    fun loadControlCenterData() {
        viewModelScope.launch {
            hijackPoliciesRepo.getData { result ->
                result.onSuccess { policies ->
                    val policy = policies.find {
                        it.first == userModel.second.hijack_policy_selected
                    }
                    if (policy == null) {
                        return@getData
                    }
                    _uiState.update {
                        it.copy(selectedPolicy = fromPair(policy))
                    }
                    updateUiState()
                }
            }
            policyLocationRepo.getData { result ->
                result.onSuccess { locations ->
                    _uiState.update { state ->
                        state.copy(
                            policyLocations = locations.map {
                                HijackPolicyLocation.fromPair(
                                    it,
                                )
                            },
                        )
                    }
                    updateUiState()
                }
            }
        }
    }

    private fun updateUiState() {
        if (_uiState.value.selectedPolicy?.description?.isNotEmpty() == true) {
            _uiState.update {
                val selectedLocation = _uiState.value.policyLocations.find { location ->
                    location.locationId == userModel.second.location_id
                }
                it.copy(isLoadingData = false, selectedLocation = selectedLocation)
            }
        } else if (_uiState.value.policyLocations.isNotEmpty()) {

        }
        else {
            _uiState.update {
                it.copy(isLoadingData = false, selectedPolicy = null)
            }
        }
    }
}
