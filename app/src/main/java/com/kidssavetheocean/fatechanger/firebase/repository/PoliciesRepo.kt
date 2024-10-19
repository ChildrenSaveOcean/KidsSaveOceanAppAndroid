package com.kidssavetheocean.fatechanger.firebase.repository

import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.firebase.model.PoliciesModel

object PoliciesRepo: BaseFirebaseDBRepo<PoliciesModel, List<PoliciesModel>>(Constants.TABLE_NAME_POLICIES, PoliciesModel::class.java) {
    override fun handleData(list: List<Pair<String, PoliciesModel>>): List<PoliciesModel> {
        return list.map {
            it.second
        }
    }
}