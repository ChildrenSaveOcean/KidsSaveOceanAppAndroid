package com.fatechanger.app.firebase.repository

import com.fatechanger.app.Constants
import com.fatechanger.app.firebase.model.CampaignsModel
import com.google.firebase.database.FirebaseDatabase

object CampaignsRepo: BaseFirebaseDBRepo<CampaignsModel, List<Pair<String, CampaignsModel>>>(Constants.TABLE_NAME_CAMPAIGNS, CampaignsModel::class.java) {

    override fun handleData(list: List<Pair<String, CampaignsModel>>): List<Pair<String, CampaignsModel>> {
        return list
    }

    fun createCampaign(campaignsModel: CampaignsModel?, campaignName: String){
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).apply {
            push()
            child("hijack_policy").setValue(campaignsModel?.hijack_policy)
            child("live").setValue(campaignsModel?.live)
            child("location_id").setValue(campaignsModel?.location_id)
            child("signatures_collected").setValue(campaignsModel?.signatures_collected)
            child("signatures_required").setValue(campaignsModel?.signatures_required)}
    }

    fun setValue(campaignName: String, childName: String, requestNumber: Int){
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).child(childName).setValue(requestNumber)
    }
}