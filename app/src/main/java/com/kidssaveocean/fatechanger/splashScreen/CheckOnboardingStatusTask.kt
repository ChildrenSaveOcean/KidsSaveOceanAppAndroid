package com.kidssaveocean.fatechanger.splashScreen

import android.app.Activity
import android.content.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.database.AppDatabase
import com.kidssaveocean.fatechanger.database.entities.KeyValue
import com.kidssaveocean.fatechanger.onboarding.OnboardingActivity

class CheckOnboardingStatusTask {

    suspend fun execute(activity: Activity): Boolean {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getAppDatabase(activity)
            val keyValue = db?.keyValueDao()?.getKeyValue(KeyValue.ONBOARDING)
            val clazz = if (keyValue != null) BottomNavigationActivity::class.java else OnboardingActivity::class.java
            activity.startActivity(Intent(activity, clazz))
            activity.finish()
            true
        }
    }

}
