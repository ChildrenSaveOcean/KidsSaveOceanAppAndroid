package com.kidssaveocean.fatechanger.onboarding.userIdentification

import android.content.Context
import android.os.AsyncTask
import com.kidssaveocean.fatechanger.common.PrefsManager

class CompletedOnboardingTask : AsyncTask<Context, Void, Boolean>() {

    override fun doInBackground(vararg context: Context): Boolean? {
        PrefsManager.isOnBoardingComplete = true
        return true
    }
}
