package com.fatechanger.app.firebase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Campaign(var campaign_id: String = "", var signatures_collected: Int = 0) : Parcelable