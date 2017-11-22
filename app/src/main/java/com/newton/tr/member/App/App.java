package com.newton.tr.member.App;

import android.app.Application;
import android.content.Context;

import com.newton.tr.member.Database.DBHelper;
import com.newton.tr.member.Database.DBManager;

public class  App extends Application {
    private static Context context;
    private static DBHelper dbHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
        DBManager.initializeInstance(dbHelper);

    }

    public static Context getContext(){
        return context;
    }

}
