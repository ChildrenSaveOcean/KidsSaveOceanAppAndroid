package com.fatechanger.app.firebase.repository

import com.fatechanger.app.Constants
import com.fatechanger.app.firebase.model.PoliciesModel

object PoliciesRepo: BaseFirebaseDBRepo<PoliciesModel, List<PoliciesModel>>(Constants.TABLE_NAME_POLICIES, PoliciesModel::class.java) {
    override fun handleData(list: List<Pair<String, PoliciesModel>>): List<PoliciesModel> {
        return list.map {
            it.second
        }
    }
}