package com.example.HabitTracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.HabitTracker.GameView.screenRatioX;
import static com.example.HabitTracker.GameView.screenRatioY;

public class Airship {

    int shoots = 0;
    boolean isGoingUp = false;
    int x, y, width, height, wingCounter = 0, shootCounter = 1;
    Bitmap airship1, airship2, bullet1, bullet2, bullet3, bullet4, bullet5, dead;
    private GameView gameView;

    Airship (GameView gameView, int screenY, Resources res) {

        this.gameView = gameView;

        airship1 = BitmapFactory.decodeResource(res, R.drawable.nave);
        airship2 = BitmapFactory.decodeResource(res, R.drawable.nave);

        width = airship1.getWidth();
        height = airship1.getHeight();


        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        airship1 = Bitmap.createScaledBitmap(airship1, width, height, false);
        airship2 = Bitmap.createScaledBitmap(airship2, width, height, false);

        bullet1 = BitmapFactory.decodeResource(res, R.drawable.nave);
        bullet2 = BitmapFactory.decodeResource(res, R.drawable.nave);
        bullet3 = BitmapFactory.decodeResource(res, R.drawable.nave);
        bullet4 = BitmapFactory.decodeResource(res, R.drawable.nave);
        bullet5 = BitmapFactory.decodeResource(res, R.drawable.nave);

        bullet1 = Bitmap.createScaledBitmap(bullet1, width, height, false);
        bullet2 = Bitmap.createScaledBitmap(bullet2, width, height, false);
        bullet3 = Bitmap.createScaledBitmap(bullet3, width, height, false);
        bullet4 = Bitmap.createScaledBitmap(bullet4, width, height, false);
        bullet5 = Bitmap.createScaledBitmap(bullet5, width, height, false);

        dead = BitmapFactory.decodeResource(res, R.drawable.naveestrellada);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);

    }

    Bitmap getFlight () {

        if (shoots != 0) {

            if (shootCounter == 1) {
                shootCounter++;
                return bullet1;
            }

            if (shootCounter == 2) {
                shootCounter++;
                return bullet2;
            }

            if (shootCounter == 3) {
                shootCounter++;
                return bullet3;
            }

            if (shootCounter == 4) {
                shootCounter++;
                return bullet4;
            }

            shootCounter = 1;
            shoots--;
            gameView.newBullet();

            return bullet5;
        }

        if (wingCounter == 0) {
            wingCounter++;
            return airship1;
        }
        wingCounter--;

        return airship2;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead () {
        return dead;
    }

}
