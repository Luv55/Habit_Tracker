package com.example.HabitTracker;

import com.example.HabitTracker.myApp.MyApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tags extends RealmObject {

    public static final String TAG_ID = "id";
    public static final String TAG_STUDIES = "0";
    public static final String TAG_SOCIAL = "0";
    public static final String TAG_DIET = "0";
    public static final String TAG_WLLBEING = "0";
    public static final String TAG_SPORTS = "0";
    public static final String TAG_MEDITATION = "0";
    public static final String TAG_USERID = "1";

    @PrimaryKey
    private long id;
    private int studies;
    private int social;
    private int diet;
    private int wellbeing;
    private int sports;
    private int meditation;
    private long userId;

    public Tags() {this.id = MyApp.TagsID.incrementAndGet();
    }

    public Tags(long id, int studies, int social, int diet, int wellbeing, int sports, int meditation, long userId) {
        this.id = id;
        this.studies = studies;
        this.social = social;
        this.diet = diet;
        this.wellbeing = wellbeing;
        this.sports = sports;
        this.meditation = meditation;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStudies() {
        return studies;
    }

    public void setStudies(int studies) {
        this.studies = studies;
    }

    public int getSocial() {
        return social;
    }

    public void setSocial(int social) {
        this.social = social;
    }

    public int getDiet() {
        return diet;
    }

    public void setDiet(int diet) {
        this.diet = diet;
    }

    public int getWellbeing() {
        return wellbeing;
    }

    public void setWellbeing(int wellbeing) {
        this.wellbeing = wellbeing;
    }

    public int getSports() {
        return sports;
    }

    public void setSports(int sports) {
        this.sports = sports;
    }

    public int getMeditation() {
        return meditation;
    }

    public void setMeditation(int meditation) {
        this.meditation = meditation;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "id=" + id +
                ", studies=" + studies +
                ", social=" + social +
                ", diet=" + diet +
                ", wellbeing=" + wellbeing +
                ", sports=" + sports +
                ", meditation=" + meditation +
                ",userId="+userId+
                '}';
    }
}
