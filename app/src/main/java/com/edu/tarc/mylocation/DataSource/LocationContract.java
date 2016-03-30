package com.edu.tarc.mylocation.DataSource;

import android.provider.BaseColumns;

/**
 * Created by Tarc on 3/14/2016.
 */
public final class LocationContract {

    public LocationContract() {
    }

    public static abstract class Point implements BaseColumns{
        public static final String TABLE_NAME = "location";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LON = "lon";
        public static final String COLUMN_NAME = "name";
    }
}
