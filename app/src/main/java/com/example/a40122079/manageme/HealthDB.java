package com.example.a40122079.manageme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HealthDB {

    public SQLiteDatabase DB;
    public String DBPath;
    public static String DBName = "myDB";
    public static final int version = '1';
    public static Context currentContext;
    public static String tableName = "habits";
    private static String LOG_TAG = "PopulateDatabase";
    // Contacts Table Column habit
    private static final String KEY_HABITS = "habit";
    private SQLiteDatabase storage;
    private SQLiteOpenHelper helper;


    public HealthDB(Context ctx) {
        helper = new SQLiteOpenHelper(ctx, DBName, null, version) {
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                  int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + tableName);
                onCreate(db);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE " +tableName + "("
                        + KEY_HABITS + " TEXT PRIMARY KEY" +")");
            }
        };
        storage = helper.getWritableDatabase();
    }



//adding habits
    public boolean add(String habit) {
        try {
            ContentValues row=new ContentValues();
            row.put(KEY_HABITS, habit);
            storage.insert(tableName, null, row);
            Log.i(LOG_TAG, String.format("(%s) inserted", habit));
        }

        catch (SQLiteException e) {
            System.err.println("Exception Message");
        }
           return true;
        }


    public void add1 (String habit) {
        ContentValues data = new ContentValues();
        data.put(KEY_HABITS, habit);
        storage.insert(tableName, null, data);
    }



//getting selected
    public List<String> getHabits(){
        Log.d(Todo.APP_TAG, "getHabits triggered");
        List<String> list = new ArrayList<String>();
        Cursor c = storage.query(tableName, new String[]{"habit"}, null,
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            while (c.isAfterLast() == false){
               list.add(c.getString(0));
                c.moveToNext();
            }
            c.close();
            Log.d(LOG_TAG,"finished" +c.getCount());
        }
        return list;
    }







}
