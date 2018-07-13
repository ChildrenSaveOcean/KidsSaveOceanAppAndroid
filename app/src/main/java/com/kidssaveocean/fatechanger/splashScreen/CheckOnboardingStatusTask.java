package com.kidssaveocean.fatechanger.splashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity;
import com.kidssaveocean.fatechanger.database.entities.Onboarding;
import com.kidssaveocean.fatechanger.onboarding.OnboardingActivity;

import com.kidssaveocean.fatechanger.database.AppDatabase;

public class CheckOnboardingStatusTask extends AsyncTask<Activity, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Activity... activities) {
        Intent intent;

        AppDatabase db = AppDatabase.getAppDatabase(activities[0]);
        Onboarding onboarding = db.onboardingDao().getOnboarding();

        if (onboarding != null && onboarding.isOnboardingCompeleted()) {
            intent = new Intent(activities[0], BottomNavigationActivity.class);
        }
        else {
            intent = new Intent(activities[0], OnboardingActivity.class);
        }
        activities[0].startActivity(intent);activities[0].finish();

        return true;
    }
}
