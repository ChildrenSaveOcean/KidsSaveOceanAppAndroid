package com.kidssaveocean.fatechanger.firebase.repository

import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.firebase.model.UsersModel

object UsersRepo: BaseFirebaseDBRepo<UsersModel, List<Pair<String, UsersModel>>>(Constants.TABLE_NAME_USERS, UsersModel::class.java) {
    override fun handleData(list: List<Pair<String, UsersModel>>): List<Pair<String, UsersModel>> {
        return list
    }
}