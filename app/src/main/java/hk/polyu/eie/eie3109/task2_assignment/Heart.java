package hk.polyu.eie.eie3109.task2_assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Heart {
    Bitmap heart;
    Context context;
    int hx, hy;
    int hSpeed;

    public Heart(Context context, int hx, int hy) {
        this.context = context;
        heart = BitmapFactory.decodeResource(context.getResources(), R.drawable.life1);
        this.hx = hx;
        this.hy = hy;
        hSpeed =15 + (int) (Math.random() * 10);
    }
    public Bitmap getHeart(){
        return heart;
    }
    public int getWidth() {
        return heart.getWidth();
    }
    public int getHeight() {
        return heart.getHeight();
    }
}
