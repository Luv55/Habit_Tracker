package com.example.HabitTracker.alarms;

import com.example.HabitTracker.myApp.MyApp;

import java.sql.Timestamp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Alarm extends RealmObject {

    public static final String ALARM_ID = "id";
    public static final String ALARM_NAME = "name";
    public static final String ALARM_TIME = "time";
    public static final String ALARM_USERID = "alarmId";

    @PrimaryKey
    private long id;
    private String name;
    private String hour;
    private String minute;
    private String time;
    private long userId;
    private boolean active;

    public Alarm() {

    }


    public Alarm(long id, String name, String hour, String minute, String time, Long userId, boolean active) {
        this.id = id;
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.time = time;
        this.userId = userId;
        this.active = active;
    }

    public Alarm(String name, String hour, String minute, String time, Long userId, boolean active) {
        this.id = MyApp.HabitID.incrementAndGet();
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.time = time;
        this.userId = userId;
        this.active = active;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", userId=" + userId +
                '}';
    }
}
