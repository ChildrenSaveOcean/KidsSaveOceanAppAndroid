package com.fatechanger.app.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fatechanger.app.R
import kotlinx.android.synthetic.main.activity_onboarding.dot_indicator
import kotlinx.android.synthetic.main.activity_onboarding.viewpager

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        viewpager.adapter = OnboardingAdapter(supportFragmentManager)
        dot_indicator.setupWithViewPager(viewpager)
    }

}
