package com.edu.tarc.mylocation.DataSource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tarc on 3/14/2016.
 */
public class LocationSQLHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "location.db";
    private static final String SQL_CREATE = "CREATE TABLE " + LocationContract.Point.TABLE_NAME + "(" +
            LocationContract.Point.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LocationContract.Point.COLUMN_LAT + " REAL, " +
            LocationContract.Point.COLUMN_LON +  " REAL, " +
            LocationContract.Point.COLUMN_NAME +  " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LocationContract.Point.TABLE_NAME;

    public LocationSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
