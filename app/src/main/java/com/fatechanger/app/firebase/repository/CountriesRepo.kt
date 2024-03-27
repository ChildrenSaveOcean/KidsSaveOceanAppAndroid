package com.fatechanger.app.firebase.repository

import com.fatechanger.app.Constants
import com.fatechanger.app.firebase.model.CountryModel

object CountriesRepo : BaseFirebaseDBRepo<CountryModel, List<Pair<String, CountryModel>>>(Constants.TABLE_NAME_COUNTRY, CountryModel::class.java) {
    override fun handleData(list: List<Pair<String, CountryModel>>): List<Pair<String, CountryModel>> {
        return list
    }
}