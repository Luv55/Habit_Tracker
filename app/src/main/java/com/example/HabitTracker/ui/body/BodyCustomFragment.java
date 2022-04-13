package com.example.HabitTracker.ui.body;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.HabitTracker.R;


public class BodyCustomFragment extends Fragment {

    private ImageView iv_body1;
    private ImageView iv_body2;
    private ImageView iv_body3;
    private ImageView iv_body4;
    private ImageView iv_body5;
    private ImageView iv_body6;
    private ImageView iv_body7;
    private ImageView iv_body8;
    private ImageView iv_body9;
    private ImageView iv_body10;
    private ImageView iv_body11;
    private ImageView iv_body12;
    private ImageView iv_body13;
    private ImageView iv_body14;
    private ImageView iv_body15;
    private ImageView iv_body16;
    private ImageView iv_body17;
    private ImageView iv_body18;
    private ImageView iv_body19;
    private ImageView iv_body20;
    private ImageView iv_body21;
    private ImageView iv_body22;
    private ImageView iv_body23;

    BodyCustomFragment.OnCustomBodyReadListener imageReadListener;

    public BodyCustomFragment(){

    }

    public interface OnCustomBodyReadListener {
        public void onCustomBodyRead(Drawable drawable, String string, int level, int price);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_body_custom, container, false);

        iv_body1 = root.findViewById(R.id.iv_body1a);
        iv_body2 = root.findViewById(R.id.iv_body2a);
        iv_body3 = root.findViewById(R.id.iv_body3a);
        iv_body4 = root.findViewById(R.id.iv_body4a);
        iv_body5 = root.findViewById(R.id.iv_body5a);
        iv_body6 = root.findViewById(R.id.iv_body6a);
        iv_body7 = root.findViewById(R.id.iv_body7a);
        iv_body8 = root.findViewById(R.id.iv_body8a);
        iv_body9 = root.findViewById(R.id.iv_body9a);
        iv_body10 = root.findViewById(R.id.iv_body10a);
        iv_body11 = root.findViewById(R.id.iv_body11a);
        iv_body12 = root.findViewById(R.id.iv_body12a);
        iv_body13 = root.findViewById(R.id.iv_body13a);
        iv_body14 = root.findViewById(R.id.iv_body14a);
        iv_body15 = root.findViewById(R.id.iv_body15a);
        iv_body16 = root.findViewById(R.id.iv_body16a);
        iv_body17 = root.findViewById(R.id.iv_body17a);
        iv_body18 = root.findViewById(R.id.iv_body18a);
        iv_body19 = root.findViewById(R.id.iv_body19a);
        iv_body20 = root.findViewById(R.id.iv_body20a);
        iv_body21 = root.findViewById(R.id.iv_body21a);
        iv_body22 = root.findViewById(R.id.iv_body22a);
        iv_body23 = root.findViewById(R.id.iv_body23a);

        iv_body1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_yellow),"body_yellow", 0, 0);
            }
        });

        iv_body2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_blue),"body_blue", 0, 0);
            }
        });

        iv_body3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_red),"body_red", 0, 0);
            }
        });

        iv_body4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_green),"body_green", 0, 0);
            }
        });

        //BUY

        iv_body5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_purple),"body_purple", 2, 5);
            }
        });

        iv_body6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_black),"body_black", 2, 5);
            }
        });

        iv_body7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_yellow_beige),"body_yellow_beige", 2, 5);
            }
        });

        iv_body8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_blue_biege),"body_blue_biege", 2, 5);
            }
        });
        iv_body9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_green_beige),"body_green_beige", 2, 5);
            }
        });

        iv_body10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_black_beige),"body_black_beige", 2, 5);
            }
        });

        iv_body11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_black_blue),"body_black_blue", 4, 20);
            }
        });

        iv_body12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_white_blue),"body_white_blue", 4, 20);
            }
        });
        iv_body13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_red_black),"body_red_black", 6, 35);
            }
        });

        iv_body14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_turquoise_black),"body_turquoise_black", 6, 35);
            }
        });

        iv_body15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_black_camo),"body_black_camo", 10, 50);
            }
        });

        iv_body16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_green_grey),"body_green_grey", 15, 80);
            }
        });
        iv_body17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_liliac_grey),"body_liliac_grey", 15, 80);
            }
        });

        iv_body18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_red_grey),"body_red_grey", 15, 80);
            }
        });

        iv_body19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_turquoise_grey),"body_turquoise_grey", 20, 100);
            }
        });

        iv_body20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_beige_grey),"body_beige_grey", 20, 100);
            }
        });
        iv_body21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_samurai_yellow),"body_samurai_yellow", 30, 150);
            }
        });

        iv_body22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_samurai_purple),"body_samurai_purple", 30, 150);
            }
        });

        iv_body23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomBodyRead(getResources().getDrawable(R.drawable.body_samurai_turquoise),"body_samurai_turquoise", 30, 150);
            }
        });


        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            imageReadListener = (BodyCustomFragment.OnCustomBodyReadListener) activity;
        }catch (ClassCastException e){
            throw  new ClassCastException(activity.toString()+"must override message");
        }
    }
}