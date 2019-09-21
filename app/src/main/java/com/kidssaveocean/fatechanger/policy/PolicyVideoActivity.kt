package com.kidssaveocean.fatechanger.policy

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.kidssaveocean.fatechanger.R

class PolicyVideoActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_policy_video)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment) as YouTubePlayerSupportFragment?
        fragment?.initialize(DEVELOPER_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
                if (!wasRestored) {
                    youTubePlayer.cueVideo(INTRO_VIDEO_STRING)
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
                Toast.makeText(this@PolicyVideoActivity, R.string.Unable_To_Play_Youtube_Video, Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        private const val DEVELOPER_KEY = "Apparently you don't need a key to play videos"
        private const val INTRO_VIDEO_STRING = "aWbPiPh_gaU"
    }
}