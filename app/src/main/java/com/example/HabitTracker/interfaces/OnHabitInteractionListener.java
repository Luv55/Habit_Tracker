package com.example.HabitTracker.interfaces;

import com.example.HabitTracker.habitos.Habit;

public interface OnHabitInteractionListener {
    public void onHabitClick(Habit mItem);
    public void onHabitRemove(long habitID);
    public void onAddListener(Habit mItem);
    public void onSubstractListener(Habit mItem);
    public void onReviveUSer(boolean newboolean);
    public void onBuyClothes();
    public void onFirstTime();
}
