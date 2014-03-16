package com.example.citytour;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DisplayOnMapActivity extends Activity {
	ArrayList<LatLng> coordinates;
	GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_on_map);
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		String[] zonas = b.getStringArray("zonas");
		int tipoRecorrido = b.getInt("tipoRecorrido");
		// get handle of the map fragment
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		coordinates = getCoordinates(zonas,tipoRecorrido);
		paintInMap(coordinates, zonas);
		CameraPosition cameraPosition = CameraPosition.builder()
				.target(coordinates.get(0))
				.zoom(13)
				.bearing(90)
				.build();
		
		// animate change in camera
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),2000,null);
		map.setMyLocationEnabled(true);
	}
	
	private ArrayList<LatLng> getCoordinates(String[] zonasSelec, int tipoRecorrido){
		ArrayList<LatLng> coordinates = new ArrayList<LatLng>();
		String[] coord = new String[zonasSelec.length];
		String[] zonas = new String[zonasSelec.length];
		if(tipoRecorrido==0){
			// cultural
			coord = getResources().getStringArray(R.array.array_coordinates);
			zonas = getResources().getStringArray(R.array.array_zonas_madrid);
		}else if(tipoRecorrido==1){
			//bares
			coord = getResources().getStringArray(R.array.array_coordinates_bars);
			zonas = getResources().getStringArray(R.array.array_bares_madrid);
		}
		for(int i=0; i<zonasSelec.length;i++){
			for(int j=0; j<zonas.length; j++){
				if(zonasSelec[i].equals(zonas[j])){
					String[] aux = coord[j].split(",");
					LatLng latLng = new LatLng(Double.parseDouble(aux[0]),Double.parseDouble(aux[1]));
					coordinates.add(latLng);
				}
			}
		}
		return coordinates;
	}
	
	private void paintInMap(ArrayList<LatLng> coordinates, String[] zonas){
		for(int i=0; i< coordinates.size(); i++){
			map.addMarker(new MarkerOptions()
				.title(zonas[i])
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.gpsmap))
				.position(coordinates.get(i))
				.flat(true)
				.rotation(90));
		}
	}
}