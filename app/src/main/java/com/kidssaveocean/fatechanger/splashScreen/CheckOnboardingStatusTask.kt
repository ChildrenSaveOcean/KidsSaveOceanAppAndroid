package com.kidssaveocean.fatechanger.splashScreen

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask

import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.common.PrefsManager
import com.kidssaveocean.fatechanger.onboarding.OnboardingActivity

class CheckOnboardingStatusTask : AsyncTask<Activity, Void, Boolean>() {

    override fun doInBackground(vararg activities: Activity): Boolean? {

        val clazz = if (PrefsManager.isOnBoardingComplete) {
            BottomNavigationActivity::class.java
        } else {
            OnboardingActivity::class.java
        }
        activities[0].startActivity(Intent(activities[0], clazz))
        activities[0].finish()
        return true
    }
}
