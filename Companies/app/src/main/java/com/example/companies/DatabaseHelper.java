package com.example.companies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CompaniesDB.db";
    public static final String TABLE_NAME = "COMPANY_INFO";
    public static final String COL1 = "ID";
    public static final String COL2 = "EMAIL";
    public static final String COL3 = "LOCATION";
    public static final String COL4 = "PHONE";
    public static final String COL5 = "SOCIALNETWORKLINK";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE IF NOT EXISTS COMPANY_INFO ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "EMAIL TEXT,"
                + "LOCATION TEXT,"
                + "PHONE TEXT,"
                + "SOCIALNETWORKLINK TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    public boolean addData(String col2, String col3, String col4, String col5){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, col2);
        contentValues.put(COL3, col3);
        contentValues.put(COL4, col4);
        contentValues.put(COL5, col5);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT EMAIL, LOCATION, PHONE, SOCIALNETWORKLINK FROM " + TABLE_NAME, null);
        return cur;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
