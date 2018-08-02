package com.example.aksha.gjusteve.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aksha.gjusteve.POJO.DataObjectHorizontal;

import java.util.ArrayList;

public class EventSQLite extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "gjust_createria";

    // Contacts table name
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_PAST = "pastevents";
    private static final String TABLE_FEEDBACK = "feedback";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    //private static final String KEY_DATE = "date";
    private static final String KEY_URL = "url";
    // --Commented out by Inspection (8/2/2018 7:23 PM):private static final String KEY_LIKE = "likes";

    public EventSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_TITLE+" TEXT,"+KEY_URL+" TEXT,"+KEY_DESCRIPTION
                +" TEXT"+")";
        String CREATE_PAST_TABLE = "CREATE TABLE "+ TABLE_PAST+"("+KEY_ID+" TEXT PRIMARY KEY"+")";
        String CREATE_FEEDBACK_TABLE = "CREATE TABLE "+ TABLE_FEEDBACK+"("+KEY_ID+" TEXT PRIMARY KEY"+")";
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_PAST_TABLE);
        db.execSQL(CREATE_FEEDBACK_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PAST);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FEEDBACK);
        // Create tables again
        onCreate(db);
    }

    public void addEvent(String id, String title, String description, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,id);
        values.put(KEY_DESCRIPTION,description);
        values.put(KEY_TITLE,title);
        values.put(KEY_URL,url);
        db.insert(TABLE_EVENTS,null,values);
        db.close();
    }

    public void addLike(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("Database",DATABASE_NAME);
        ContentValues values = new ContentValues();
        values.put(KEY_ID,uid);
        db.insert(TABLE_PAST,null,values);

        db.close();
    }

    public void deleteLike(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PAST+ " WHERE id = "+"'"+ uid + "'");
        db.close();
    }

    public void addUpvote(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("Database",DATABASE_NAME);
        ContentValues values = new ContentValues();
        values.put(KEY_ID,uid);
        db.insert(TABLE_FEEDBACK,null,values);

        db.close();
    }

// --Commented out by Inspection START (8/2/2018 7:23 PM):
//    public void deleteUpvote(String uid){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_FEEDBACK+ " WHERE id = "+"'"+ uid + "'");
//        db.close();
//    }
// --Commented out by Inspection STOP (8/2/2018 7:23 PM)

    public ArrayList<String> getUpvote(){
        ArrayList<String> like = new ArrayList<>();
        String selectQuery ="SELECT * FROM "+ TABLE_FEEDBACK;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery,null);
            try{
                if (cursor.moveToFirst()){
                    do{
                        String likeCheck = cursor.getString(0);
                        like.add(likeCheck);
                        Log.e("Likw",likeCheck);
                    }while (cursor.moveToNext());
                }
            }finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }
        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }
        return like;
    }
    public void deleteEvent(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_EVENTS+ " WHERE id = "+"'"+ id + "'");
        db.close();

    }

    public ArrayList<String> getImpEvents() {
        ArrayList<String> user = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        if (cursor.moveToFirst()) {
            do {
                user.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }

    public ArrayList<String> getLike(){
        ArrayList<String> like = new ArrayList<>();
        String selectQuery ="SELECT * FROM "+ TABLE_PAST;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery,null);
            try{
                if (cursor.moveToFirst()){
                    do{
                        String likeCheck = cursor.getString(0);
                        like.add(likeCheck);
                        Log.e("Likw",likeCheck);
                    }while (cursor.moveToNext());
                }
            }finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }
        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }
        return like;
    }

    public ArrayList<DataObjectHorizontal> getAllElements() {

        ArrayList<DataObjectHorizontal> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        DataObjectHorizontal obj = new DataObjectHorizontal(cursor.getString(0),cursor.getString(1)
                                ,cursor.getString(3),cursor.getString(2));
                        //only one column

                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }
}
