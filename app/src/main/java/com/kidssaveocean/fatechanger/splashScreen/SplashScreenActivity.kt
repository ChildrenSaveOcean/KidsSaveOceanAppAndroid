package com.kidssaveocean.fatechanger.splashScreen

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // defined theme in manifest: "@style/SplashTheme"
        // layout not needed

        Handler().postDelayed({
            CheckOnboardingStatusTask().execute(this)
        }, SPLASH_DURATION.toLong())
    }

    companion object {

        private const val SPLASH_DURATION = 500
    }
}
