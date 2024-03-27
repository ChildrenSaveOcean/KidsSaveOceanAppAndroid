package com.fatechanger.app.splashScreen

import android.app.Activity
import android.content.Intent
import com.fatechanger.app.bottomNavigation.BottomNavigationActivity
import com.fatechanger.app.database.AppDatabase
import com.fatechanger.app.database.entities.KeyValue
import com.fatechanger.app.onboarding.OnboardingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
