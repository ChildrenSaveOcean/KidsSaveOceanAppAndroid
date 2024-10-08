package com.kidssavetheocean.fatechanger.onboarding.userIdentification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssavetheocean.fatechanger.databinding.ActivityIntroductionVideoBinding
import com.kidssavetheocean.fatechanger.onboarding.OnboardingViewModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import com.kidssavetheocean.fatechanger.sharedprefs.FateChangerSharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_introduction_video.youtube_text_view

@AndroidEntryPoint
class IntroductionVideoActivity : AbstractActivity<ActivityIntroductionVideoBinding, EmptyViewModel>() {

    private lateinit var flNoInternet: FrameLayout

    @SuppressLint("SetJavaScriptEnabled") // we need javascript for youtube videos to load
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(INTRO_TYPE)
        youtube_text_view.text = title
        flNoInternet = findViewById(R.id.fl_no_internet)
        updateFlNoInternet()

        val videoData = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/aWbPiPh_gaU?si=dLHSbFkaeqQ5DHOZ\" " +
                "title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
                "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
        binding.videoWebView.apply {
            settings.javaScriptEnabled = true
            loadData(videoData, "text/html", "utf-8")
//            webChromeClient = WebChromeClient() -- might need this, the webview is running really bad on emulator, so it needs more testing
        }

    }

    private fun updateFlNoInternet() {
        flNoInternet.visibility = if (isNetworkConnected()) View.GONE else View.VISIBLE
    }


    fun clickStartButton(view: View) {
        val prefs = FateChangerSharedPrefs(this)
        prefs.edit {
            putBoolean(OnboardingViewModel.ONBOARDING_STATUS_KEY, true)
        }
        val intent = Intent(this, BottomNavigationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    override fun getLayoutId(): Int = R.layout.activity_introduction_video

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    companion object {
        const val INTRO_TYPE = "INTRO_TYPE"
    }

}
