package com.kidssaveocean.fatechanger.onboarding

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kidssaveocean.fatechanger.presentation.AbstractViewModel
import com.kidssaveocean.fatechanger.service.coroutines.ICoroutineContextProvider
import com.kidssaveocean.fatechanger.sharedprefs.FateChangerSharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(@ApplicationContext context: Context, contextProvider: ICoroutineContextProvider) :
    AbstractViewModel(contextProvider) {

    var isReady = false
        private set

    var hasCompletedOnboarding = false
        private set

    public val onboardingStatus = MutableLiveData<Boolean>()


    private val sharedPrefs: FateChangerSharedPrefs

    init {
        sharedPrefs = FateChangerSharedPrefs(context)
    }

    fun checkStatus() {
        launch(Dispatchers.IO) {
            hasCompletedOnboarding = sharedPrefs.getBoolean(ONBOARDING_STATUS_KEY, false)
            onboardingStatus.postValue(hasCompletedOnboarding)

            isReady = true
        }
    }

    companion object {
        const val ONBOARDING_STATUS_KEY = "onboarding_status"
    }
}