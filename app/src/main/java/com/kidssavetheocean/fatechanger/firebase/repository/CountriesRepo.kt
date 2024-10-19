package com.kidssavetheocean.fatechanger.firebase.repository

import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.firebase.model.CountryModel

object CountriesRepo : BaseFirebaseDBRepo<CountryModel, List<Pair<String, CountryModel>>>(Constants.TABLE_NAME_COUNTRY, CountryModel::class.java) {
    override fun handleData(list: List<Pair<String, CountryModel>>): List<Pair<String, CountryModel>> {
        return list
    }
}