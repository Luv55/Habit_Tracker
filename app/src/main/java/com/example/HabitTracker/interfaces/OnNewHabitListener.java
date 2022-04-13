package com.example.HabitTracker.interfaces;

public interface OnNewHabitListener {

    public void onHabitSaveClickListener(String name, String description, String difficulty, String tag, String streakReach, int streak, long userID, String time);
    public void onHabitUpdateClickListener(long id, String name, String description, String difficulty,String tag, String streakReach, int streak, long userID, String time);

}
