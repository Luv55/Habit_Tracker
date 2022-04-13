package com.example.HabitTracker;

import com.example.HabitTracker.myApp.MyApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_LEVEL = "1";
    private static final String USER_EXPERIENCE = "0.0";
    private static final String USER_HEALTH = "50";
    private static final String USER_MONEY = "0";

    @PrimaryKey
    private long id;
    private String name;
    private String email;
    private String password;
    private int level;
    private double experience;
    private int health;
    private int money;


    public User() {

    }

    public User(long id, String name, String email, String password, int level, double experience, int health, int money) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
        this.experience = experience;
        this.health = health;
        this.money = money;
    }

    public User(String name, String email, String password, int level, double experience, int health, int money) {
        this.id = MyApp.TagsID.incrementAndGet();
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
        this.experience = experience;
        this.health = health;
        this.money = money;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
