package com.example.HabitTracker.habitos;

import com.example.HabitTracker.myApp.MyApp;


import java.sql.Date;
import java.sql.Timestamp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Habit extends RealmObject {

    public static final String HABIT_ID = "id";
    public static final String HABIT_NAME = "name";
    public static final String HABIT_DESCRIPTION = "description";
    public static final String HABIT_DIFFICULTY = "difficulty";
    public static final String HABIT_TAG = "TAG";
    public static final String HABIT_STREAKREACH = "streakReach";
    public static final String HABIT_STREAK = "Streak";
    public static final String HABIT_USERID = "userId";
    public static final String HABIT_TIME = (new Timestamp(System.currentTimeMillis())+"");

    @PrimaryKey
    private long id;
    private String name;
    private String description;
    private String difficulty;
    private String tag;
    private String streakReach;
    private int streak;
    private long userId;
    private String time;

    public Habit() {

    }


    public Habit(long id, String name, String description, String difficulty, String tag, String streakReach, int streak, long userId, String time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.tag = tag;
        this.streakReach = streakReach;
        this.streak = streak;
        this.userId = userId;
        this.time = time;
    }

    public Habit(String name, String description, String difficulty, String tag, String streakReach, int streak, long userId, String time) {
        this.id = MyApp.HabitID.incrementAndGet();
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.tag = tag;
        this.streakReach = streakReach;
        this.streak = streak;
        this.userId = userId;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStreakReach() {
        return streakReach;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public void setStreakReach(String streakReach) {
        this.streakReach = streakReach;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", tag='" + tag + '\'' +
                ", streakReach='" + streakReach + '\'' +
                ", streak=" + streak +
                ", userId=" + userId +
                '}';
    }
}
