package hk.polyu.eie.eie3109.task2_assignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class SpaceWar extends View {
    private Context context;
    private Handler myHandler = new Handler();
    static int screenWidth, screenHeight;
    private int scores = 0;
    private int life = 3;
    private Paint paintScore;
    private boolean paused = false;
    private Player player;
    private EnemyBoss enemyBoss;
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();
    private ArrayList<Bullet> playerBullets = new ArrayList<>();
    private ArrayList<SmallEnemy> smallEnemies = new ArrayList<>();
    private ArrayList<Heart> hearts = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private Bitmap lifeImg;
    private Bitmap backgroundImg;

    private boolean canGeneratePlayerBullets = true;
    private boolean canGenerateBossBullets = true;
    private boolean canGenerateSmallEnemy = true;
    private boolean canGenerateHeart = true;
    private boolean timeToGenerateHeart = false;

    private int smallEnemyInitialSpeed = 8;

    public SpaceWar(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point screen = new Point();
        display.getSize(screen);
        screenWidth = screen.x;
        screenHeight = screen.y;
        player = new Player(context);
        enemyBoss = new EnemyBoss(context);
        backgroundImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.gamebackground);
        lifeImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.life1);
        paintScore = new Paint();
        paintScore.setColor(Color.rgb(255,255,255));
        paintScore.setTextSize(100);
        paintScore.setTextAlign(Paint.Align.LEFT);
    }

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {

        //if the player dies
        if(life ==0){
            myHandler = null;
            paused = true;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("scores", scores);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

        // display background, scores and lives,must draw background first
        canvas.drawBitmap(backgroundImg, 0, 0, null);
        canvas.drawText("Score: " + scores, 0, 100, paintScore);
        if(life>=3)
            life =3;
        int current = life;
        while(current>=1){
            canvas.drawBitmap(lifeImg, screenWidth - lifeImg.getWidth() * current, 0, null);
            current--;
        }

        // Move the boss of the enemies
        enemyBoss.ex += enemyBoss.speedX;
        // Check collisions of the boss and the screen, speed up gradually with a limit
        if((enemyBoss.ex + enemyBoss.getWidth() >= screenWidth||enemyBoss.ex<=0)){
            if(Math.abs(enemyBoss.speedX)<=30)
                enemyBoss.speedX *= -1.1;
            else
                enemyBoss.speedX = -enemyBoss.speedX;
        }

        // Draw the boss and the player
        canvas.drawBitmap(enemyBoss.getBoss(), enemyBoss.ex, enemyBoss.ey, null);
        // Draw the player, if the position is out of the screen, limit it inside the screen
        if(player.px > screenWidth - player.getPlayerWidth()){
            player.px = screenWidth - player.getPlayerWidth();
        }
        if(player.px < 0){
            player.px = 0;
        }
        canvas.drawBitmap(player.getPlayer(), player.px, player.py, null);


       //let the heart boss bullet, player bullet and small enemy appear on the screen at a certain time
        if(canGenerateHeart){
            if(timeToGenerateHeart == false){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timeToGenerateHeart =true;
                    }
                }, 10000);
            }else{
                Heart heart = new Heart(context, 60 + (int)((screenWidth-120)*Math.random()), 40 );
                hearts.add(heart);
                canGenerateHeart = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        canGenerateHeart =true;
                    }
                }, 10000);
            }

        }

        if(canGenerateBossBullets){
            Bullet enemyBullet = new Bullet(context, enemyBoss.ex + enemyBoss.getWidth() / 2, enemyBoss.ey +enemyBoss.getBoss().getHeight() );
            enemyBullets.add(enemyBullet);
            canGenerateBossBullets = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    canGenerateBossBullets =true;
                }
            }, 1000);
        }

        if(canGeneratePlayerBullets){
            Bullet playerBullet = new Bullet(context, player.px + player.getPlayerWidth()/ 2, player.py +player.getPlayerHeight()/2 );
            playerBullets.add(playerBullet);
            canGeneratePlayerBullets = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    canGeneratePlayerBullets =true;
                }
            }, 1200);
        }

        if(canGenerateSmallEnemy==true){
            SmallEnemy smallEnemy = new SmallEnemy(context, 60 + (int)((screenWidth-120)*Math.random()),40 ,smallEnemyInitialSpeed);
            smallEnemies.add(smallEnemy);
            if(smallEnemyInitialSpeed<=18)
                smallEnemyInitialSpeed +=2;
            canGenerateSmallEnemy =false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    canGenerateSmallEnemy =true;
                }
            }, 5000);

        }


        //draw the heart
        for(int i=0;i<hearts.size();i++){
            Heart thisHeart = hearts.get(i);
            thisHeart.hy +=  thisHeart.hSpeed;
            canvas.drawBitmap(thisHeart.getHeart(), thisHeart.hx, thisHeart.hy, null);
            if((thisHeart.hx >= player.px) && thisHeart.hx <= player.px + player.getPlayerWidth()
                    && thisHeart.hy >= player.py){
                life++;
                hearts.remove(i);
            }else if(thisHeart.hy >= screenHeight){
                hearts.remove(i);
            }

        }


        //draw the small enemies
        for(int i=0;i<smallEnemies.size();i++){
            SmallEnemy thisSmallEnemy = smallEnemies.get(i);
            thisSmallEnemy.sy +=  thisSmallEnemy.sSpeed;
            canvas.drawBitmap(thisSmallEnemy.getSmallEnemy(), thisSmallEnemy.sx, thisSmallEnemy.sy, null);
            if((thisSmallEnemy.sx >= player.px) && thisSmallEnemy.sx <= player.px + player.getPlayerWidth()
                    && thisSmallEnemy.sy >= player.py){
                life--;
                smallEnemies.remove(i);
                explosions.add(new Explosion(context, player.px, player.py));
            }else if(thisSmallEnemy.sy >= screenHeight){
                smallEnemies.remove(i);
            }

        }

        //Draw the player's bullet
        for(int i = 0; i < playerBullets.size(); i++){
            playerBullets.get(i).by -= 20;
            canvas.drawBitmap(playerBullets.get(i).getBullet(), playerBullets.get(i).bx, playerBullets.get(i).by, null);
            //shoot the boss
            boolean disappear = false;
            if((playerBullets.get(i).bx >= enemyBoss.ex) && playerBullets.get(i).bx <= enemyBoss.ex + enemyBoss.getWidth()
                    && playerBullets.get(i).by <= enemyBoss.getHeight() ){
                scores+= 2;
                playerBullets.remove(i);
                disappear = true;
                explosions.add(new Explosion(context, enemyBoss.ex, enemyBoss.ey));
            }else if(playerBullets.get(i).by <=0){
                playerBullets.remove(i);
                disappear = true;
            }
            if(disappear == false){

                //kill a small enemy
                for(int j=0;j<smallEnemies.size();j++){
                    SmallEnemy thisEnemy = smallEnemies.get(j);
                    if((playerBullets.get(i).bx >= thisEnemy.sx) && playerBullets.get(i).bx <= thisEnemy.sx + thisEnemy.getWidth()
                            && playerBullets.get(i).by <= thisEnemy.getHeight()+thisEnemy.sy ){
                        scores+= 1;
                        playerBullets.remove(i);
                        explosions.add(new Explosion(context, thisEnemy.sx, thisEnemy.sy));
                        smallEnemies.remove(j);
                    }
                }


            }





        }

        //Draw the boss's bullet,check the collision and change the life
        for(int i = 0; i < enemyBullets.size(); i++){
            enemyBullets.get(i).by += enemyBullets.get(i).bSpeed;
            canvas.drawBitmap(enemyBullets.get(i).getBullet(), enemyBullets.get(i).bx, enemyBullets.get(i).by, null);
            //check if the player is shot by the enemy
            if((enemyBullets.get(i).bx >= player.px) && enemyBullets.get(i).bx <= player.px + player.getPlayerWidth()
                    && enemyBullets.get(i).by >= player.py){
                life--;
                enemyBullets.remove(i);
                explosions.add(new Explosion(context, player.px, player.py));
            }else if(enemyBullets.get(i).by >= screenHeight){
                enemyBullets.remove(i);
            }
        }

        //show the collision effect
        for(int i=0; i < explosions.size(); i++){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).eX, explosions.get(i).eY, null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame > 5){
                explosions.remove(i);
            }
        }
        if(!paused)
            myHandler.postDelayed(runnable, 30);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        // let the plane move along to the finger
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            player.px = touchX;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            player.px = touchX;
        }
        return true;
    }
}
