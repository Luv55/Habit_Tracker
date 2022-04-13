package com.example.HabitTracker.fragments;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.HabitTracker.ActivityHome;
import com.example.HabitTracker.R;
import com.example.HabitTracker.interfaces.OnNewHabitListener;
import com.example.HabitTracker.interfaces.OnSplashScreen;


public class FragmentLoadingScreen extends DialogFragment {

    AlertDialog.Builder builder;
    OnSplashScreen mListener;
    View v;
    ProgressBar pb_1;
    Context ctx;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        builder = new AlertDialog.Builder(getActivity());

        ctx = getActivity();


        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        v = inflater.inflate(R.layout.fragment_loading_screen, null);

        pb_1 = v.findViewById(R.id.progressBar3);
        ObjectAnimator.ofInt(pb_1, "progress", 100).setDuration(5000).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListener.onLoading(true);
            }
        }, 5000);

        builder.setView(v);

        return builder.create();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading_screen, container, false);
    }
}