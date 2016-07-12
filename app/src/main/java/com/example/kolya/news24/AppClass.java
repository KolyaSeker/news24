package com.example.kolya.news24;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AppClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
