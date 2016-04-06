package com.example.a40122079.manageme;

/**
 * Created by 40122079 on 06/04/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class HabitsAdapter {

    public static final String KEY_HABIT = "habit";


    private static final String TAG = "HabitsAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "World";
    private static final String SQLITE_TABLE = "Country";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;



    private static final String DATABASE_CREATE =
            "CREATE TABLE " +SQLITE_TABLE + "("
                    + KEY_HABIT + " TEXT PRIMARY KEY" +")";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public HabitsAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public HabitsAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createCountry(String habit) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_HABIT, habit);
        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllCountries() {
        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    void addListItem(ArrayList<String> listItem) {
        mDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < listItem.size(); i++) {
            Log.e("value inserting==", "" + listItem.get(i));
            values.put(KEY_HABIT, listItem.get(i));
            mDb.insert(SQLITE_TABLE, null, values);
        }
       mDb.close(); // Closing database connection
    }


    Cursor getListItem() {       String selectQuery = "SELECT  * FROM " + SQLITE_TABLE;
        mDb = mDbHelper.getWritableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        return cursor;
    }


    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_HABIT},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_HABIT},
                    KEY_HABIT+ " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllCountries() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_HABIT},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeCountries() {
        createCountry("Smile");
        createCountry("Walk");
        createCountry("Drink water");
        createCountry("Sleep");
    }

    public void add(String habit){
        ContentValues data = new ContentValues();
        data.put(KEY_HABIT, habit);
        mDb.insert(SQLITE_TABLE, null, data);
    }

}