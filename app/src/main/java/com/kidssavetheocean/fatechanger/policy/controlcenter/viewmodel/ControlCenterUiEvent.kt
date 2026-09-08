package com.kidssavetheocean.fatechanger.policy.controlcenter.viewmodel

import com.kidssavetheocean.fatechanger.firebase.repository.HijackPolicyLocation

sealed class ControlCenterUiEvent {
    class LocationChosen(val location: HijackPolicyLocation): ControlCenterUiEvent()
    class PlannedSignaturesUpdated(val amount: Int): ControlCenterUiEvent()
}
