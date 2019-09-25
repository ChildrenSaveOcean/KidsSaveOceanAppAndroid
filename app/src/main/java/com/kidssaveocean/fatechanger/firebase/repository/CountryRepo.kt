package com.kidssaveocean.fatechanger.firebase.repository

import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.firebase.model.CountryModel

object CountryRepo : BaseFirebaseDBRepo<CountryModel, List<Pair<String, CountryModel>>>(Constants.TABLE_NAME_COUNTRY, CountryModel::class.java) {
    override fun handleData(list: List<Pair<String, CountryModel>>): List<Pair<String, CountryModel>> {
        return list
    }
}