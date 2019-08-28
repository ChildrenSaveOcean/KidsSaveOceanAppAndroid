package com.kidssaveocean.fatechanger.onboarding.userIdentification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity

import kotlinx.android.synthetic.main.activity_introduction_video.*

class IntroductionVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction_video)

        val title = intent.getStringExtra(INTRO_TYPE)
        youtube_text_view.text = title

        val fragment = supportFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerSupportFragment?
        fragment!!.initialize(DEVELOPER_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
                if (!wasRestored) {
                    youTubePlayer.cueVideo(INTRO_VIDEO_STRING)
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
                Toast.makeText(this@IntroductionVideoActivity, R.string.Unable_To_Play_Youtube_Video, Toast.LENGTH_LONG).show()
            }
        })

    }

    fun clickStartButton(view : View) {
        val completedOnboardingTask = CompletedOnboardingTask()
        completedOnboardingTask.execute(this)
        val intent = Intent(this, BottomNavigationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    companion object {

        const val INTRO_TYPE = "INTRO_TYPE"
        private const val DEVELOPER_KEY = "Apparently you don't need a key to play videos"
        private const val INTRO_VIDEO_STRING = "aWbPiPh_gaU"
    }

}
