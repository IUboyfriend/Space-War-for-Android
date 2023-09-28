package hk.polyu.eie.eie3109.task2_assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

//the main game activity
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SpaceWar(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        BackgroundMusic.stop(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BackgroundMusic.play(this, R.raw.backgroundmusic);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}