package com.kidssavetheocean.fatechanger.firebase.repository

import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.firebase.model.HijackPolicyLocationModel

object HijackPolicyLocationRepo: BaseFirebaseDBRepo<HijackPolicyLocationModel,
        List<Pair<String, HijackPolicyLocationModel>>>(Constants.TABLE_NAME_HIJACK_POLICY_LOCATION, HijackPolicyLocationModel::class.java) {

    override fun handleData(list: List<Pair<String, HijackPolicyLocationModel>>): List<Pair<String, HijackPolicyLocationModel>> {
        return list
    }
}

data class HijackPolicyLocation(
    val locationId: String,
    val location: String
){
    companion object
}

fun HijackPolicyLocation.Companion.fromPair(pair: Pair<String, HijackPolicyLocationModel>): HijackPolicyLocation {
    return HijackPolicyLocation(
        pair.first,
        pair.second.location
    )
}
