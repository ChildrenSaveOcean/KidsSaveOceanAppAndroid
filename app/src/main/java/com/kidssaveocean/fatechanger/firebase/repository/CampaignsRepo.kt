package com.kidssaveocean.fatechanger.firebase.repository

import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel

object CampaignsRepo: BaseFirebaseDBRepo<CampaignsModel, List<CampaignsModel>>(Constants.TABLE_NAME_CAMPAIGNS, CampaignsModel::class.java) {

    override fun handleData(list: List<Pair<String, CampaignsModel>>): List<CampaignsModel> {
        return list.map {
            it.second
        }
    }
}