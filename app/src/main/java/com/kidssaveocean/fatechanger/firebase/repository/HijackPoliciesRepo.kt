package com.kidssaveocean.fatechanger.firebase.repository

import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel

object HijackPoliciesRepo: BaseFirebaseDBRepo<HijackPoliciesModel, List<Pair<String, HijackPoliciesModel>>>(Constants.TABLE_NAME_HIJACK_POLICIES, HijackPoliciesModel::class.java){

    override fun handleData(list: List<Pair<String, HijackPoliciesModel>>): List<Pair<String, HijackPoliciesModel>> {
        return list
    }
}