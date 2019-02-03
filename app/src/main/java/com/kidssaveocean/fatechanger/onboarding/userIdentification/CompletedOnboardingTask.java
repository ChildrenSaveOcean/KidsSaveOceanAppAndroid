package com.kidssaveocean.fatechanger.onboarding.userIdentification;

import android.content.Context;
import android.os.AsyncTask;

import com.kidssaveocean.fatechanger.database.AppDatabase;
import com.kidssaveocean.fatechanger.database.entities.KeyValue;

public class CompletedOnboardingTask extends AsyncTask<Context, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Context... context) {
        AppDatabase db = AppDatabase.Companion.getAppDatabase(context[0].getApplicationContext());
        KeyValue onboarding = new KeyValue(KeyValue.ONBOARDING, "COMPLETE");
        db.keyValueDao().insertKeyValue(onboarding);
        return true;
    }
}
