package com.example.HabitTracker.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.HabitTracker.R;
import com.example.HabitTracker.interfaces.OnNewHabitListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class NewHabitDialog  extends DialogFragment {
    AlertDialog.Builder builder;
    OnNewHabitListener mListener;
    View v;
    EditText et_name, et_description;
    String bt_difficulty, bt_streak, bt_tag;
    Button bt_dif_1, bt_dif_2, bt_dif_3, bt_dif_4, bt_streak_1, bt_streak_2, bt_streak_3, bt_tag_sports, bt_tag_social, bt_tag_meditation, bt_tag_diet, bt_tag_studies, bt_tag_wellbeing;
    Context ctx;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());

        ctx = getActivity();


        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        v = inflater.inflate(R.layout.fragment_new_habit_dialog, null);
        et_name = v.findViewById(R.id.et_tName);
        et_description = v.findViewById(R.id.et_Description);
        bt_dif_1 = v.findViewById(R.id.bt_dif_1);
        bt_dif_2 = v.findViewById(R.id.bt_dif_2);
        bt_dif_3 = v.findViewById(R.id.bt_dif_3);
        bt_dif_4 = v.findViewById(R.id.bt_dif_4);
        bt_streak_1 = v.findViewById(R.id.bt_streak_1);
        bt_streak_2 = v.findViewById(R.id.bt_streak_2);
        bt_streak_3 = v.findViewById(R.id.bt_streak_3);
        bt_tag_diet = v.findViewById(R.id.bt_tag_diet);
        bt_tag_meditation = v.findViewById(R.id.bt_tag_meditation);
        bt_tag_social = v.findViewById(R.id.bt_tag_social);
        bt_tag_sports = v.findViewById(R.id.bt_tag_sport);
        bt_tag_studies = v.findViewById(R.id.bt_tag_studies);
        bt_tag_wellbeing = v.findViewById(R.id.bt_tag_wellbeing);

        bt_difficulty = "";
        bt_streak="";
        bt_tag="";
        //Difficulty
        bt_dif_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bt_difficulty.equals("0")){
                    bt_dif_2.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_3.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_4.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_1.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                }
                    bt_dif_1.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                    bt_difficulty = "1";

            }
        });
        bt_dif_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bt_difficulty.equals("0")){
                    bt_dif_1.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_3.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_4.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_2.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                }
                    bt_dif_2.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                    bt_difficulty = "2";

            }
        });
        bt_dif_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bt_difficulty.equals("0")){
                    bt_dif_1.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_2.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_4.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_3.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                }
                bt_dif_3.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_difficulty = "3";
            }
        });
        bt_dif_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bt_difficulty.equals("0")){
                    bt_dif_1.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_2.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_3.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_dif_4.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                }
                bt_dif_4.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_difficulty = "4";
            }
        });

        //Streak
        bt_streak_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bt_streak.equals("0")){
                    bt_streak_3.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_streak_2.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_streak_1.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                }
                bt_streak_1.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_streak = "1";
            }
        });
        bt_streak_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bt_streak.equals("0")){
                    bt_streak_3.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_streak_1.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_streak_2.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                }
                bt_streak_2.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_streak = "2";
            }
        });
        bt_streak_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bt_streak.equals("0")){
                    bt_streak_2.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_streak_1.setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                    bt_streak_3.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                }
                bt_streak_3.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_streak = "3";
            }
        });

        //Tag
        final ArrayList<Button> btTags = new ArrayList<Button>();
        btTags.add(bt_tag_diet);
        btTags.add(bt_tag_meditation);
        btTags.add(bt_tag_social);
        btTags.add(bt_tag_sports);
        btTags.add(bt_tag_studies);
        btTags.add(bt_tag_wellbeing);

        bt_tag_studies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<btTags.size(); i++){
                    btTags.get(i).setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                }
                bt_tag_studies.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "studies";
            }
        });
        bt_tag_wellbeing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<btTags.size(); i++){
                    btTags.get(i).setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                }
                bt_tag_wellbeing.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "wellbeing";
            }
        });
        bt_tag_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<btTags.size(); i++){
                    btTags.get(i).setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                }
                bt_tag_sports.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "sports";
            }
        });
        bt_tag_social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<btTags.size(); i++){
                    btTags.get(i).setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                }
                bt_tag_social.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "social";
            }
        });
        bt_tag_meditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<btTags.size(); i++){
                    btTags.get(i).setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                }
                bt_tag_meditation.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "meditation";
            }
        });
        bt_tag_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<btTags.size(); i++){
                    btTags.get(i).setBackground(getContext().getDrawable(R.drawable.rounded_button_default));
                }
                bt_tag_diet.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "diet";
            }
        });

        builder.setView(v);

        builder.setTitle(getText(R.string.newHabit))
                .setPositiveButton(getText(R.string.save), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String name = et_name.getText().toString();
                        String description = et_description.getText().toString();
                        String difficulty = bt_difficulty;
                        String streak = bt_streak;
                        String tag = bt_tag;
                        if(!name.isEmpty() && !difficulty.isEmpty() && !streak.isEmpty() && !tag.isEmpty()) {
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Timestamp(System.currentTimeMillis()));
                            c.set(Calendar.MILLISECOND, 0);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm.ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                            mListener.onHabitSaveClickListener(name, description, difficulty, tag, streak, 0, R.string.preferences_id, (sdf.format(c.getTime()))+"");

                            Toast.makeText(getActivity(), getText(R.string.habitSaved), Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        } else {
                            Toast.makeText(ctx, getText(R.string.dialogs), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(getText(R.string.close), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OnNewHabitListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement OnNewHabitListener");
        }
    }
}