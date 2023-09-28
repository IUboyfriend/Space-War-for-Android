package hk.polyu.eie.eie3109.task2_assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SmallEnemy {
    Bitmap smallEnemy;
    Context context;
    int sx, sy;
    int sSpeed;

    public SmallEnemy(Context context, int bx, int by,int smallEnemyInitialSpeed) {
        this.context = context;
        smallEnemy = BitmapFactory.decodeResource(context.getResources(), R.drawable.smallenemy);
        this.sx = bx;
        this.sy = by;
        sSpeed = smallEnemyInitialSpeed + (int) (Math.random() * 10);
    }
    public Bitmap getSmallEnemy(){
        return smallEnemy;
    }
    public int getWidth() {
        return smallEnemy.getWidth();
    }
    public int getHeight() {
        return smallEnemy.getHeight();
    }
}
