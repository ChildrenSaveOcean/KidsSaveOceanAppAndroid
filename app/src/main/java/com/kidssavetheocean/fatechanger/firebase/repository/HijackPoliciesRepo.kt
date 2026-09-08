package com.kidssavetheocean.fatechanger.firebase.repository

import com.google.firebase.database.FirebaseDatabase
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.firebase.model.HijackPoliciesModel

object HijackPoliciesRepo :
    BaseFirebaseDBRepo<HijackPoliciesModel, List<Pair<String, HijackPoliciesModel>>>(
        Constants.TABLE_NAME_HIJACK_POLICIES,
        HijackPoliciesModel::class.java
    ) {

    override fun handleData(list: List<Pair<String, HijackPoliciesModel>>): List<Pair<String, HijackPoliciesModel>> {
        return list
    }

    fun setValue(policyName: String, valueName: String, value: Any) {
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_HIJACK_POLICIES)
            .child(policyName).child(valueName).setValue(value)
    }
}

data class HijackPolicy(
    val id: String = "",
    val description: String = "",
    val summary: String = "",
    val votes: Int = 0,
    val difficulty: Double = 0.0,
    val impact: Double = 0.0
)

fun fromPair(pair: Pair<String, HijackPoliciesModel>): HijackPolicy {
    return HijackPolicy(
        pair.first,
        pair.second.description,
        pair.second.summary,
        pair.second.votes,
        pair.second.difficulty,
        pair.second.Impact
    )
}