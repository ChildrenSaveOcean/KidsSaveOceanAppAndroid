package com.kidssaveocean.fatechanger.splashScreen;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kidssaveocean.fatechanger.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final static int SPLASH_DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            CheckOnboardingStatusTask checkOnboardingStatusTask = new CheckOnboardingStatusTask();
            checkOnboardingStatusTask.execute(this);
        },SPLASH_DURATION);
    }
}
