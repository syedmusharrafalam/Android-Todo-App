package com.rapidapps.instagram.realtimedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ActivitySong extends YouTubeBaseActivity {
    //AIzaSyD17VjtAeYV_Ok-KVgTGg4HA7c6I5mY-pA

    YouTubePlayerView youTubePlayerView;
    public  static final String KEY="AIzaSyD17VjtAeYV_Ok-KVgTGg4HA7c6I5mY-pA";
    YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.video1);
        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("roMDA9GXVis");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize(KEY,onInitializedListener);

    }
}
