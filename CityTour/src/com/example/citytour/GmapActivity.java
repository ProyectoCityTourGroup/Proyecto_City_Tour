package com.example.citytour;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class GmapActivity extends MapActivity {

	private MapView mapa = null;
	
	private LocationManager locManager;
	private LocationListener listenerFine,listenerCoarse;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap);
 
        //Obtenemos una referencia al control MapView
        mapa = (MapView)findViewById(R.id.mapa);
 
        //Mostramos los controles de zoom sobre el mapa
        mapa.setBuiltInZoomControls(true);
    }
 
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    /**
     *   Creates LocationListeners
     */
    private void createLocationListeners() {
    	// https://sites.google.com/site/androiddevelopmentproject/home/rf-signal-tracker/how-to-setup-the-gps-a-very-basic-tutorial
     listenerFine = new LocationListener() {
         public void onStatusChanged(String provider, int status, Bundle extras) {}
         public void onProviderEnabled(String provider) {}
         public void onProviderDisabled(String provider) {}
         public void onLocationChanged(Location location) {
             if (location.getAccuracy() > 500 && location.hasAccuracy()){
                 locManager.removeUpdates(listenerFine);              
             }else{
                 // do something with your location update
             }
         }
     };

     listenerCoarse = new LocationListener() {
         public void onStatusChanged(String provider, int status, Bundle extras) {}
         public void onProviderEnabled(String provider) {}
         public void onProviderDisabled(String provider) {}
         public void onLocationChanged(Location location) {
             if (location.getAccuracy() < 500 && location.hasAccuracy()){
                 locManager.removeUpdates(listenerCoarse);              
             }else{
                 // do something with your location update
             }

         }
     };
    }

}
