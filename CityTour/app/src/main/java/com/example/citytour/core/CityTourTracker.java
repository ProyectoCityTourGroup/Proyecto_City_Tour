package com.example.citytour.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by alvaro on 14/09/15.
 */
public class CityTourTracker extends Service implements LocationListener {

    private static final String TAG = CityTourTracker.class.getCanonicalName();

    private static final long MIN_TIME = 100;
    private static final long MIN_DISTANCE = 1;

    private static final double DEFAULT_LAT = 40.4169473, DEFAULT_LNG = -3.7035285;

    private static final Location DEFAULT_LOCATION = new Location("DEFAULT");

    static {
        DEFAULT_LOCATION.setLatitude(DEFAULT_LAT);
        DEFAULT_LOCATION.setLongitude(DEFAULT_LNG);
    }

    private final Context context;
    private Location location;
    private boolean isUserLocated;

    CityTourTracker(Context context){
        this.context = context;
        this.location = null;
        this.isUserLocated = false;
        getLocation();
    }

    // default constructor para poder declarar el servicio en el manifiesto
    public CityTourTracker(){
        context = null;
    }

    private Location getLocation() {

        if (this.isUserLocated) return this.location;

        this.location = getLocationOrNull();
        this.isUserLocated = location != null;
        this.location = isUserLocated ? location : DEFAULT_LOCATION;

        Log.d(TAG, "currentPositionRequest=" + location);
        return location; //this is never null
    }

    private Location getLocationOrNull() {
        Location result = null;
        LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled || isNetworkEnabled) {
            if (isNetworkEnabled) {
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                result = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (isGPSEnabled && (result == null || result.getLatitude() == 0.0 || result.getLongitude() == 0.0)) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                result = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        return result;
    }

    public LatLng getLatLng(){
        Location location = this.getLocation();
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            this.location = location;
            this.isUserLocated = true;
            Log.d(TAG, "locationChanged="+location.toString());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
