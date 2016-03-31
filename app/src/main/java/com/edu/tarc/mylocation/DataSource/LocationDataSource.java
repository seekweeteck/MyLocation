package com.edu.tarc.mylocation.DataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.edu.tarc.mylocation.DataClass.LocationPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarc on 3/14/2016.
 */
public class LocationDataSource {
    private SQLiteDatabase database;
    private LocationSQLHelper dbHelper;
    private String[] allColumn = {
            LocationContract.Point.COLUMN_ID,
            LocationContract.Point.COLUMN_LAT,
            LocationContract.Point.COLUMN_LON,
            LocationContract.Point.COLUMN_NAME};

    public LocationDataSource(Context context) {
        dbHelper = new LocationSQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }

    public void deleteLocation(){
        try{
            database= dbHelper.getWritableDatabase();
            database.execSQL("DELETE FROM " + LocationContract.Point.TABLE_NAME);
            database.close();
        }catch (Exception e){
            Log.d("Delete", e.getMessage());
        }

    }

    public void insertLocation(LocationPoint point){
        ContentValues values = new ContentValues();
        values.put(LocationContract.Point.COLUMN_LAT, point.getLatitude());
        values.put(LocationContract.Point.COLUMN_LON, point.getLongitude());
        values.put(LocationContract.Point.COLUMN_NAME, point.getName());
        database = dbHelper.getWritableDatabase();
        database.insert(LocationContract.Point.TABLE_NAME, null, values);
        database.close();
    }

    public List<LocationPoint> getAllLocations(){
        List<LocationPoint> records = new ArrayList<>();
        Cursor cursor = database.query(LocationContract.Point.TABLE_NAME, allColumn , null,
                null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            LocationPoint locationRecord = new LocationPoint();
            locationRecord.setId(cursor.getString(0));
            locationRecord.setLatitude(cursor.getFloat(1));
            locationRecord.setLongitude(cursor.getFloat(2));
            locationRecord.setName(cursor.getString(3));
            records.add(locationRecord);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }
}
