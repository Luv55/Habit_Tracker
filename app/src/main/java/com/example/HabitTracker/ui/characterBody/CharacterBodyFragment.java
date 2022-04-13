package com.example.HabitTracker.ui.characterBody;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.HabitTracker.R;

public class CharacterBodyFragment extends Fragment {

    private ImageView iv_body1;
    private ImageView iv_body2;
    private ImageView iv_body3;
    private ImageView iv_body4;

    OnCharacterBodyReadListener imageReadListener;

    public CharacterBodyFragment(){

    }

    public interface OnCharacterBodyReadListener {
        public void onBodyRead(Drawable drawable, String string);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_character_body, container, false);

        iv_body1 = root.findViewById(R.id.iv_body1);
        iv_body2 = root.findViewById(R.id.iv_body2);
        iv_body3 = root.findViewById(R.id.iv_body3);
        iv_body4 = root.findViewById(R.id.iv_body4);

        iv_body1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onBodyRead(getResources().getDrawable(R.drawable.body_yellow),"body_yellow");
            }
        });

        iv_body2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onBodyRead(getResources().getDrawable(R.drawable.body_blue),"body_blue");
            }
        });

        iv_body3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onBodyRead(getResources().getDrawable(R.drawable.body_red),"body_red");
            }
        });

        iv_body4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onBodyRead(getResources().getDrawable(R.drawable.body_green),"body_green");
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            imageReadListener = (OnCharacterBodyReadListener) activity;
        }catch (ClassCastException e){
            throw  new ClassCastException(activity.toString()+"must override message");
        }
    }
}