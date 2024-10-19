package com.kidssavetheocean.fatechanger.onboarding

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssavetheocean.fatechanger.databinding.ActivityOnboardingBinding
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_onboarding.viewpager

@AndroidEntryPoint
class OnboardingActivity : AbstractActivity<ActivityOnboardingBinding, OnboardingViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        viewModel.checkStatus()
        binding.root.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                // Check whether the initial data is ready.
                if (!viewModel.isReady) return false
                //Check whether onboarding has been completed
                if (viewModel.hasCompletedOnboarding) return false
                return true
            }
        })
        viewModel.onboardingStatus.observe(this) {
            if (it) {
                openActivity(BottomNavigationActivity::class)
            }
        }
        viewpager.adapter = OnboardingAdapter(supportFragmentManager)
        binding.dotIndicator.setupWithViewPager(viewpager)
    }

    override fun getLayoutId() = R.layout.activity_onboarding

    override fun getViewModelClass() = OnboardingViewModel::class.java
}