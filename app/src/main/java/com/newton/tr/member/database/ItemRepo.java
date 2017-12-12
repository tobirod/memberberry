package com.newton.tr.member.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.newton.tr.member.models.Item;

import java.util.ArrayList;

public class ItemRepo {

    static final String ITEMTABLE_NAME = "ITEM_TABLE";
    private static final String TAG = "ItemRepo";

    private static final String COL0 = "ID";
    private static final String COL1 = "STATUS";
    private static final String COL2 = "NAME";

    static String createItemTable(){
        return "CREATE TABLE " + ITEMTABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " INT, " + COL2 + " TEXT)";
    }

    public boolean addItem(int ID, boolean status, String name) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, status);
        contentValues.put(COL2, name);

        Log.d(TAG, "addItem: Adding item: " + ID + ": '" + name + "' to '" + ITEMTABLE_NAME + "'.");

        long itemResult = db.insert(ITEMTABLE_NAME, null, contentValues);
        DBManager.getInstance().closeDatabase();

        return itemResult != -1;
    }

    public ArrayList<Item> getAllItems() {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + ITEMTABLE_NAME;

        ArrayList<Item> listData = new ArrayList<>();

        Cursor data = db.rawQuery(query, null);

        if (data.moveToFirst()) {
            do {

                int itemID = data.getInt(0);
                boolean itemStatus = false;

                if (data.getInt(1) == 0) {
                    itemStatus = false;
                } else if (data.getInt(1) == 1) {
                    itemStatus = true;
                }

                String name = data.getString(2);

                Item itemBuffer = new Item(itemID, itemStatus, false, name);

                listData.add(itemBuffer);

            } while (data.moveToNext());
        }

        data.close();

        DBManager.getInstance().closeDatabase();
        return listData;
    }

    public void deleteItem(int ID, String name) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "DELETE FROM " + ITEMTABLE_NAME + " WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteItem: query: '" + query + "'.");
        Log.d(TAG, "deleteItem: Deleting item with ID " + ID + "; '" + name + "' from database.");
        db.execSQL(query);
        DBManager.getInstance().closeDatabase();
    }

    public void updateItem(int ID, int status, String name, String oldName) {
        SQLiteDatabase db = DBManager.getInstance().openDatabase();
        String query = "UPDATE " + ITEMTABLE_NAME + " SET " + COL1 + " = '" + status + "', " + COL2 + " = '" + name + "' WHERE " + COL0 + " = '" + ID + "'" + " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateItem: query: '" + query + "'.");
        Log.d(TAG, "updateItem: New item is '" + name + "' and status is '" + status + "'.");
        db.execSQL(query);
        DBManager.getInstance().closeDatabase();
    }

}
