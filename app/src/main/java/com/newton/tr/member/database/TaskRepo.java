package com.newton.tr.member.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.newton.tr.member.models.Task;

import java.util.ArrayList;

public class TaskRepo {

    static final String TASKTABLE_NAME = "TASK_TABLE";
    private static final String TAG = "TaskRepo";

    private static final String COL0 = "ID";
    private static final String COL1 = "STATUS";
    private static final String COL2 = "DATEADDED";
    private static final String COL3 = "TASK";

     static String createTaskTable(){
        return "CREATE TABLE " + TASKTABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " INT, " + COL2 + " TEXT, " + COL3 + " TEXT)";
    }

    public boolean addTask(int ID, boolean status, String dateAdded, String task) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, status);
        contentValues.put(COL2, dateAdded);
        contentValues.put(COL3, task);

        Log.d(TAG, "addTask: Adding task: " + ID + ": '" + task + "' to '" + TASKTABLE_NAME + "'.");

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

                String dateAdded = data.getString(2);
                String task = data.getString(3);

                Task taskBuffer = new Task(taskID, taskStatus, false, dateAdded, task);

                listData.add(taskBuffer);

            } while (data.moveToNext());
        }

        data.close();

        DBManager.getInstance().closeDatabase();
        return listData;
    }

    public void deleteTask(int ID, String task) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "DELETE FROM " + TASKTABLE_NAME + " WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL3 + " = '" + task + "'";
        Log.d(TAG, "deleteTask: query: '" + query + "'.");
        Log.d(TAG, "deleteTask: Deleting task with ID " + ID + "; '" + task + "' from database.");
        db.execSQL(query);
        DBManager.getInstance().closeDatabase();
    }

    public void updateTask(int ID, int status, String dateAdded, String task, String oldTask) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "UPDATE " + TASKTABLE_NAME + " SET " + COL1 + " = '" + status + "', " + COL2 + " = '" + dateAdded + "', " + COL3 + " = '" + task + "' WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL3 + " = '" + oldTask + "'";
        Log.d(TAG, "updateTask: query: '" + query + "'.");
        Log.d(TAG, "updateTask: New task is '" + task + "' and status is '" + status + "'.");
        db.execSQL(query);
        DBManager.getInstance().closeDatabase();
    }
}
