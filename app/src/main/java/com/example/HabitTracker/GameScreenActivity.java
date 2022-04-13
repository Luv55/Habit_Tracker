package com.example.HabitTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class GameScreenActivity extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game_screen);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
            }
        });

        TextView highScoreTxt = findViewById(R.id.moneyGame);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);

        int money = Integer.parseInt(sharedPref.getString(getString(R.string.preferences_money),"money"));
        boolean win = sharedPref.getBoolean("preferences_moneyBoolean",false);
        int moneyObt;

        if (win){
            moneyObt = sharedPref.getInt("highscore", 0);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("preferences_moneyBoolean", false);
            editor.apply();
        }else{
            moneyObt = 0;
        }

        if (moneyObt != 0){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.preferences_money), money+moneyObt+"");
            editor.apply();
        }

        highScoreTxt.setText("Money: " + sharedPref.getString(getString(R.string.preferences_money),"money"));

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ActivityHome.class);
        startActivity(i);
    }
}