package com.example.HabitTracker.ui.characterHair;

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

public class CharacterHairFragment extends Fragment {

    private ImageView iv_hair1;
    private ImageView iv_hair2;
    private ImageView iv_hair3;
    private ImageView iv_hair4;

    OnCharacterHairReadListener imageReadListener;

    public interface OnCharacterHairReadListener {

        public void onHairRead(Drawable drawable, String string);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_character_hair, container, false);

        iv_hair1 = root.findViewById(R.id.iv_hair1);
        iv_hair2 = root.findViewById(R.id.iv_hair2);
        iv_hair3 = root.findViewById(R.id.iv_hair3);
        iv_hair4 = root.findViewById(R.id.iv_hair4);

        iv_hair1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onHairRead(getResources().getDrawable(R.drawable.hair_black),"hair_black");
            }
        });

        iv_hair2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onHairRead(getResources().getDrawable(R.drawable.hair_brown),"hair_brown");
            }
        });

        iv_hair3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onHairRead(getResources().getDrawable(R.drawable.hair_blue),"hair_blue");
            }
        });

        iv_hair4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onHairRead(getResources().getDrawable(R.drawable.hair_yellow),"hair_yellow");
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            imageReadListener = (CharacterHairFragment.OnCharacterHairReadListener) activity;
        }catch (ClassCastException e){
            throw  new ClassCastException(activity.toString()+"must override message");
        }
    }
}