package hk.polyu.eie.eie3109.task2_assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;

public class Player {
    Context context;
    Bitmap player;
    int px;
    int py;

    public Player(Context context) {
        this.context = context;
        player = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane);
        px = new Random().nextInt(SpaceWar.screenWidth - getPlayerWidth());
        py = SpaceWar.screenHeight - getPlayerHeight();
    }

    public Bitmap getPlayer() {
        return player;
    }

    int getPlayerWidth() {
        return player.getWidth();
    }

    int getPlayerHeight() {
        return player.getHeight();
    }
}
