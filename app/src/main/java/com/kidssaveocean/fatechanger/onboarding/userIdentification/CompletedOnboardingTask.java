package com.kidssaveocean.fatechanger.onboarding.userIdentification;

import android.content.Context;
import android.os.AsyncTask;

import com.kidssaveocean.fatechanger.database.AppDatabase;
import com.kidssaveocean.fatechanger.database.entities.Onboarding;

public class CompletedOnboardingTask extends AsyncTask<Context, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Context... context) {
        AppDatabase db = AppDatabase.getAppDatabase(context[0].getApplicationContext());
        Onboarding onboarding = new Onboarding();
        onboarding.setUId(1);
        onboarding.setOnboardingCompeleted(true);
        db.onboardingDao().insertOnboaring(onboarding);
        return true;
    }
}
