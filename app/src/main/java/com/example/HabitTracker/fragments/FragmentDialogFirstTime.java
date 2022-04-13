package com.example.HabitTracker.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.HabitTracker.R;
import com.example.HabitTracker.interfaces.OnHabitInteractionListener;


public class FragmentDialogFirstTime extends DialogFragment {
    AlertDialog.Builder builder;
    View v;
    OnHabitInteractionListener mListener;
    Context ctx;
    Button bt_next;
    TextView tv_content;
    int nTimes;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());

        ctx = getActivity();

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        v = inflater.inflate(R.layout.fragment_dialog_first_time, null);

        nTimes = 0;

        tv_content = v.findViewById(R.id.tv_first_content);
        bt_next = v.findViewById(R.id.bt_next);

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTimes++;

                switch (nTimes){
                    case 1:
                        tv_content.setText(getText(R.string.firstTimeContent2));
                        break;
                    case 2:
                        tv_content.setText(getText(R.string.firstTimeContent3));
                        bt_next.setText(getText(R.string.firstTimeButton2));
                        break;
                    case 3:
                        mListener.onFirstTime();
                }
            }
        });

        builder.setView(v);

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OnHabitInteractionListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement OnHabitInteractionListener");
        }
    }
}