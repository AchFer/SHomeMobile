/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.example.ahemdhammouda.malvoyants.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLiteHandlernoeud extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandlernoeud.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "Id";
    private static final String KEY_ID_CUI = "cuisine";
    private static final String KEY_ID_SAL = "salon";
    private static final String KEY_ID_BAIN = "saledebain";

    public SQLiteHandlernoeud(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ID_CUI + " TEXT,"
                + KEY_ID_SAL + " TEXT,"
                + KEY_ID_BAIN + " TEXT"
                + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String cuisine , String salon , String bain) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CUI, cuisine);
        values.put(KEY_ID_SAL, salon);
        values.put(KEY_ID_BAIN, bain);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.e(TAG, "New noeudddddddd: " + id + "salonid : "+salon+" cuisine : "+cuisine+" bain : "+bain);
    }


    /**
     * Getting user data from database
     * */
    public String getcuisid() {
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        String a = "";
        String b= "";
        String c= "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {


            a =  cursor.getString(1);

           // Log.e("ccccccc",cursor.getString(3));
           // Log.e("ssssssssss",cursor.getString(1));
           // Log.e("bbbbbbbbbbbbbb",cursor.getString(2));


        }
        cursor.close();
        db.close();
        // return user

        return  a;
    }

    public String getbainid() {
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        String a = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {


            a =  cursor.getString(3);

        }
        cursor.close();
        db.close();
        // return user

        return  a;
    }

    public String getsalid() {
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        String a = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {


            a =  cursor.getString(2);

        }
        cursor.close();
        db.close();
        // return user

        return  a;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
