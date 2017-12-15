package com.newton.tr.member.app;

import android.app.Application;
import android.content.Context;

import com.newton.tr.member.database.DBHelper;
import com.newton.tr.member.database.DBManager;

public class App extends Application {
    private Context context = App.this;

    @Override
    public void onCreate()
    {
        super.onCreate();
        DBHelper dbHelper = new DBHelper(context);
        DBManager.initializeInstance(dbHelper);

    }

}
