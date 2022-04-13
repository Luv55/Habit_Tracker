package com.example.HabitTracker;

import com.example.HabitTracker.myApp.MyApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Characters extends RealmObject {

    private static final String CHARACTER_ID = "id";
    private static final String CHARACTER_FACE = "face";
    private static final String CHARACTER_BODY = "body";
    private static final String CHARACTER_HAIR = "hair";

    @PrimaryKey
    private long id;
    private String face;
    private String body;
    private String hair;
    private long userId;

    public Characters() { this.id = MyApp.CharacterID.incrementAndGet();
    }

    public Characters(long id,String face, String body, String hair, long userId) {
        this.id = id;
        this.face = face;
        this.body = body;
        this.hair = hair;
        this.userId = userId;
    }

    public Characters(String face, String body, String hair, long userId) {
        this.id = MyApp.CharacterID.getAndIncrement();
        this.face = face;
        this.body = body;
        this.hair = hair;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", face=" + face +
                ", body=" + body +
                ", hair=" + hair +
                ", userId=" + userId +
                '}';
    }
}
