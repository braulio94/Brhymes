package brhymes.app.player;

public interface PlayerAdapter {

    void loadMedia(int resourceId);
    void release();
    boolean isPlaying();
    void play();
    void reset();
    void pause();
    void initializedProgressCallback();
    void seekTo(int position);

}
