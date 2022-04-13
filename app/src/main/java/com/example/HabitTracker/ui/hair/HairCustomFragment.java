package com.example.HabitTracker.ui.hair;

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


public class HairCustomFragment extends Fragment {

    private ImageView iv_hair1;
    private ImageView iv_hair2;
    private ImageView iv_hair3;
    private ImageView iv_hair4;
    private ImageView iv_hair5;
    private ImageView iv_hair6;
    private ImageView iv_hair7;
    private ImageView iv_hair8;

    HairCustomFragment.OnCustomHairReadListener imageReadListener;

    public HairCustomFragment(){

    }

    public interface OnCustomHairReadListener {

        public void onCustomHairRead(Drawable drawable, String string);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_hair_custom, container, false);

        iv_hair1 = root.findViewById(R.id.iv_hair1a);
        iv_hair2 = root.findViewById(R.id.iv_hair2a);
        iv_hair3 = root.findViewById(R.id.iv_hair3a);
        iv_hair4 = root.findViewById(R.id.iv_hair4a);
        iv_hair5 = root.findViewById(R.id.iv_hair5a);
        iv_hair6 = root.findViewById(R.id.iv_hair6a);
        iv_hair7 = root.findViewById(R.id.iv_hair7a);
        iv_hair8 = root.findViewById(R.id.iv_hair8a);

        iv_hair1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomHairRead(getResources().getDrawable(R.drawable.hair_black),"hair_black");
            }
        });

        iv_hair2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomHairRead(getResources().getDrawable(R.drawable.hair_brown),"hair_brown");
            }
        });

        iv_hair3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomHairRead(getResources().getDrawable(R.drawable.hair_blue),"hair_blue");
            }
        });
        iv_hair4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomHairRead(getResources().getDrawable(R.drawable.hair_yellow),"hair_yellow");
            }
        });

        iv_hair5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomHairRead(getResources().getDrawable(R.drawable.hair_empty),"hair_empty");
            }
        });

        iv_hair6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomHairRead(getResources().getDrawable(R.drawable.hair_pink),"hair_pink");
            }
        });

        iv_hair7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomHairRead(getResources().getDrawable(R.drawable.hair_red),"hair_red");
            }
        });

        iv_hair8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onCustomHairRead(getResources().getDrawable(R.drawable.hair_turquoise),"hair_turquoise");
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;


        try{
            imageReadListener = (HairCustomFragment.OnCustomHairReadListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+"must override message");
        }
    }
}