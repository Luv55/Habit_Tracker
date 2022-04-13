package com.example.HabitTracker.myApp;

import android.app.Application;

import com.example.HabitTracker.Characters;
import com.example.HabitTracker.Purchases;
import com.example.HabitTracker.Tags;
import com.example.HabitTracker.User;
import com.example.HabitTracker.habitos.Habit;

import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApp extends Application {

    public static AtomicLong HabitID = new AtomicLong();
    public static AtomicLong UserID = new AtomicLong();
    public static AtomicLong CharacterID = new AtomicLong();
    public static AtomicLong TagsID = new AtomicLong();
    public static AtomicLong PurchasesID = new AtomicLong();
    public static AtomicLong AlarmID = new AtomicLong();

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();

        Realm realm = Realm.getDefaultInstance();
        //Se puede copiar si se crean mas clases de habitos
        HabitID = getIdByTable(realm, Habit.class);
        UserID = getIdByTable(realm, User.class);
        CharacterID = getIdByTable(realm, Characters.class);
        TagsID = getIdByTable(realm, Tags.class);
        PurchasesID = getIdByTable(realm, Purchases.class);
        realm.close();
    }

    private void initRealm() {

        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration =  new RealmConfiguration.Builder()
                .name("habitTracker.db")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    private <T extends RealmObject> AtomicLong getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() >0) ? new AtomicLong(results.max("id").intValue()) : new AtomicLong();
    }

}
