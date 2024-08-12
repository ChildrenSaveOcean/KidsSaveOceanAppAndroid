package com.kidssaveocean.fatechanger.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.ActivityDashboardVideoBinding
import com.kidssaveocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardVideoActivity : AbstractActivity<ActivityDashboardVideoBinding, EmptyViewModel>() {
    private lateinit var flNoInternet: FrameLayout
    //todo why is this an activity maaaan

    @SuppressLint("SetJavaScriptEnabled") // we need javascript for youtube videos to load
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flNoInternet = findViewById(R.id.fl_no_internet)
        updateFlNoInternet()

        val videoData = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/x2oClSfdtTY?si=6MD97ZGP2JCiirtr\" " +
                "title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; " +
                "picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>"
        binding.dashboardVideoWebView.apply {
            settings.javaScriptEnabled = true
            loadData(videoData, "text/html", "utf-8")
            //webChromeClient = WebChromeClient() -- might need this, the webview is running really bad on emulator, so it needs more testing
        }
    }

    private fun updateFlNoInternet() {
        flNoInternet.visibility = if (isNetworkConnected()) View.GONE else View.VISIBLE
    }

    override fun getLayoutId(): Int = R.layout.activity_dashboard_video

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java
}

