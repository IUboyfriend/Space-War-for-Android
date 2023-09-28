package hk.polyu.eie.eie3109.task2_assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class EnemyBoss {
    Context context;
    Bitmap enemy;
    int ex, ey;
    int speedX;
    Random random;

    public EnemyBoss(Context context) {
        this.context = context;
        enemy = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy1);
        random = new Random();
        ex = 200 + random.nextInt(400);
        ey = 0;
        speedX = 15;
    }

    public Bitmap getBoss(){
        return enemy;
    }

    int getWidth(){
        return enemy.getWidth();
    }

    int getHeight(){
        return enemy.getHeight();
    }
}
