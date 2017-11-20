package com.newton.tr.member.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.UUID;

public class TaskRepo {

    static final String TASKTABLE_NAME = "TASK_TABLE";
    private static final String TAG = "TaskRepo";

    private static final String COL0 = "ID";
    private static final String COL1 = "UUID";
    private static final String COL2 = "STATUS";
    private static final String COL3 = "PRIOLEVEL";
    private static final String COL4 = "DATEADDED";
    private static final String COL5 = "TASK";

     static String createTaskTable(){
        return "CREATE TABLE " + TASKTABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT, " + COL2 + " BOOLEAN, " + COL3 + " INT, " + COL4 + " TEXT, " + COL5 + " TEXT)";
    }

    public boolean addTask(String UUID, boolean status, int prioLevel, String dateAdded, String task) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, UUID);
        contentValues.put(COL2, status);
        contentValues.put(COL3, prioLevel);
        contentValues.put(COL4, dateAdded);
        contentValues.put(COL5, task);

        Log.d(TAG, "addTask: Adding task: " + UUID + " to" + TASKTABLE_NAME);

        long taskResult = db.insert(TASKTABLE_NAME, null, contentValues);
        DBManager.getInstance().closeDatabase();

        return taskResult != -1;
    }

    public Cursor getAllTasks() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + TASKTABLE_NAME;
        DBManager.getInstance().closeDatabase();

        return db.rawQuery(query, null);
    }

    public Cursor getTaskId(String UUID) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "SELECT " + COL0 + " FROM " + TASKTABLE_NAME + " WHERE " + COL1 + " = '" + UUID + "'";
        Cursor data = db.rawQuery(query, null);
        DBManager.getInstance().closeDatabase();

        return data;
    }

    public void deleteTask(int ID, String UUID) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "DELETE FROM " + TASKTABLE_NAME + " WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL1 + " = '" + UUID + "'";
        Log.d(TAG, "deleteTask: query:" + query);
        Log.d(TAG, "deleteTask: Deleting task: " + UUID + " from database.");
        db.execSQL(query);
        DBManager.getInstance().closeDatabase();
    }

    public void updateTask(int ID, String UUID, boolean status, int prioLevel, String dateAdded, String task) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "UPDATE " + TASKTABLE_NAME + " SET " + COL1 + " = '" + UUID + "' WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL1 + " = '" + oldProduct + "'";
        Log.d(TAG, "updateProduct: query:" + query);
        Log.d(TAG, "updateProduct: New product is " + newProduct);
        db.execSQL(query);
    }
}
