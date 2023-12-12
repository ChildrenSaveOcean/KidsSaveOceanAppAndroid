package com.kidssaveocean.fatechanger.splashScreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    //todo fix the damn splashscreen?
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // defined theme in manifest: "@style/SplashTheme"
        // layout not needed

        Handler(Looper.getMainLooper()).postDelayed({
            GlobalScope.launch {
                CheckOnboardingStatusTask().execute(this@SplashScreenActivity)
            }
        }, SPLASH_DURATION.toLong())
    }

    companion object {

        private const val SPLASH_DURATION = 500
    }
}
