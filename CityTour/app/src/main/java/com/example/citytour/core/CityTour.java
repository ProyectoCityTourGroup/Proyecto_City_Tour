package com.example.citytour.core;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alvaro on 14/09/15.
 */
public class CityTour extends Application {
    private static Context context;
    private static CityTour application;

    private static SQLiteOpenHelper dbHelper;
    private static SQLiteDatabase database;

    private static CityTourTracker tracker;

    public void onCreate(){
        super.onCreate();
        CityTour.context = getApplicationContext();
        CityTour.application = this;
        dbHelper = new CityTourDBHelper(this);
        database = dbHelper.getWritableDatabase();
        tracker = new CityTourTracker(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        dbHelper.close();
        dbHelper = null;
    }

    public static CityTour getApplication(){ return CityTour.application; }

    public static SQLiteDatabase getDatabaseInstance(){ return CityTour.database; }

    public static CityTourTracker getTracker(){ return CityTour.tracker; }

    public static Context getContext(){
        return CityTour.context;
    }

}
