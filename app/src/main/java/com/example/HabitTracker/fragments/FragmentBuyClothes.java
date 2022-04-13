package com.example.HabitTracker.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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


public class FragmentBuyClothes extends DialogFragment {

    AlertDialog.Builder builder;
    View v;
    OnHabitInteractionListener mListener;
    Button bt_purchase;
    Context ctx;
    TextView tv_text;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());

        ctx = getActivity();

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        v = inflater.inflate(R.layout.fragment_buy_clothes, null);

        SharedPreferences sharedPref = ctx.getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);

        final int price = Integer.parseInt(sharedPref.getString(getString(R.string.preferences_price),"0"));

        tv_text = v.findViewById(R.id.tv_purchase);
        bt_purchase = v.findViewById(R.id.bottom_buy);

        tv_text.setText(getText(R.string.itemcost)+" "+ price);

        bt_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onBuyClothes();

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