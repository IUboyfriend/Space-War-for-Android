package hk.polyu.eie.eie3109.task2_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//the start interface
public class Begin extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);
    }

    public void game_begins(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
