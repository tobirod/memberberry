package com.newton.tr.member.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.newton.tr.member.models.Note;

import java.util.ArrayList;

public class NoteRepo {

    static final String NOTETABLE_NAME = "NOTE_TABLE";
    private static final String TAG = "ItemRepo";

    private static final String COL0 = "ID";
    private static final String COL1 = "TITLE";
    private static final String COL2 = "BODY";

    static String createNoteTable(){
        return "CREATE TABLE " + NOTETABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT, " + COL2 + " TEXT)";
    }

    public boolean addNote(int ID, String title, String body) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, title);
        contentValues.put(COL2, body);

        Log.d(TAG, "addNote: Adding Note: " + ID + ": '" + title + "' to '" + NOTETABLE_NAME + "'.");

        long noteResult = db.insert(NOTETABLE_NAME, null, contentValues);
        DBManager.getInstance().closeDatabase();

        return noteResult != -1;
    }

    public ArrayList<Note> getAllNotes() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + NOTETABLE_NAME;

        ArrayList<Note> listData = new ArrayList<>();

        Cursor data = db.rawQuery(query, null);

        if (data.moveToFirst()) {
            do {

                int noteID = data.getInt(0);

                String noteTitle = data.getString(1);
                String noteBody = data.getString(2);

                Note noteBuffer = new Note(noteID, false, false, noteTitle, noteBody);

                listData.add(noteBuffer);

            } while (data.moveToNext());
        }

        data.close();

        DBManager.getInstance().closeDatabase();
        return listData;
    }

    public void deleteNote(int ID, String title) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "DELETE FROM " + NOTETABLE_NAME + " WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL2 + " = '" + title + "'";
        Log.d(TAG, "deleteNote: query: '" + query + "'.");
        Log.d(TAG, "deleteNote: Deleting item with ID " + ID + "; '" + title + "' from database.");
        db.execSQL(query);
        DBManager.getInstance().closeDatabase();
    }

    public void updateNote(int ID, String title, String body, String oldTitle) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "UPDATE " + NOTETABLE_NAME + " SET " + COL1 + " = '" + title + "', " + COL2 + " = '" + body + "' WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL1 + " = '" + oldTitle + "'";
        Log.d(TAG, "updateNote: query: '" + query + "'.");
        Log.d(TAG, "updateNote: New note is '" + title + "' and body is '" + body + "'.");
        db.execSQL(query);
        DBManager.getInstance().closeDatabase();
    }

}
