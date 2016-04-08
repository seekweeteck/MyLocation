package com.edu.tarc.mylocation.DataClass;

import android.app.Application;

/**
 * Created by TARC on 4/7/2016.
 */
public class ConstantValues extends Application{
    public static long UPDATE_INTERVAL = 2000;
    public static float UPDATE_DISTANCE = 5;

    private static ConstantValues constantValues;
    
    public static ConstantValues getConstantValues() {
        return constantValues;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        constantValues = this;
    }
}
