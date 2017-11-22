package com.newton.tr.member.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.newton.tr.member.Models.Task;

import java.util.ArrayList;

public class TaskRepo {

    static final String TASKTABLE_NAME = "TASK_TABLE";
    private static final String TAG = "TaskRepo";

    private static final String COL0 = "ID";
    private static final String COL1 = "STATUS";
    private static final String COL2 = "PRIOLEVEL";
    private static final String COL3 = "DATEADDED";
    private static final String COL4 = "TASK";

     static String createTaskTable(){
        return "CREATE TABLE " + TASKTABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " INT, " + COL2 + " INT, " + COL3 + " TEXT, " + COL4 + " TEXT)";
    }

    public boolean addTask(int ID, boolean status, int prioLevel, String dateAdded, String task) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, status);
        contentValues.put(COL2, prioLevel);
        contentValues.put(COL3, dateAdded);
        contentValues.put(COL4, task);

        Log.d(TAG, "addTask: Adding task: " + ID + ": " + task + " to" + TASKTABLE_NAME);

        long taskResult = db.insert(TASKTABLE_NAME, null, contentValues);
        DBManager.getInstance().closeDatabase();

        return taskResult != -1;
    }

    public ArrayList<Task> getAllTasks() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + TASKTABLE_NAME;

        ArrayList<Task> listData = new ArrayList<>();

        Cursor data = db.rawQuery(query, null);

        if (data.moveToFirst()) {
            do {

                int taskID = data.getInt(0);
                boolean taskStatus = false;

                if (data.getInt(1) == 0) {
                    taskStatus = false;
                } else if (data.getInt(1) == 1) {
                    taskStatus = true;
                }

                int prioLevel = data.getInt(2);
                String dateAdded = data.getString(3);
                String task = data.getString(4);

                Task taskBuffer = new Task(taskID, taskStatus, prioLevel, dateAdded, task);

                listData.add(taskBuffer);

            } while (data.moveToNext());
        }

        data.close();

        DBManager.getInstance().closeDatabase();
        return listData;
    }

    public Cursor getTaskId(String UUID) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "SELECT " + COL0 + " FROM " + TASKTABLE_NAME + " WHERE " + COL1 + " = '" + UUID + "'";
        Cursor data = db.rawQuery(query, null);
        DBManager.getInstance().closeDatabase();

        return data;
    }

    public void deleteTask(int ID, String task) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "DELETE FROM " + TASKTABLE_NAME + " WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL4 + " = '" + task + "'";
        Log.d(TAG, "deleteTask: query:" + query);
        Log.d(TAG, "deleteTask: Deleting task: " + ID + ": " + task + " from database.");
        db.execSQL(query);
        DBManager.getInstance().closeDatabase();
    }

//    public void updateTask(int ID, boolean status, int prioLevel, String dateAdded, String task) {
//        SQLiteDatabase db = DBManager.getInstance().openDatabase();
//        String query = "UPDATE " + TASKTABLE_NAME + " SET " + COL1 + " = '" + UUID + "' WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL1 + " = '" + oldProduct + "'";
//
//
//
//        Log.d(TAG, "updateProduct: query:" + query);
//        Log.d(TAG, "updateProduct: New product is " + newProduct);
//        db.execSQL(query);
//    }
}
