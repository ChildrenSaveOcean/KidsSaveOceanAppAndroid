package com.kidssavetheocean.fatechanger.firebase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HijackPoliciesModel(
    val description: String = "",
    val summary: String = "",
    val votes: Int = 0,
    val difficulty: Double = 0.0,
    val Impact: Double = 0.0
) : Parcelable