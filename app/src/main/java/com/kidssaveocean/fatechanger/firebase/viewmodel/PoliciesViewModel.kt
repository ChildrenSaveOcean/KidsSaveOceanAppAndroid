package com.kidssaveocean.fatechanger.firebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kidssaveocean.fatechanger.dagger.component.DaggerHijackPolicyComponent
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPolicyLocationModel
import com.kidssaveocean.fatechanger.firebase.model.PolicyCombineData
import com.kidssaveocean.fatechanger.firebase.repository.CampaignsRepo
import com.kidssaveocean.fatechanger.firebase.repository.HijackPoliciesRepo
import com.kidssaveocean.fatechanger.firebase.repository.HijackPolicyLocationRepo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import javax.inject.Inject

class PoliciesViewModel : ViewModel() {
    private val disposables = mutableListOf<Disposable>()
    lateinit var liveDataPolicies: MutableLiveData<List<Pair<String, HijackPoliciesModel>>>
    @Inject
    lateinit var hijackPoliciesRepo: HijackPoliciesRepo

    @Inject
    lateinit var campaignsRepo: CampaignsRepo
    lateinit var liveDataCampaigns: MutableLiveData<List<Pair<String, CampaignsModel>>>

    @Inject
    lateinit var policyLocationRepo: HijackPolicyLocationRepo
    lateinit var liveDataPolicyLocations: MutableLiveData<List<Pair<String, HijackPolicyLocationModel>>>

    init {
        DaggerHijackPolicyComponent.builder().build().inject(this)
    }

    fun getLiveDataPolicies(): LiveData<List<Pair<String, HijackPoliciesModel>>> {
        liveDataPolicies = MutableLiveData()
        disposables.add(hijackPoliciesRepo.getData().subscribe({
            liveDataPolicies.value = it
        }, {}))
        return liveDataPolicies
    }

    fun getLiveDataCampaigns(): LiveData<List<Pair<String, CampaignsModel>>> {
        liveDataCampaigns = MutableLiveData()
        disposables.add(campaignsRepo.getData().subscribe ({
            liveDataCampaigns.value = it
        }, {}))
        return liveDataCampaigns
    }

    fun getLiveDataPolicyLocations(): LiveData<List<Pair<String, HijackPolicyLocationModel>>> {
        liveDataPolicyLocations = MutableLiveData()
        disposables.add(policyLocationRepo.getData().subscribe ({
            liveDataPolicyLocations.value = it
        }, {}))
        return liveDataPolicyLocations
    }


    fun getPolicyCombineData(): LiveData<PolicyCombineData>{
        val policyCombineData = MutableLiveData<PolicyCombineData>()
        disposables.add(Single.zip(hijackPoliciesRepo.getData(),
                campaignsRepo.getData(),
                policyLocationRepo.getData(),
                Function3<List<Pair<String, HijackPoliciesModel>>, List<Pair<String, CampaignsModel>>, List<Pair<String, HijackPolicyLocationModel>>, PolicyCombineData> { t1, t2, t3 ->
                    PolicyCombineData(t1, t2, t3)
                }).subscribe ({
            policyCombineData.value = it
        }, {}))
        return policyCombineData
    }

    fun policyVote(policyName: String, valueName: String, value: Any) {
        hijackPoliciesRepo.setValue(policyName, valueName, value)
    }

    fun campaignCreated(campaignsModel: CampaignsModel?, campaignName: String) {
        campaignsRepo.campaignCreated(campaignsModel, campaignName)
    }

    fun setSignatureRequest(campaignName: String, requestNumber: Int) {
        campaignsRepo.setSignatureRequest(campaignName, requestNumber)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.map {
            it.dispose()
        }
    }
}