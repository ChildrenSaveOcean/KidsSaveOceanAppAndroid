package com.fatechanger.app.firebase.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatechanger.app.R
import com.fatechanger.app.dagger.component.DaggerHijackPolicyComponent
import com.fatechanger.app.firebase.repository.PolicyStepsRepo
import com.fatechanger.app.presentation.AbstractViewModel
import com.fatechanger.app.service.coroutines.ICoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PolicyStepsViewModel @Inject constructor(contextProvider: ICoroutineContextProvider) : AbstractViewModel(contextProvider){
    lateinit var policySteps: MutableLiveData<List<String>>
    @Inject lateinit var policyStepsRepo: PolicyStepsRepo

    init {
        DaggerHijackPolicyComponent.builder().build().inject(this)
    }

    fun getLiveDataPolicySteps(context: Context): LiveData<List<String>>{
            val steps : MutableList<String> = mutableListOf()
            steps.apply {
                add(context.resources.getString(R.string.step_one))
                add(context.resources.getString(R.string.step_two))
                add(context.resources.getString(R.string.step_three))
                add(context.resources.getString(R.string.step_four))
                add(context.resources.getString(R.string.step_five))
                add(context.resources.getString(R.string.step_six))
                add(context.resources.getString(R.string.step_seven))
            }
        policySteps = policyStepsRepo.getLiveDataSteps()
        policySteps.value = steps
        return policySteps
    }
}