package com.example.HabitTracker.ui.characterFace;

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

public class CharacterFaceFragment extends Fragment {

    private ImageView iv_face1;
    private ImageView iv_face2;
    private ImageView iv_face3;
    private ImageView iv_face4;
    private ImageView iv_face5;
    private ImageView iv_face6;
    private ImageView iv_face7;
    private ImageView iv_face8;

    OnCharacterFaceReadListener imageReadListener;

    public CharacterFaceFragment(){

    }

    public interface OnCharacterFaceReadListener {

        public void onFaceRead(Drawable drawable, String string);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_character_face, container, false);

        iv_face1 = root.findViewById(R.id.iv_face1);
        iv_face2 = root.findViewById(R.id.iv_face2);
        iv_face3 = root.findViewById(R.id.iv_face3);
        iv_face4 = root.findViewById(R.id.iv_face4);
        iv_face5 = root.findViewById(R.id.iv_face5);
        iv_face6 = root.findViewById(R.id.iv_face6);
        iv_face7 = root.findViewById(R.id.iv_face7);
        iv_face8 = root.findViewById(R.id.iv_face8);

        iv_face1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onFaceRead(getResources().getDrawable(R.drawable.face5),"face5");
            }
        });

        iv_face2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onFaceRead(getResources().getDrawable(R.drawable.face7),"face7");
            }
        });

        iv_face3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onFaceRead(getResources().getDrawable(R.drawable.face6),"face6");
            }
        });
        iv_face4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onFaceRead(getResources().getDrawable(R.drawable.face8),"face8");
            }
        });

        iv_face5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onFaceRead(getResources().getDrawable(R.drawable.face1),"face1");
            }
        });

        iv_face6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onFaceRead(getResources().getDrawable(R.drawable.face3),"face3");
            }
        });

        iv_face7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onFaceRead(getResources().getDrawable(R.drawable.face2),"face2");
            }
        });

        iv_face8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageReadListener.onFaceRead(getResources().getDrawable(R.drawable.face4),"face4");
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;


        try{
            imageReadListener = (OnCharacterFaceReadListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+"must override message");
        }
    }
}