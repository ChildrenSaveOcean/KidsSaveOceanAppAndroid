package com.fatechanger.app.firebase.repository

import com.fatechanger.app.Constants
import com.fatechanger.app.firebase.model.ActionModel

object ActionsRepo: BaseFirebaseDBRepo<ActionModel, List<ActionModel>>(Constants.TABLE_NAME_ACTIONS, ActionModel::class.java) {

    override fun handleData(list: List<Pair<String, ActionModel>>): List<ActionModel> {
        return list.map {
            it.second
        }
    }
}