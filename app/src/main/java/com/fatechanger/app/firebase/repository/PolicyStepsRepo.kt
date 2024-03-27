package com.fatechanger.app.firebase.repository

import androidx.lifecycle.MutableLiveData

object PolicyStepsRepo: BaseFirebaseDBRepo<String, List<String>>("", String::class.java) {

    override fun handleData(list: List<Pair<String, String>>): List<String> {
        return list.map {
            it.second
        }
    }

    fun getLiveDataSteps(): MutableLiveData<List<String>>{
        return MutableLiveData()
    }
}