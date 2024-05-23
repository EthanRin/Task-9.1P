package com.example.extendedlostfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatebase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "LostAndFound.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "AdvertListings";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_STATUS = "advert_status";
    private static final String COLUMN_TITLE = "advert_name";
    private static final String COLUMN_PHONE = "advert_phone";
    private static final String COLUMN_DESCRIPTION = "advert_desc";
    private static final String COLUMN_LAT = "advert_latitude";
    private static final String COLUMN_LNG = "advert_longitude";
    private static final String COLUMN_DATE = "advert_date";
    MyDatebase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                COLUMN_STATUS + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_LAT + " TEXT, " +
                COLUMN_LNG + " TEXT, " +
                COLUMN_DATE + " TEXT DEFAULT (strftime('%Y-%m-%d', 'now')))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addAdvert(String status, String title, String phone, String description, String date, String lat, String lng){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_LAT, lat);
        values.put(COLUMN_LNG, lng);
        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1){
            Toast.makeText(context, "Insert Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Insert Successful", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id = ?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
        }
    }
}
