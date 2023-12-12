package com.kidssaveocean.fatechanger.dashboard

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.AbstractActivity
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.view_toolbar.*

class DashBoardVideoActivity : AbstractActivity() {
    private lateinit var flNoInternet: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_video)

        flNoInternet = findViewById(R.id.fl_no_internet)
        updateFlNoInternet()

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
        flNoInternet.visibility = if (isNetworkConnected()) View.GONE else View.VISIBLE
    }

    override fun networkChangeConnect() {
        super.networkChangeConnect()
        updateFlNoInternet()
    }

    companion object {

        const val INTRO_TYPE = "INTRO_TYPE"
        private const val DEVELOPER_KEY = "Apparently you don't need a key to play videos"
        private const val INTRO_VIDEO_STRING = "x2oClSfdtTY"
    }
}

