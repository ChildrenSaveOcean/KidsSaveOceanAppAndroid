package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.common.BaseActivity

class PolicyVideoActivity: BaseActivity() {
    private lateinit var flNoInternet: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_video)

        flNoInternet = findViewById(R.id.fl_no_internet)
        flNoInternet.visibility = if (isNetworkConnected()) View.VISIBLE else View.GONE

        val fragment = supportFragmentManager.findFragmentById(R.id.frgVideo) as YouTubePlayerSupportFragment?
        fragment?.initialize(DEVELOPER_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
                if (!wasRestored) {
                    youTubePlayer.cueVideo(POLICY_VIDEO_STRING)
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
                Toast.makeText(this@PolicyVideoActivity, R.string.Unable_To_Play_Youtube_Video, Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        private const val DEVELOPER_KEY = "Apparently you don't need a key to play videos"
        private const val POLICY_VIDEO_STRING = "HQTUWK7CM-Y"
    }
}