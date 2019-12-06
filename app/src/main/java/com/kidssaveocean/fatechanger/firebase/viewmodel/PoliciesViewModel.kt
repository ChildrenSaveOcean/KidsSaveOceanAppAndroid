package com.kidssaveocean.fatechanger.firebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kidssaveocean.fatechanger.dagger.component.DaggerHijackPolicyComponent
import com.kidssaveocean.fatechanger.firebase.model.CampaignsModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssaveocean.fatechanger.firebase.model.HijackPolicyLocationModel
import com.kidssaveocean.fatechanger.firebase.repository.CampaignsRepo
import com.kidssaveocean.fatechanger.firebase.repository.HijackPoliciesRepo
import com.kidssaveocean.fatechanger.firebase.repository.HijackPolicyLocationRepo
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class PoliciesViewModel: ViewModel() {
    private val disposables = mutableListOf<Disposable>()
    lateinit var liveDataPolicies: MutableLiveData<List<Pair<String, HijackPoliciesModel>>>
    @Inject lateinit var hijackPoliciesRepo: HijackPoliciesRepo

    @Inject lateinit var campaignsRepo: CampaignsRepo
    lateinit var liveDataCampaigns: MutableLiveData<List<Pair<String, CampaignsModel>>>

    @Inject lateinit var policyLocationRepo: HijackPolicyLocationRepo
    lateinit var liveDataPolicyLocations: MutableLiveData<List<Pair<String, HijackPolicyLocationModel>>>

    init {
        DaggerHijackPolicyComponent.builder().build().inject(this)
    }

    fun getLiveDataPolicies(): LiveData<List<Pair<String, HijackPoliciesModel>>> {
        liveDataPolicies = MutableLiveData()
        disposables.add(hijackPoliciesRepo.getDataObservable().subscribe {
            liveDataPolicies.value = it
        })
        hijackPoliciesRepo.getData()
        return liveDataPolicies
    }

    fun getLiveDataCampaigns(): LiveData<List<Pair<String, CampaignsModel>>>{
        liveDataCampaigns = MutableLiveData()
        disposables.add(campaignsRepo.getDataObservable().subscribe {
            liveDataCampaigns.value = it
        })
        campaignsRepo.getData()
        return liveDataCampaigns
    }

    fun getLiveDataPolicyLocations(): LiveData<List<Pair<String, HijackPolicyLocationModel>>>{
        liveDataPolicyLocations = MutableLiveData()
        disposables.add(policyLocationRepo.getDataObservable().subscribe {
            liveDataPolicyLocations.value = it
        })
        policyLocationRepo.getData()
        return liveDataPolicyLocations
    }

    fun policyVote(policyName: String, valueName: String, value: Any){
        hijackPoliciesRepo.setValue(policyName, valueName, value)
    }

    fun campaignCreated(campaignsModel: CampaignsModel?, campaignName: String){
        campaignsRepo.campaignCreated(campaignsModel, campaignName)
    }

    fun setSignatureRequest(campaignName: String, requestNumber: Int){
        campaignsRepo.setSignatureRequest(campaignName, requestNumber)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.map {
            it.dispose()
        }
    }
}