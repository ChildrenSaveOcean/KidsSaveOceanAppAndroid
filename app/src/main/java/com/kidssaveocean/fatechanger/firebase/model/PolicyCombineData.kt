package com.kidssaveocean.fatechanger.firebase.model

data class PolicyCombineData(val policies: List<Pair<String, HijackPoliciesModel>>, val campaigns: List<Pair<String, CampaignsModel>>, val policyLocations: List<Pair<String, HijackPolicyLocationModel>>)