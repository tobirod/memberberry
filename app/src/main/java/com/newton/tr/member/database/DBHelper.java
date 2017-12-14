package com.newton.tr.member.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.newton.tr.member.app.App;
import com.newton.tr.member.models.Item;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "MemberDB.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskRepo.createTaskTable());
        db.execSQL(ItemRepo.createItemTable());
        db.execSQL(NoteRepo.createNoteTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + TaskRepo.TASKTABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemRepo.ITEMTABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NoteRepo.NOTETABLE_NAME);
        onCreate(db);
    }
}
