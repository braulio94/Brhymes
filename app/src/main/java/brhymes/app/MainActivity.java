package brhymes.app;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import java.lang.reflect.Field;
import java.util.ArrayList;

import brhymes.app.adapters.AuthorsImageStackAdapter;
import brhymes.app.cardstackview.HorizontalCardStackView;
import brhymes.app.model.Author;
import brhymes.app.player.MediaPlayerHolder;
import brhymes.app.player.PlaybackListener;
import brhymes.app.player.PlayerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static BottomAppBar bottomAppBar;
    static AnimatedVectorDrawable play;
    static AnimatedVectorDrawable pause;
    static boolean playtopause = true;
    static FloatingActionButton fab;
    private HorizontalCardStackView mAuthorsImageStack;
    private AuthorsImageStackAdapter mAuthorsAdapter;

    public static PlayerAdapter mPlayerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        play = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_play_pause);
        pause = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_pause_play);
        bottomAppBar = findViewById(R.id.bar);

        initializePlaybackController();
        createStackAuthorImages();
    }

    private void createStackAuthorImages(){
        ArrayList<Author> authors = new ArrayList<>();
        for (int items = 0; items < 6; items++) {
            Author author = new Author("Braulio " + items, getDrawable(getResId("image000" + items, R.drawable.class)));
            authors.add(author);
        }
        mAuthorsImageStack = findViewById(R.id.stackview_main);
        mAuthorsAdapter = new AuthorsImageStackAdapter(this);
        mAuthorsImageStack.setAdapter(mAuthorsAdapter);
        mAuthorsAdapter.updateData(authors);

    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                animatePlayIcon();
                if (mPlayerAdapter.isPlaying()) {
                    mPlayerAdapter.pause();
                } else {
                    mPlayerAdapter.play();
                }
                break;
        }
    }

    public static void animatePlayIcon() {
        bottomAppBar.setFabAttached(!bottomAppBar.isFabAttached());
        AnimatedVectorDrawable drawable = playtopause ? play : pause;
        fab.setImageDrawable(drawable);
        drawable.start();
        playtopause = !playtopause;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPlayerAdapter.loadMedia(R.raw.balance);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isChangingConfigurations() && !mPlayerAdapter.isPlaying()) {
            mPlayerAdapter.release();
        }
    }

    private void initializePlaybackController() {
        MediaPlayerHolder mMediaPlayerHolder = new MediaPlayerHolder(this);
        mMediaPlayerHolder.setPlaybackInfoListener(new PlaybackListener(this));
        mPlayerAdapter = mMediaPlayerHolder;
    }
}
