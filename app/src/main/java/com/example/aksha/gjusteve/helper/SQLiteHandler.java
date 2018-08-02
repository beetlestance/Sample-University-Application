package com.example.aksha.gjusteve.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "gjust_creteria";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_PROFILEIMAGE = "url";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLL = "roll";
    private static final String KEY_COURSE = "course";
    private static final String KEY_PHONE = "phone";
    private SQLiteDatabase db;

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_ROLL + " INTEGER PRIMARY KEY,"
                + KEY_COURSE + " TEXT," +KEY_PHONE +" TEXT,"+KEY_PROFILEIMAGE+ " TEXT"+")";
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
    public void addUser(String name, String email, String roll, String course,String phone, String url) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_ROLL, roll); // Email
        values.put(KEY_COURSE, course); // Created At
        values.put(KEY_PHONE,phone); //Phone
        values.put(KEY_PROFILEIMAGE, url);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        //Log.d(TAG, String.format("New user inserted into sqlite: %d", id));
    }


    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        String userName;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(0));
            user.put("email", cursor.getString(1));
            user.put("roll", cursor.getString(2));
            user.put("course", cursor.getString(3));
            user.put("phone",cursor.getString(4));
            user.put("url",cursor.getString(5));
        }
        cursor.close();
        db.close();
        // return user
        // return user
        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void updateUser(String name, String email, String roll, String course, String phone) {

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("roll", roll);
        contentValues.put("course", course);
        contentValues.put("phone", phone);
        db.update(TABLE_USER, contentValues, null, null);
        db.close();
    }

}