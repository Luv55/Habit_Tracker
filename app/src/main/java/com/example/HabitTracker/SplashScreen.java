package com.example.HabitTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    ProgressBar pb_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        pb_1 = findViewById(R.id.progressBar3);
        ObjectAnimator.ofInt(pb_1, "progress", 100).setDuration(5000).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
                startActivity(intent);


            }
        }, 5000);


    }
}