package com.example.HabitTracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, moneyObt = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Asteroid[] asteroids;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<Bullet> bullets;
    private int sound;
    private Airship airship;
    private GameActivity activity;
    private Background background1, background2;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("preferencesHabitTracker", Context.MODE_PRIVATE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.shoot, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        airship = new Airship(this, screenY, getResources());

        bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        asteroids = new Asteroid[4];

        for (int i = 0;i < 4;i++) {

            Asteroid asteroid = new Asteroid(getResources());
            asteroids[i] = asteroid;

        }

        random = new Random();

    }

    @Override
    public void run() {

        while (isPlaying) {

            update ();
            draw ();
            sleep ();

        }

    }

    private void update () {

        background1.x += 1 * screenRatioX;
        background2.x += 1 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (airship.isGoingUp)
            airship.y -= 30 * screenRatioY;
        else
            airship.y += 30 * screenRatioY;

        if (airship.y < 0)
            airship.y = 0;

        if (airship.y >= screenY - airship.height)
            airship.y = screenY - airship.height;

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {

            if (bullet.x > screenX)
                trash.add(bullet);

            bullet.x += 50 * screenRatioX;

            for (Asteroid asteroid : asteroids) {

                if (Rect.intersects(asteroid.getCollisionShape(),
                        bullet.getCollisionShape())) {

                    moneyObt++;
                    asteroid.x = -500;
                    bullet.x = screenX + 500;
                    asteroid.wasShot = true;

                }

            }

        }

        for (Bullet bullet : trash)
            bullets.remove(bullet);

        for (Asteroid asteroid : asteroids) {

            asteroid.x -= asteroid.speed;

            if (asteroid.x + asteroid.width < 0) {


                int bound = (int) (50 * screenRatioX);
                asteroid.speed = random.nextInt(bound);

                if (asteroid.speed < 5 * screenRatioX){
                    asteroid.speed = (int) (50 * screenRatioX);
                    if (asteroid.speed < 5 * screenRatioX){
                        asteroid.speed = (int) (50 * screenRatioX);
                    }
                }

                asteroid.x = screenX;
                asteroid.y = random.nextInt(screenY - asteroid.height);

                asteroid.wasShot = false;
            }

            if (Rect.intersects(asteroid.getCollisionShape(), airship.getCollisionShape())) {

                isGameOver = true;
                return;
            }

        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (Asteroid asteroid : asteroids)
                canvas.drawBitmap(asteroid.getAsteroid(), asteroid.x, asteroid.y, paint);

            canvas.drawText(moneyObt + "", screenX / 2f, 164, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(airship.getDead(), airship.x, airship.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveMoney();
                waitBeforeExiting ();
                return;
            }

            canvas.drawBitmap(airship.getFlight(), airship.x, airship.y, paint);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
                canvas.drawBitmap(airship.getFlight(), airship.x, airship.y, paint);


            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, GameScreenActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveMoney() {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("highscore", moneyObt);
        editor.putBoolean("preferences_moneyBoolean", true);
        editor.apply();

    }

    private void sleep () {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    airship.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                airship.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    airship.shoots++;
                break;
        }

        return true;
    }

    public void newBullet() {

        soundPool.play(sound, 1, 1, 0, 0, 1);

        Bullet bullet = new Bullet(getResources());
        bullet.x = airship.x + airship.width;
        bullet.y = airship.y + (airship.height / 2);
        bullets.add(bullet);

    }
}