package com.example.HabitTracker.ui.alarms;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.HabitTracker.R;
import com.example.HabitTracker.alarms.Alarm;
import com.example.HabitTracker.interfaces.OnAlarmInteractionListener;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;


public class MyAlarmRecyclerViewAdapter extends RecyclerView.Adapter<MyAlarmRecyclerViewAdapter.ViewHolder> {

    private final List<Alarm> mValues;
    private final OnAlarmInteractionListener mListener;
    private Context ctx;
    private RealmChangeListener listenerRefresh;
    private int streak = 0;

    public MyAlarmRecyclerViewAdapter(FragmentActivity context, RealmResults<Alarm> items, OnAlarmInteractionListener listener) {
        ctx = context;
        mValues = items;
        mListener = listener;
        this.listenerRefresh = new RealmChangeListener<OrderedRealmCollection<Alarm>>() {
            @Override
            public void onChange(OrderedRealmCollection<Alarm> results) {
                notifyDataSetChanged();
            }
        };

        if (items != null) {
            addListener(items);
        }
    }

    private void addListener(OrderedRealmCollection<Alarm> items) {
        if (items instanceof RealmResults) {
            RealmResults realmResults = (RealmResults) items;
            realmResults.addChangeListener(listenerRefresh);
        } else if (items instanceof RealmList) {
            RealmList<Alarm> list = (RealmList<Alarm>) items;
            //noinspection unchecke
            list.addChangeListener((RealmChangeListener) listenerRefresh);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + items.getClass());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAlarmRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);

        holder.tv_name.setText(holder.mItem.getName());
        holder.tv_time.setText(holder.mItem.getTime());
        holder.alarmContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAlarmClick(holder.mItem);
            }
        });

    }



    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tv_name;
        public final TextView tv_time;
        public final ConstraintLayout alarmContainer;
        public Alarm mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv_name = view.findViewById(R.id.tv_alarmName);
            tv_time = view.findViewById(R.id.tv_alarmtime);
            alarmContainer = view.findViewById(R.id.alarmContainer);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_name.getText() + "'";
        }
    }
}