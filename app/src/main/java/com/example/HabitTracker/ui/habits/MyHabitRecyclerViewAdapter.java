package com.example.HabitTracker.ui.habits;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HabitTracker.R;
import com.example.HabitTracker.habitos.Habit;
import com.example.HabitTracker.interfaces.OnHabitInteractionListener;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;


public class MyHabitRecyclerViewAdapter extends RecyclerView.Adapter<MyHabitRecyclerViewAdapter.ViewHolder> {

    private final List<Habit> mValues;
    private final OnHabitInteractionListener mListener;
    private Context ctx;
    private RealmChangeListener listenerRefresh;
    private int streak = 0;

    public MyHabitRecyclerViewAdapter(FragmentActivity context, RealmResults<Habit> items, OnHabitInteractionListener listener) {
        ctx = context;
        mValues = items;
        mListener = listener;
        this.listenerRefresh = new RealmChangeListener<OrderedRealmCollection<Habit>>() {
            @Override
            public void onChange(OrderedRealmCollection<Habit> results) {
                notifyDataSetChanged();
            }
        };

        if (items != null) {
            addListener(items);
        }
    }

    private void addListener(OrderedRealmCollection<Habit> items) {
        if (items instanceof RealmResults) {
            RealmResults realmResults = (RealmResults) items;
            realmResults.addChangeListener(listenerRefresh);
        } else if (items instanceof RealmList) {
            RealmList<Habit> list = (RealmList<Habit>) items;
            //noinspection unchecke
            list.addChangeListener((RealmChangeListener) listenerRefresh);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + items.getClass());
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_habit_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);

        holder.tv_name.setText(holder.mItem.getName());
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddListener(holder.mItem);
            }
        });
        holder.iv_subs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSubstractListener(holder.mItem);
            }
        });
        holder.habitContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onHabitClick(holder.mItem);
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
        public final ImageView iv_add;
        public final ImageView iv_subs;
        public final ConstraintLayout habitContainer;
        public Habit mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv_name = view.findViewById(R.id.tv_name_habit);
            iv_add = view.findViewById(R.id.iv_add);
            iv_subs = view.findViewById(R.id.iv_subs);
            habitContainer = view.findViewById(R.id.HabitContainer);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_name.getText() + "'";
        }
    }
}