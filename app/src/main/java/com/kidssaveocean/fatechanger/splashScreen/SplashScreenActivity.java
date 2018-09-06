package com.kidssaveocean.fatechanger.splashScreen;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.kidssaveocean.fatechanger.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final static int SPLASH_DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // defined theme in manifest: "@style/SplashTheme"
        // layout not needed

        new Handler().postDelayed(() -> {
            CheckOnboardingStatusTask checkOnboardingStatusTask = new CheckOnboardingStatusTask();
            checkOnboardingStatusTask.execute(this);
        },SPLASH_DURATION);
    }
}
