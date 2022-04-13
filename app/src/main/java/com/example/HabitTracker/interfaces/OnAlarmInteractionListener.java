package com.example.HabitTracker.interfaces;

import com.example.HabitTracker.alarms.Alarm;

public interface OnAlarmInteractionListener {
    public void onAlarmClick(Alarm mItem);
    public void onAlarmRemove(long habitID);
}
