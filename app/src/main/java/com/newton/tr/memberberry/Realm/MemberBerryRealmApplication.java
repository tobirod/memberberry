package com.newton.tr.memberberry.Realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MemberBerryRealmApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("memberberry.realm")
                .schemaVersion(0)
                .build();

        Realm.setDefaultConfiguration(realmConfig);
    }
}