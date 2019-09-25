package com.schn.camera2019.ui.player.job;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.schn.camera2019.R;

public final class Mp4VideoPlayJob implements VideoPlayJob, PlaybackPreparer {

    private final Context context;
    private final DefaultDataSourceFactory dataSourceFactory;
    private SimpleExoPlayer player;
    private long contentPosition;
    private MediaSource mediaSource;
    private int startWindow;
    private long startPosition;
    private Listener listener;

    public Mp4VideoPlayJob(Context context) {
        this.context = context;
        // Create a player instance.
        player = ExoPlayerFactory.newSimpleInstance(context);
        player.setPlayWhenReady(true);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
                    if (getListener() != null) {
                        getListener().onBuffer();
                    }
                }else{
                    if (getListener() != null) {
                        getListener().onPlaying();
                    }
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                if (getListener() != null) {
                    getListener().onError();
                }
                clearStartPosition();
                boolean haveStartPosition = startWindow != C.INDEX_UNSET;
                if (haveStartPosition) {
                    player.seekTo(startWindow, startPosition);
                }
                player.prepare(mediaSource, !haveStartPosition, false);
            }
        });
        player.setRepeatMode(Player.REPEAT_MODE_OFF);
        String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
        dataSourceFactory = new DefaultDataSourceFactory(context, userAgent);
    }

    @Override
    public void init(PlayerView playerView, Listener listener) {
        setListener(listener);
        playerView.setPlayer(player);
        playerView.setUseController(true);
        playerView.setPlaybackPreparer(this);
    }

    @NonNull
    @Override
    public VideoPlayJob playResource(@NonNull Bundle args) {
        String contentUrl = args.getString("FILE_PATH_KEY");
        mediaSource = buildMediaSource(Uri.parse(contentUrl));
        player.seekTo(contentPosition);
        player.prepare(mediaSource);
        return this;
    }

    private void clearStartPosition() {
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void reset() {
        if (player != null) {
            contentPosition = player.getContentPosition();
            player.release();
            player = null;
            listener = null;
        }
    }

    @Override
    public void release() {
        if (player != null) {
            player.release();
            player = null;
            listener = null;
        }
    }

    // Internal methods.
    private MediaSource buildMediaSource(Uri uri) {
        @C.ContentType int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }

    @Override
    public void preparePlayback() {
        player.retry();
    }
}