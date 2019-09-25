package com.kidssaveocean.fatechanger.firebase.repository

import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.firebase.model.HijackPolicyLocationModel

object HijackPolicyLocationRepo: BaseFirebaseDBRepo<HijackPolicyLocationModel,
        List<Pair<String, HijackPolicyLocationModel>>>(Constants.TABLE_NAME_HIJACK_POLICY_LOCATION, HijackPolicyLocationModel::class.java) {

    override fun handleData(list: List<Pair<String, HijackPolicyLocationModel>>): List<Pair<String, HijackPolicyLocationModel>> {
        return list
    }
}