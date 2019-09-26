package com.kidssaveocean.fatechanger.firebase.repository

import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.firebase.model.ActionsModel

object ActionsRepo: BaseFirebaseDBRepo<ActionsModel, List<ActionsModel>>(Constants.TABLE_NAME_ACTIONS, ActionsModel::class.java) {

    override fun handleData(list: List<Pair<String, ActionsModel>>): List<ActionsModel> {
        return list.map {
            it.second
        }
    }
}