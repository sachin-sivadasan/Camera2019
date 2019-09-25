package com.schn.camera2019.ui.player.job;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.ui.PlayerView;

public interface VideoPlayJob {
    void init(PlayerView playerView, Listener listener);

    @NonNull
    VideoPlayJob playResource(@NonNull Bundle bundle);

    void reset();

    void release();
    interface Listener {
        void onBuffer();

        void onPlaying();

        void onError();
    }
}