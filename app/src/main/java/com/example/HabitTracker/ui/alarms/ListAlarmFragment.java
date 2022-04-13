package com.example.HabitTracker.ui.alarms;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.HabitTracker.R;
import com.example.HabitTracker.alarms.Alarm;
import com.example.HabitTracker.habitos.Habit;
import com.example.HabitTracker.interfaces.OnAlarmInteractionListener;
import com.example.HabitTracker.interfaces.OnHabitInteractionListener;
import com.example.HabitTracker.ui.habits.MyHabitRecyclerViewAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A fragment representing a list of Items.
 */
public class ListAlarmFragment extends Fragment {

    OnAlarmInteractionListener mListener;
    RealmResults<Alarm> alarmList;
    Realm realm;

    public ListAlarmFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_alarm, container, false);

        // Seleccionamos el adaptador
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // Lista de alarmas
            alarmList = realm.where(Alarm.class).findAll();

            recyclerView.setAdapter(new MyAlarmRecyclerViewAdapter(getActivity(), alarmList, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnAlarmInteractionListener) {
            mListener = (OnAlarmInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAlarmInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    // TODO: Customize parameter argument names
    //private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    //private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    /*public ListAlarmFragment() {
    }*/

    // TODO: Customize parameter initialization
    /*public static ListAlarmFragment newInstance(int columnCount) {
        ListAlarmFragment fragment = new ListAlarmFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_alarm, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyAlarmRecyclerViewAdapter(DummyContent.ITEMS));
        }
        return view;
    }*/
}