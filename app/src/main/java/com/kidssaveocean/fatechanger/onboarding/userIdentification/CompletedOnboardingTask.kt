package com.kidssaveocean.fatechanger.onboarding.userIdentification

import android.content.Context
import android.os.AsyncTask

import com.kidssaveocean.fatechanger.database.AppDatabase
import com.kidssaveocean.fatechanger.database.entities.KeyValue

class CompletedOnboardingTask : AsyncTask<Context, Void, Boolean>() {

    override fun doInBackground(vararg context: Context): Boolean? {
        val db = AppDatabase.getAppDatabase(context[0].applicationContext)
        val onboarding = KeyValue(KeyValue.ONBOARDING, "COMPLETE")
        db!!.keyValueDao().insertKeyValue(onboarding)
        return true
    }
}
