package brhymes.app.player;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.widget.SeekBar;

import brhymes.app.MainActivity;
import brhymes.app.R;

import static brhymes.app.player.PlaybackInfoListener.State.*;

public class PlaybackListener extends PlaybackInfoListener implements SeekBar.OnSeekBarChangeListener {

    int userSelectedPosition = 0;
    private SeekBar mSeekbarAudio;
    private boolean mUserIsSeeking = false;

    public PlaybackListener(Activity act) {
        mSeekbarAudio = act.findViewById(R.id.seekBar);
        mSeekbarAudio.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onDurationChanged(int duration) {
        mSeekbarAudio.setMax(duration);
    }

    @Override
    public void onPositionChanged(int position) {
        if (!mUserIsSeeking) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mSeekbarAudio.setProgress(position, true);
            } else {
                mSeekbarAudio.setProgress(position);
            }
        }
    }

    @Override
    public void onStateChanged(@State int state) {
        String stateToString = PlaybackInfoListener.convertStateToString(state);
        Log.d("PlaybackListener", "Playback State: " + stateToString);
        if (state == COMPLETED || state == RESET){
            MainActivity.animatePlayIcon();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            userSelectedPosition = progress;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mUserIsSeeking = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mUserIsSeeking = false;
        MainActivity.mPlayerAdapter.seekTo(userSelectedPosition);
    }
}