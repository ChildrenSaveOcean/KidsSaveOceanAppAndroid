package com.kidssaveocean.fatechanger.firebase.repository

import com.google.firebase.database.FirebaseDatabase
import com.kidssaveocean.fatechanger.Constants
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel
object CampaignsRepo: BaseFirebaseDBRepo<CampaignsModel, List<Pair<String, CampaignsModel>>>(Constants.TABLE_NAME_CAMPAIGNS, CampaignsModel::class.java) {

    override fun handleData(list: List<Pair<String, CampaignsModel>>): List<Pair<String, CampaignsModel>> {
        return list
    }

    fun campaignCreated(campaignsModel: CampaignsModel?, campaignName: String){
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).push()
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).child("hijack_policy").setValue(campaignsModel?.hijack_policy)
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).child("live").setValue(campaignsModel?.live)
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).child("location_id").setValue(campaignsModel?.location_id)
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).child("signatures_collected").setValue(campaignsModel?.signatures_collected)
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).child("signatures_pledged").setValue(campaignsModel?.signatures_pledged)
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).child("signatures_required").setValue(campaignsModel?.signatures_required)
    }

    fun setSignatureRequest(campaignName: String, requestNumber: Int){
        FirebaseDatabase.getInstance().reference.child(Constants.TABLE_NAME_CAMPAIGNS).child(campaignName).child("signatures_required").setValue(requestNumber)
    }
}