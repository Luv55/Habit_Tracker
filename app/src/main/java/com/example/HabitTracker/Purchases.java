package com.example.HabitTracker;

import com.example.HabitTracker.myApp.MyApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Purchases extends RealmObject {

    public static final String PURCHASES_ID = "id";
    public static final String PURCHASES_NAME = "name";
    public static final String PURCHASES_USERID = "userid";

    @PrimaryKey
    private long id;
    private String name;
    private long userId;

    public Purchases() {
        this.id = MyApp.PurchasesID.incrementAndGet();
    }

    public Purchases(String name, long userId) {
        this.id = MyApp.PurchasesID.incrementAndGet();
        this.name = name;
        this.userId = userId;
    }

    public Purchases(long id, String name, long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Purchases{" +
                "id=" + id +
                ", name=" + name +
                ", userId=" + userId +
                '}';
    }
}
