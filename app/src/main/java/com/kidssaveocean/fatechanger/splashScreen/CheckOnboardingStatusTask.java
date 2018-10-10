package com.kidssaveocean.fatechanger.splashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity;
import com.kidssaveocean.fatechanger.database.AppDatabase;
import com.kidssaveocean.fatechanger.database.entities.KeyValue;
import com.kidssaveocean.fatechanger.onboarding.OnboardingActivity;

public class CheckOnboardingStatusTask extends AsyncTask<Activity, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Activity... activities) {
        Intent intent;

        AppDatabase db = AppDatabase.getAppDatabase(activities[0]);
        KeyValue keyValue = db.keyValueDao().getKeyValue(KeyValue.ONBOARDING);

        if (keyValue != null) {
            intent = new Intent(activities[0], BottomNavigationActivity.class);
        }
        else {
            intent = new Intent(activities[0], OnboardingActivity.class);
        }
        activities[0].startActivity(intent);
        activities[0].finish();

        return true;
    }
}
