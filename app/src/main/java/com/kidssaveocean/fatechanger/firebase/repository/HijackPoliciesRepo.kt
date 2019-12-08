package com.kidssaveocean.fatechanger.firebase.repository

import com.google.firebase.database.FirebaseDatabase
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel

object HijackPoliciesRepo: BaseFirebaseDBRepo<HijackPoliciesModel, List<Pair<String, HijackPoliciesModel>>>(Constants.TABLE_NAME_HIJACK_POLICIES, HijackPoliciesModel::class.java) {

    override fun handleData(list: List<Pair<String, HijackPoliciesModel>>): List<Pair<String, HijackPoliciesModel>> {
        return list
    }

    fun setValue(policyName: String, valueName: String, value: Any){
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_HIJACK_POLICIES).child(policyName).child(valueName).setValue(value)
    }
}