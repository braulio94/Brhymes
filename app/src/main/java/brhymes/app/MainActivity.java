package brhymes.app;

import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomAppBar = findViewById(R.id.bar);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomAppBar.isFabAttached()){
                    bottomAppBar.setFabAttached(false);
                } else {
                    bottomAppBar.setFabAttached(true);
                }
            }
        });

    }
}
