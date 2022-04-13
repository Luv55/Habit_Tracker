package com.example.HabitTracker.fragments;

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
import android.widget.EditText;

import com.example.HabitTracker.R;
import com.example.HabitTracker.interfaces.OnNewHabitListener;


public class NewAlarmDialog extends DialogFragment {

    AlertDialog.Builder builder;
    OnNewHabitListener mListener;
    View v;
    EditText et_name, et_description;
    String bt_difficulty, bt_streak, bt_tag;
    Context ctx;


}