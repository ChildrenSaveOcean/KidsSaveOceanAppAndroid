package com.fatechanger.app.firebase.repository

import com.fatechanger.app.Constants
import com.fatechanger.app.firebase.model.HijackPoliciesModel
import com.google.firebase.database.FirebaseDatabase

object HijackPoliciesRepo: BaseFirebaseDBRepo<HijackPoliciesModel, List<Pair<String, HijackPoliciesModel>>>(Constants.TABLE_NAME_HIJACK_POLICIES, HijackPoliciesModel::class.java) {

    override fun handleData(list: List<Pair<String, HijackPoliciesModel>>): List<Pair<String, HijackPoliciesModel>> {
        return list
    }

    fun setValue(policyName: String, valueName: String, value: Any){
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_HIJACK_POLICIES).child(policyName).child(valueName).setValue(value)
    }
}