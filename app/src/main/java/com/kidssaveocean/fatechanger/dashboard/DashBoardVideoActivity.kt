package com.kidssaveocean.fatechanger.dashboard

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.databinding.ActivityDashboardVideoBinding
import com.kidssaveocean.fatechanger.presentation.mvvm.activity.AbstractActivity
import com.kidssaveocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardVideoActivity : AbstractActivity<ActivityDashboardVideoBinding, EmptyViewModel>() {
    private lateinit var flNoInternet: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flNoInternet = findViewById(R.id.fl_no_internet)
        updateFlNoInternet()

        //todo fix the youtube video player everywhere
        val fragment = supportFragmentManager.findFragmentById(R.id.link_fragment) as YouTubePlayerSupportFragment?
        fragment?.initialize(DEVELOPER_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
                if (!wasRestored) {
                    youTubePlayer.cueVideo(INTRO_VIDEO_STRING)
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
                Toast.makeText(this@DashBoardVideoActivity, R.string.Unable_To_Play_Youtube_Video, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateFlNoInternet() {
        //todo fix
//        flNoInternet.visibility = if (isNetworkConnected()) View.GONE else View.VISIBLE
    }




    override fun getLayoutId(): Int = R.layout.activity_dashboard_video

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    companion object {

        const val INTRO_TYPE = "INTRO_TYPE"
        private const val DEVELOPER_KEY = "Apparently you don't need a key to play videos"
        private const val INTRO_VIDEO_STRING = "x2oClSfdtTY"
    }
}

