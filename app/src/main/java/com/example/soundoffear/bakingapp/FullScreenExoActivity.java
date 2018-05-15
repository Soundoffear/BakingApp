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
    public static final String EXO_STATE = "exoplayer_state";
    boolean destroyVideo = true;
    private SimpleExoPlayerView simpleExoPlayer;
    private long playerPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer_fullscreen);

        Intent fullScreenIntent = getIntent();
        assert fullScreenIntent != null;
        playerPosition = fullScreenIntent.getLongExtra(EXO_STATE, 0);

        simpleExoPlayer = findViewById(R.id.fullscreenExoPlayer);

        ExoplayerHandlerUtility.getInstance().uriExoplayer(this,
                Uri.parse(fullScreenIntent.getStringExtra(FULL_SCREEN_URI)),
                simpleExoPlayer);
        ExoplayerHandlerUtility.getInstance().goToForeground();
        ExoplayerHandlerUtility.getInstance().setPlayerSeek(playerPosition);

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
        ExoplayerHandlerUtility.getInstance().getCurrentPosition();
        ExoplayerHandlerUtility.getInstance().goToBacground();
        ExoplayerHandlerUtility.getInstance().releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(destroyVideo) {
            ExoplayerHandlerUtility.getInstance().releasePlayer();
        }
    }
}
