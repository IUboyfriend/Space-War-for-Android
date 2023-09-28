package hk.polyu.eie.eie3109.task2_assignment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Bullet {
    Bitmap bullet;
    Context context;
    int bx, by;
    int bSpeed;

    public Bullet(Context context, int bx, int by) {
        this.context = context;
        bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.shot);
        this.bx = bx;
        this.by = by;
        bSpeed = 15 + (int) (Math.random() * 10);
    }
    public Bitmap getBullet(){
        return bullet;
    }
    public int getShotWidth() {
        return bullet.getWidth();
    }
    public int getShotHeight() {
        return bullet.getHeight();
    }
}
