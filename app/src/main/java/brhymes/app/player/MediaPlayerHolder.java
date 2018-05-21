package brhymes.app.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class MediaPlayerHolder implements PlayerAdapter {

    public static final int PLAYBACK_POSITION_REFRESH_IINTERNAL_MS = 1000;
    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private int mResourceId;
    private PlaybackInfoListener mPlaybackInfoListener;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;

    public MediaPlayerHolder(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private void initializeMediaPlayer(){
        if (mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopUpdatingCallbackWithPosition(true);
                    if (mPlaybackInfoListener != null){
                        mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.COMPLETED);
                        mPlaybackInfoListener.onPlaybackCompleted();
                    }
                }
            });
        }
    }

    public void setPlaybackInfoListener(PlaybackInfoListener listener){
        mPlaybackInfoListener = listener;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void loadMedia(int resourceId) {
        mResourceId = resourceId;
        initializeMediaPlayer();
        AssetFileDescriptor assetFileDescriptor = mContext.getResources().openRawResourceFd(mResourceId);
        try {
            mMediaPlayer.setDataSource(assetFileDescriptor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializedProgressCallback();
    }

    @Override
    public void release() {
        if (mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    @Override
    public void play() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
            if (mPlaybackInfoListener != null){
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PLAYING);
            }
            startUpdatingCallbackWithPosition();
        }
    }

    @Override
    public void reset() {
        if (mMediaPlayer != null){
            mMediaPlayer.reset();
            loadMedia(mResourceId);
            if (mPlaybackInfoListener != null){
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.RESET);
            }
            stopUpdatingCallbackWithPosition(true);
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            if (mPlaybackInfoListener != null){
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PAUSED);
            }
        }
    }

    @Override
    public void initializedProgressCallback() {
        final int duration = mMediaPlayer.getDuration();
        if (mPlaybackInfoListener != null){
            mPlaybackInfoListener.onDurationChanged(duration);
            mPlaybackInfoListener.onPositionChanged(0);
        }
    }

    @Override
    public void seekTo(int position) {
        if (mMediaPlayer != null){
            mMediaPlayer.seekTo(position);
        }
    }

    private void startUpdatingCallbackWithPosition(){
        if (mExecutor == null){
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mSeekbarPositionUpdateTask == null){
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    updateProgressCallback();
                }
            };
        }
        mExecutor.scheduleAtFixedRate(
                mSeekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_IINTERNAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition){
        if (mExecutor != null){
            mExecutor.shutdown();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
            if (resetUIPlaybackPosition && mPlaybackInfoListener != null){
                mPlaybackInfoListener.onPositionChanged(0);
            }
        }
    }

    private void updateProgressCallback(){
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()){
            int currentPosition = mMediaPlayer.getCurrentPosition();
            if (mPlaybackInfoListener != null){
                mPlaybackInfoListener.onPositionChanged(currentPosition);
            }
        }
    }
}
