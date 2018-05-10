package com.example.soundoffear.bakingapp.Utilities;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by soundoffear on 06/04/2018.
 */

public class ExoplayerHandlerUtility {

    private static ExoplayerHandlerUtility instance;

    public static ExoplayerHandlerUtility getInstance() {
        if(instance == null) {
            instance = new ExoplayerHandlerUtility();
        }
        return instance;
    }

    private SimpleExoPlayer player;
    private Uri playerUri;
    private boolean isPlayerPlaying;

    public ExoplayerHandlerUtility() {}

    public void uriExoplayer(Context context, Uri uri, SimpleExoPlayerView simpleExoPlayerView) {

        if(context != null && uri != null && simpleExoPlayerView != null) {
            if(!uri.equals(playerUri) || player == null) {
                playerUri = uri;

                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
                TrackSelector trackselector = new DefaultTrackSelector(trackSelectionFactory);

                player = ExoPlayerFactory.newSimpleInstance(context, trackselector);
                simpleExoPlayerView.setPlayer(player);

                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                        Util.getUserAgent(context, context.getPackageName()),
                        (TransferListener<? super DataSource>) bandwidthMeter);

                MediaSource mediaSource = new ExtractorMediaSource(playerUri,
                        dataSourceFactory,
                        new DefaultExtractorsFactory(),
                        null,
                        null);

                player.prepare(mediaSource);
            }

            player.clearVideoSurface();
            player.setVideoSurfaceView((SurfaceView) simpleExoPlayerView.getVideoSurfaceView());
            player.seekTo(player.getCurrentPosition() + 1);
            simpleExoPlayerView.setPlayer(player);
        }

    }

    public void releasePlayer() {
        if(player != null) {
            player.release();
        }
        player = null;
    }

    public void goToBacground() {
        if(player != null) {
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    public void goToForeground() {
        if(player != null) {
            player.setPlayWhenReady(true);
        }
    }
}
