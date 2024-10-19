package com.kidssavetheocean.fatechanger.firebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kidssavetheocean.fatechanger.dagger.component.DaggerHijackPolicyComponent
import com.kidssavetheocean.fatechanger.firebase.model.HijackPoliciesModel
import com.kidssavetheocean.fatechanger.firebase.model.PolicyCombineData
import com.kidssavetheocean.fatechanger.firebase.repository.CampaignsRepo
import com.kidssavetheocean.fatechanger.firebase.repository.HijackPoliciesRepo
import com.kidssavetheocean.fatechanger.firebase.repository.HijackPolicyLocationRepo
import com.kidssavetheocean.fatechanger.presentation.AbstractViewModel
import com.kidssavetheocean.fatechanger.service.coroutines.ICoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoliciesViewModel @Inject constructor(contextProvider: ICoroutineContextProvider) : AbstractViewModel(contextProvider) {

    lateinit var liveDataPolicies: MutableLiveData<List<Pair<String, HijackPoliciesModel>>>

    @Inject
    lateinit var hijackPoliciesRepo: HijackPoliciesRepo

    @Inject
    lateinit var campaignsRepo: CampaignsRepo

    @Inject
    lateinit var policyLocationRepo: HijackPolicyLocationRepo

    init {
        DaggerHijackPolicyComponent.builder().build().inject(this)
    }

    fun getLiveDataPolicies(): LiveData<List<Pair<String, HijackPoliciesModel>>> {
        liveDataPolicies = MutableLiveData()
        backgroundScope.launch {
            hijackPoliciesRepo.getData {
                it.onSuccess { list ->
                    liveDataPolicies.value = list
                }
            }
        }
        return liveDataPolicies
    }

    fun getPolicyCombineData(): LiveData<PolicyCombineData> {
        val policyCombineData = MutableLiveData<PolicyCombineData>()
        //todo this is awful, but it is the fastest way to get the app running again
        backgroundScope.launch {
            hijackPoliciesRepo.getData { policy ->
                policy.onSuccess { pList ->
                    campaignsRepo.getData { campaign ->
                        campaign.onSuccess { cList ->
                            policyLocationRepo.getData { location ->
                                location.onSuccess { lList ->
                                    policyCombineData.value = PolicyCombineData(pList, cList, lList)
                                }
                            }
                        }
                    }
                }
            }
        }
        //keep this to try and figure out what should be happening
//        disposables.add(Single.zip(hijackPoliciesRepo.getData(),
//            campaignsRepo.getData(),
//            policyLocationRepo.getData(),
//            Function3<List<Pair<String, HijackPoliciesModel>>, List<Pair<String, CampaignsModel>>, List<Pair<String, HijackPolicyLocationModel>>, PolicyCombineData> { t1, t2, t3 ->
//                PolicyCombineData(t1, t2, t3)
//            }).subscribe({
//            policyCombineData.value = it
//        }, {})
//        )
        return policyCombineData
    }

    fun policyVote(policyName: String, valueName: String, value: Any) {
        hijackPoliciesRepo.setValue(policyName, valueName, value)
    }
}