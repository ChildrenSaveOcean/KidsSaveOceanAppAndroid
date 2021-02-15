package com.kidssaveocean.fatechanger.dashboard

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.google.android.youtube.player.YouTubePlayerView
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity
import com.kidssaveocean.fatechanger.onboarding.userIdentification.IntroductionVideoActivity
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.view_toolbar.*

class DashBoardVideoActivity : BaseActivity() {
    private lateinit var flNoInternet: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_video)

        flNoInternet = findViewById(R.id.fl_no_internet)
        flNoInternet.visibility = if (isNetworkConnected()) View.GONE else View.VISIBLE

        val fragment = supportFragmentManager.findFragmentById(R.id.link_fragment) as YouTubePlayerSupportFragment?
        fragment?.initialize(DEVELOPER_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
                if (!wasRestored) {
                    youTubePlayer.cueVideo(INTRO_VIDEO_STRING)
                    flNoInternet.visibility = View.GONE
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {

                flNoInternet.visibility = View.VISIBLE
                Toast.makeText(this@DashBoardVideoActivity, R.string.Unable_To_Play_Youtube_Video, Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {

        const val INTRO_TYPE = "INTRO_TYPE"
        private const val DEVELOPER_KEY = "Apparently you don't need a key to play videos"
        private const val INTRO_VIDEO_STRING = "x2oClSfdtTY"
    }
}