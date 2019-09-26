package com.kidssaveocean.fatechanger.firebase.model

data class CampaignsModel(val hijack_policy: String = "", val live: Boolean = false,
                          val location_id: String = "", val signatures_collected: Int = 0,
                          val signatures_pledged: Int = 0, val signatures_required: Int = 0)