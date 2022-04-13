package com.example.HabitTracker.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.HabitTracker.R;
import com.example.HabitTracker.Tags;

import io.realm.Realm;
import io.realm.RealmResults;

public class StatsFragment extends Fragment {

    ProgressBar pb_studies;
    ProgressBar pb_sports;
    ProgressBar pb_meditation;
    ProgressBar pb_diet;
    ProgressBar pb_wellbeing;
    ProgressBar pb_social;
    Realm realm;
    RealmResults<Tags> tagsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stats, container, false);

        pb_studies = root.findViewById(R.id.pb_studies);
        pb_sports = root.findViewById(R.id.pb_sports);
        pb_meditation = root.findViewById(R.id.pb_meditation);
        pb_diet = root.findViewById(R.id.pb_diet);
        pb_wellbeing = root.findViewById(R.id.pb_wellbeing);
        pb_social = root.findViewById(R.id.pb_social);

        realm = Realm.getDefaultInstance();

        tagsList = realm.where(Tags.class).findAll();

        pb_studies.setMax(100);
        pb_studies.setProgress(tagsList.get(0).getStudies());

        pb_diet.setMax(100);
        pb_diet.setProgress(tagsList.get(0).getDiet());

        pb_meditation.setMax(100);
        pb_meditation.setProgress(tagsList.get(0).getMeditation());

        pb_social.setMax(100);
        pb_social.setProgress(tagsList.get(0).getSocial());

        pb_sports.setMax(100);
        pb_sports.setProgress(tagsList.get(0).getSports());

        pb_wellbeing.setMax(100);
        pb_wellbeing.setProgress(tagsList.get(0).getWellbeing());

        return root;
    }
}