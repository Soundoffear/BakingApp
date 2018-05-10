package com.example.soundoffear.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.soundoffear.bakingapp.Utilities.ExoplayerHandlerUtility;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class FullScreenExoActivity extends AppCompatActivity {

    public static final String FULL_SCREEN_URI = "fullscreen_uri_player";
    boolean destroyVideo = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer_fullscreen);

        Intent fullScreenIntent = getIntent();

        SimpleExoPlayerView simpleExoPlayer = findViewById(R.id.fullscreenExoPlayer);

        ExoplayerHandlerUtility.getInstance().uriExoplayer(this,
                Uri.parse(fullScreenIntent.getStringExtra(FULL_SCREEN_URI)),
                simpleExoPlayer);
        ExoplayerHandlerUtility.getInstance().goToForeground();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

    }

    @Override
    public void onBackPressed() {
        destroyVideo = false;
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ExoplayerHandlerUtility.getInstance().goToBacground();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(destroyVideo) {
            ExoplayerHandlerUtility.getInstance().releasePlayer();
        }
    }
}
