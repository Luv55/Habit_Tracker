package com.example.HabitTracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.HabitTracker.GameView.screenRatioY;
import static com.example.HabitTracker.GameView.screenRatioX;

public class Asteroid {

    public int speed = 20;
    public boolean wasShot = true;
    int x = 0, y, width, height, asteroidCounter = 1;
    Bitmap asteroid1, asteroid2, asteroid3, asteroid4;

    Asteroid (Resources res) {

        asteroid1 = BitmapFactory.decodeResource(res, R.drawable.asteroide);
        asteroid2 = BitmapFactory.decodeResource(res, R.drawable.asteroide);
        asteroid3 = BitmapFactory.decodeResource(res, R.drawable.asteroide);
        asteroid4 = BitmapFactory.decodeResource(res, R.drawable.asteroide);

        width = asteroid1.getWidth();
        height = asteroid1.getHeight();



        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        asteroid1 = Bitmap.createScaledBitmap(asteroid1, width, height, false);
        asteroid2 = Bitmap.createScaledBitmap(asteroid1, width, height, false);
        asteroid3 = Bitmap.createScaledBitmap(asteroid1, width, height, false);
        asteroid4 = Bitmap.createScaledBitmap(asteroid1, width, height, false);

        y = -height;
    }

    Bitmap getAsteroid() {

        if (asteroidCounter == 1) {
            asteroidCounter++;
            return asteroid1;
        }

        if (asteroidCounter == 2) {
            asteroidCounter++;
            return asteroid2;
        }

        if (asteroidCounter == 3) {
            asteroidCounter++;
            return asteroid3;
        }

        asteroidCounter = 1;

        return asteroid4;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}
