package brhymes.app;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private AnimatedVectorDrawable play;
    private AnimatedVectorDrawable pause;
    private boolean playtopause = true;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        play = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_play_pause);
        pause = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_pause_play);
        bottomAppBar = findViewById(R.id.bar);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //findViewById(R.id.record_duration).setVisibility(bottomAppBar.isFabAttached() ? View.VISIBLE : View.GONE);
                bottomAppBar.setFabAttached(!bottomAppBar.isFabAttached());
                animatePlayIcon();
            }
        });
    }

    public void animatePlayIcon() {
        AnimatedVectorDrawable drawable = playtopause ? play : pause;
        fab.setImageDrawable(drawable);
        drawable.start();
        playtopause = !playtopause;
    }
}
