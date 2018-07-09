package com.kidssaveocean.fatechanger.introductionVideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.kidssaveocean.fatechanger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroductionVideoActivity extends AppCompatActivity {

    public static final String INTRO_TYPE = "INTRO_TYPE";

    private static final String DEVELOPER_KEY = "Apparently you don't need a key to play videos";
    private static final String INTRO_VIDEO_STRING = "aWbPiPh_gaU";

    @BindView(R.id.youtube_text_view) TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_video);

        ButterKnife.bind(this);

        String title = getIntent().getStringExtra(INTRO_TYPE);
        titleTextView.setText(title);

        YouTubePlayerSupportFragment fragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        fragment.initialize(DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer.cueVideo(INTRO_VIDEO_STRING);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(IntroductionVideoActivity.this, R.string.Unable_To_Play_Youtube_Video, Toast.LENGTH_LONG).show();
            }
        });
    }


}
