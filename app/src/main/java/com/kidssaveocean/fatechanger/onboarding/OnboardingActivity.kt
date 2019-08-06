package com.kidssaveocean.fatechanger.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.kidssaveocean.fatechanger.R

import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        viewpager.adapter = OnboardingAdapter(supportFragmentManager)
        dot_indicator.setupWithViewPager(viewpager)
    }

}
