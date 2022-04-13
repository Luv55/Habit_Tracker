package com.example.HabitTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityStartUp extends AppCompatActivity {

    private Button bt_login;
    private Button bt_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        bt_login = findViewById(R.id.bt_login);
        bt_sign = findViewById(R.id.bt_sign);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);
        boolean isLogin = sharedPref.getBoolean(getString(R.string.preferences_islogin),false);

        if(isLogin) {
            Intent i = new Intent(getApplicationContext(), ActivityHome.class);
            startActivity(i);
        }

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bt_login.setBackground(getDrawable(R.drawable.rounded_buttom_pressed));

                Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
                startActivity(i);
            }
        });

        bt_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bt_sign.setBackground(getDrawable(R.drawable.rounded_buttom_pressed));

                Intent i = new Intent(getApplicationContext(), ActivitySignUp.class);
                startActivity(i);
            }
        });

    }
}