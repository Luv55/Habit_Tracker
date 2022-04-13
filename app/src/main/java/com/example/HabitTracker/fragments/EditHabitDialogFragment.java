package com.example.HabitTracker.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.HabitTracker.R;
import com.example.HabitTracker.habitos.Habit;
import com.example.HabitTracker.interfaces.OnHabitInteractionListener;
import com.example.HabitTracker.interfaces.OnNewHabitListener;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditHabitDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditHabitDialogFragment extends DialogFragment {

    private long habitID;
    private String name, description, tag, difficulty, streakReach, time;
    private int streak;
    AlertDialog.Builder builder;
    OnNewHabitListener mListener;
    OnHabitInteractionListener mListener2;
    View v;
    EditText et_name, et_description;
    String bt_difficulty, bt_streak, bt_tag;
    Button bt_dif_1, bt_dif_2, bt_dif_3, bt_dif_4, bt_streak_1, bt_streak_2, bt_streak_3, bt_tag_sports, bt_tag_social, bt_tag_meditation, bt_tag_diet, bt_tag_studies, bt_tag_wellbeing;
    Context ctx;
    Realm realm;


    public EditHabitDialogFragment() {
        // Required empty public constructor
    }

    public static EditHabitDialogFragment newInstance(long hID, String n, String d, String dif, String t, String sr, int s, long uID, String ti) {
        EditHabitDialogFragment fragment = new EditHabitDialogFragment();
        Bundle args = new Bundle();
        args.putLong(Habit.HABIT_ID, hID);
        args.putString(Habit.HABIT_NAME, n);
        args.putString(Habit.HABIT_DESCRIPTION, d);
        args.putString(Habit.HABIT_DIFFICULTY, dif);
        args.putString(Habit.HABIT_TAG, t);
        args.putString(Habit.HABIT_STREAKREACH, sr);
        args.putInt(Habit.HABIT_STREAK, s);
        args.putLong(Habit.HABIT_USERID, uID);
        args.putString(Habit.HABIT_TIME, ti);
        fragment.setArguments(args);
        Log.d("argumentos", args.toString());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            habitID = getArguments().getLong(Habit.HABIT_ID);
            name = getArguments().getString(Habit.HABIT_NAME);
            description = getArguments().getString(Habit.HABIT_DESCRIPTION);
            difficulty = getArguments().getString(Habit.HABIT_DIFFICULTY);
            tag = getArguments().getString(Habit.HABIT_TAG);
            streakReach = getArguments().getString(Habit.HABIT_STREAKREACH);
            streak = getArguments().getInt(Habit.HABIT_STREAK);
            time = getArguments().getString(Habit.HABIT_TIME);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());

        ctx = getActivity();
        Toast.makeText(ctx, habitID +" "+ name +" "+ description +" "+ difficulty, Toast.LENGTH_SHORT).show();

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

        habitID = getArguments().getLong(Habit.HABIT_ID);
        et_name.setText(getArguments().getString(Habit.HABIT_NAME));
        name = getArguments().getString(Habit.HABIT_NAME);
        et_description.setText(getArguments().getString(Habit.HABIT_DESCRIPTION));
        description = getArguments().getString(Habit.HABIT_DESCRIPTION);

        if (difficulty.equals("1")){
            bt_dif_1.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
            bt_difficulty = "1";
        }
        if (difficulty.equals("2")){
            bt_dif_2.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
            bt_difficulty = "2";
        }
        if (difficulty.equals("3")){
            bt_dif_3.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
            bt_difficulty = "3";
        }
        if (difficulty.equals("4")){
            bt_dif_4.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
            bt_difficulty = "4";
        }

        if (streakReach.equals("1")){
            bt_streak_1.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
            bt_streak = "1";
        }
        if (streakReach.equals("2")){
            bt_streak_2.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
            bt_streak = "2";
        }
        if (streakReach.equals("3")){
            bt_streak_3.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
            bt_streak = "3";
        }

        switch (tag){
            case "studies":
                bt_tag_studies.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "studies";
                break;
            case "wellbeing":
                bt_tag_wellbeing.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "wellbeing";
                break;
            case "diet":
                bt_tag_diet.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "diet";
                break;
            case "meditation":
                bt_tag_meditation.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "meditation";
                break;
            case "sports":
                bt_tag_sports.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "sports";
                break;
            case "social":
                bt_tag_social.setBackground(getContext().getDrawable(R.drawable.rounded_buttom_pressed));
                bt_tag = "soxial";
                break;
        }

        //Difficulty
        bt_dif_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(bt_difficulty.equals("0"))){
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
                if (!(bt_difficulty.equals("0"))){
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
                if (!(bt_difficulty.equals("0"))){
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
                if (!(bt_difficulty.equals("0"))){
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

        builder.setTitle(getText(R.string.editHabit) )
                .setPositiveButton(getText(R.string.save), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), getText(R.string.habitSaved), Toast.LENGTH_SHORT).show();

                        String name = et_name.getText().toString();
                        String description = et_description.getText().toString();
                        String difficulty = bt_difficulty;
                        String streakReach = bt_streak;
                        String tag = bt_tag;


                        if(!name.isEmpty() && !difficulty.isEmpty() && !streakReach.isEmpty() && !tag.isEmpty()) {
                            mListener.onHabitUpdateClickListener(habitID,name, description, difficulty, tag, streakReach, streak , R.string.preferences_id, time);

                            dialog.dismiss();
                        } else {
                            Toast.makeText(ctx, getText(R.string.dialogs), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNeutralButton(getText(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mListener2.onHabitRemove(habitID);
                    }
                })
                .setNegativeButton(getText(R.string.close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Cancelar > cerrar el cuadro de diálogo
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
            mListener2 = (OnHabitInteractionListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement OnNuevaAveriaListener");
        }
    }

}