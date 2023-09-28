package hk.polyu.eie.eie3109.task2_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//the game over interface
public class GameOver extends AppCompatActivity {

    TextView tvScores;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end);
        int points = getIntent().getExtras().getInt("scores");
        tvScores = findViewById(R.id.tvScores);
        tvScores.setText("" + points);
    }

    public void restart(View view) {
        startActivity(new Intent(GameOver.this, Begin.class));
        finish();
    }

    public void exit(View view) {
        finish();
    }
}
