package com.example.citytour.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.citytour.R;

public class MainActivity extends Activity {

	String[] city,route,duration;
	String pName = "";
	
	int cityIndex,routeIndex,timeIndex;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// checks if GPS is enabled
//		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//		boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//		if(!enabled){
//			showGPSDisabledAlertToUser();
//		}
		
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
		    	cityIndex = parentView.getSelectedItemPosition();
	            // storing string resources into Array
	            city = getResources().getStringArray(R.array.array_ciudades);
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
		    	routeIndex = parentView.getSelectedItemPosition();
	            // storing string resources into Array
	            route = getResources().getStringArray(R.array.array_recorridos);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // do nothing
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
		    	timeIndex = parentView.getSelectedItemPosition();
	            // storing string resources into Array
	            duration = getResources().getStringArray(R.array.array_duraciones);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
//	private void showGPSDisabledAlertToUser(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage(getResources().getString(R.string.gpsDisabled))
//        .setCancelable(false)
//        .setPositiveButton(getResources().getString(R.string.yes),
//                new DialogInterface.OnClickListener(){
//            public void onClick(DialogInterface dialog, int id){
//                Intent callGPSSettingIntent = new Intent(
//                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(callGPSSettingIntent);
//            }
//        });
//        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.no),
//                new DialogInterface.OnClickListener(){
//            public void onClick(DialogInterface dialog, int id){
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();
//    }
    
    public void goToSecondActivity(View view){
    	if(cityIndex!=0){
    		Toast.makeText(getBaseContext(), city[cityIndex]+" "+getResources().getString(R.string.notYet), Toast.LENGTH_SHORT).show();
    		return;
    	}
    	if(cityIndex==0&&routeIndex > 1){
    		Toast.makeText(getBaseContext(), "Recorrido "+ getResources().getString(R.string.notYet), Toast.LENGTH_SHORT).show();
    		return;
    	}
		Intent intent = new Intent(this, SecondActivity.class);
		// save data into Shared Preferences
		SharedPreferences prefs = getSharedPreferences("com.example.citytour", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("cityIndex", cityIndex);
		editor.putInt("routeIndex", routeIndex);
		editor.putInt("timeIndex", timeIndex);
		editor.apply();
		startActivity(intent);
	}
    
}
