package com.example.HabitTracker.ui.habits;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HabitTracker.R;
import com.example.HabitTracker.habitos.Habit;
import com.example.HabitTracker.interfaces.OnHabitInteractionListener;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListHabitFragment extends Fragment {

    OnHabitInteractionListener mListener;
    RealmResults<Habit> habitList;
    Realm realm;

    public ListHabitFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habits, container, false);

        // Seleccionamos el adaptador
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // Lista de averias
            habitList = realm.where(Habit.class).findAll();

            recyclerView.setAdapter(new MyHabitRecyclerViewAdapter(getActivity(), habitList, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHabitInteractionListener) {
            mListener = (OnHabitInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHabitInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}