package com.kidssaveocean.fatechanger.splashScreen

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask

import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.database.AppDatabase
import com.kidssaveocean.fatechanger.database.entities.KeyValue
import com.kidssaveocean.fatechanger.onboarding.OnboardingActivity

class CheckOnboardingStatusTask : AsyncTask<Activity, Void, Boolean>() {

    override fun doInBackground(vararg activities: Activity): Boolean? {
        val db = AppDatabase.getAppDatabase(activities[0])
        val keyValue = db?.keyValueDao()?.getKeyValue(KeyValue.ONBOARDING)
        val clazz = if (keyValue != null) BottomNavigationActivity::class.java else OnboardingActivity::class.java
        activities[0].startActivity(Intent(activities[0], clazz))
        activities[0].finish()
        return true
    }
}
