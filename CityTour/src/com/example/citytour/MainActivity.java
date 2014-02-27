package com.example.citytour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	String[] ciudad,recorrido,duracion;
	int indexCiudad,indexRecorrido,indexDuracion;
	LocationManager locationManager;
	LocationListener listenerCoarse, listenerFine;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Spinner de las ciudades
		Spinner spinnerCiudades = (Spinner) findViewById(R.id.spinnerCiudades);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterCiudades = ArrayAdapter.createFromResource(this,
		        R.array.array_ciudades, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterCiudades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerCiudades.setAdapter(adapterCiudades);
		spinnerCiudades.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        // your code here
		    	indexCiudad = parentView.getSelectedItemPosition();
                
	            // storing string resources into Array
	            ciudad = getResources().getStringArray(R.array.array_ciudades);
	                    
	            Toast.makeText(getBaseContext(), "You have selected : " +ciudad[indexCiudad], Toast.LENGTH_SHORT).show();
	            //Toast.makeText(getBaseContext(), "Index Ciudad : " +indexCiudad, Toast.LENGTH_SHORT).show();
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
		// Spinner de los recorridos
		Spinner spinnerRecorridos = (Spinner) findViewById(R.id.spinnerRecorridos);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterRecorridos = ArrayAdapter.createFromResource(this,
		        R.array.array_recorridos, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterRecorridos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerRecorridos.setAdapter(adapterRecorridos);
		spinnerRecorridos.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        // your code here
		    	indexRecorrido = parentView.getSelectedItemPosition();
                
	            // storing string resources into Array
	            recorrido = getResources().getStringArray(R.array.array_recorridos);
	                    
	            //Toast.makeText(getBaseContext(), "You have selected : " +recorrido[indexRecorrido], Toast.LENGTH_SHORT).show();
	            //Toast.makeText(getBaseContext(), "Index Recorrido : " +indexRecorrido, Toast.LENGTH_SHORT).show();
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
		// Spinner de las duraciones
		Spinner spinnerDuraciones = (Spinner) findViewById(R.id.spinnerDuraciones);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterDuraciones = ArrayAdapter.createFromResource(this,
		        R.array.array_duraciones, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterDuraciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerDuraciones.setAdapter(adapterDuraciones);
		spinnerDuraciones.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        // your code here
		    	indexDuracion = parentView.getSelectedItemPosition();
                
	            // storing string resources into Array
	            duracion = getResources().getStringArray(R.array.array_duraciones);
	                    
	            //Toast.makeText(getBaseContext(), "You have selected : " +duracion[indexDuracion], Toast.LENGTH_SHORT).show();
	            //Toast.makeText(getBaseContext(), "Index Duracion : " +indexDuracion, Toast.LENGTH_SHORT).show();
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		locationSetup();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void showPzaEspañaPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.pzaEspaña);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	public void showMuseoReinaSofiaPage(View view){
		Intent intent = new Intent(this, InfoActivity.class);
		String url = getString(R.string.museoReinaSofia);
		intent.putExtra("url", url);
		startActivity(intent);
	}

	 private void locationSetup(){
		Toast.makeText(getBaseContext(), "Starting Location Setup", Toast.LENGTH_SHORT).show();
	        try{
	            // set the location manager
	         locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	         if(locationManager != null){
	             locationManager.removeUpdates(listenerCoarse);
	             locationManager.removeUpdates(listenerFine);
	        }

	         // Initialize fine criteria for location providers
	         Criteria fine = new Criteria(); 
	         fine.setAccuracy(Criteria.ACCURACY_FINE); 
	         fine.setAltitudeRequired(false); 
	         fine.setBearingRequired(false); 
	         fine.setSpeedRequired(true); 
	         fine.setCostAllowed(true); 
	            
	         // Initialize coarse criteria for location providers.
	         Criteria coarse = new Criteria(); 
	         coarse.setAccuracy(Criteria.ACCURACY_COARSE); 

	         fine.setPowerRequirement(Criteria.POWER_HIGH); 
	         coarse.setPowerRequirement(Criteria.POWER_LOW); 
	            
	         // set gps update distance & time
	         int GPS_TIMEUPDATE = 1500; // update gps every 1.5sec
	         int GPS_DISTANCEUPDATE = 7; // update gps every 7m

	         // get the last known location
	         String provider = locationManager.getBestProvider(coarse, true);
	         Location location = locationManager.getLastKnownLocation(provider); // your initial location
	         
	         // setup your listener
	         if (listenerFine == null || listenerCoarse == null){ createLocationListeners(); }

	         // Will keep updating about every GPS_TIMEUPDATE ms until accuracy is 
	         // about GPS_DISTANCEUPDATE meters to get quick fix.
	         if(listenerCoarse != null){
	        	 locationManager.requestLocationUpdates(locationManager.getBestProvider(coarse, true),GPS_TIMEUPDATE,GPS_DISTANCEUPDATE,listenerCoarse);}
	            
	         // Will keep updating about every GPS_TIMEUPDATE ms until accuracy is 
	         // about GPS_DISTANCEUPDATE meters to get accurate fix.
	         if(listenerFine != null){locationManager.requestLocationUpdates(locationManager.getBestProvider(fine, true),GPS_TIMEUPDATE,GPS_DISTANCEUPDATE,listenerFine);}
	            
	        }catch(Exception e){
	         e.printStackTrace();
	        }
	        Toast.makeText(getBaseContext(), "Finishinging Location Setup", Toast.LENGTH_SHORT).show();
	    }
	
	public void mostrarAvisoGpsDeshabilitado(){
		Toast.makeText(getBaseContext(), R.string.GPSdeshabilitado, Toast.LENGTH_SHORT).show();
	}
	
	public void goToMap(){
		Intent intent = new Intent(this, MapActivity.class);
		String latitude = getString(R.string.latitude);
		intent.putExtra("latitude", latitude);
		startActivity(intent);
	}
	
	 /**
     *   Creates LocationListeners
     */
    private void createLocationListeners() {

     listenerFine = new LocationListener() {
         public void onStatusChanged(String provider, int status, Bundle extras) {}
         public void onProviderEnabled(String provider) {}
         public void onProviderDisabled(String provider) {}
         public void onLocationChanged(Location location) {
             if (location.getAccuracy() > 500 && location.hasAccuracy()){
                 locationManager.removeUpdates(listenerFine);              
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
                 locationManager.removeUpdates(listenerCoarse);              
             }else{
                 // do something with your location update
             }

         }
     };
    }

}
