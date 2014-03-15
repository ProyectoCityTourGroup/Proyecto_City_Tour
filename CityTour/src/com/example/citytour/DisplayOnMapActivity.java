package com.example.citytour;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DisplayOnMapActivity extends Activity {
	
	ProgressDialog pDialog;
	JSONParser jsonparser = new JSONParser();
	JSONObject jobjParse,jobjTitle,jobjText;
	GoogleMap map;
	private static final String TAG_TEXT = "text";
	private static final String TAG_PARSE = "parse";
	private static final String TAG_LAT = "latitude";
	private static final String TAG_LNG = "longitude";
	private static String DATA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_on_map);
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		String[] zonas = b.getStringArray("zonas");
//		// get handle of the map fragment
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		String[] urls = getURLS(zonas);
		new GetCoordinates().execute(urls);
		
		
//		
//		// animate change in camera
//		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),2000,null);
//		map.setMyLocationEnabled(true);
////		map.moveCamera(CameraUpdateFactory.newLatLngZoom(Madrid, 13));
////		
////		map.addMarker(new MarkerOptions()
////				.title("Madrid")
////				.snippet("Full of koalas")
////				.position(Madrid));
	}
	
	private String[] getURLS(String[] zonasSeleccionadas){
		String[] urls = getResources().getStringArray(R.array.array_url_zonas);
		String[] URLS = new String[zonasSeleccionadas.length];
		for(int i=0; i<urls.length; i++){
			for(int j=0; j<zonasSeleccionadas.length;j++){
				String[] aux = zonasSeleccionadas[j].split(" ");
				int L = 0;
				for(int k=0; k<aux.length;k++){
					if(urls[i].contains(aux[k])){
						L++;
					}
					if(L==aux.length){
						URLS[j] = urls[i];
					}
				}
			}
		}
		return URLS;
	}
	
	private class GetCoordinates extends AsyncTask<String,String[],JSONObject[]>{
		ArrayList<LatLng> latLngArray = new ArrayList<LatLng>();
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(DisplayOnMapActivity.this);
            pDialog.setMessage("Getting coordinates ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
		}
		
		@Override
		protected JSONObject[] doInBackground(String... args){
			JSONObject[] jobjParseString = new JSONObject[args.length];
			for(int i=0; i<args.length;i++){
				jobjParseString[i] = jsonparser.makeHttpRequest(args[i]);
			}
			return jobjParseString;
		}
		
		@Override
		protected void onPostExecute(JSONObject[] jobjParseString){
			for(int i=0; i<jobjParseString.length;i++){
				try {
					jobjParse = jobjParseString[i].getJSONObject(TAG_PARSE);
					jobjText = jobjParse.getJSONObject(TAG_TEXT);
					DATA = jobjText.toString();
					// LatLng
					String lat = DATA.substring(DATA.lastIndexOf(TAG_LAT)+11,DATA.lastIndexOf(TAG_LAT)+19);
					Log.d("LAT:",lat);
					String lng = DATA.substring(DATA.lastIndexOf(TAG_LNG)+12,DATA.lastIndexOf(TAG_LNG)+20);
					Log.d("LNG:",lng);
//					String latLng = lat+","+lng;
					LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
					latLngArray.add(latLng);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			pDialog.dismiss();
			for(int i=0; i<latLngArray.size();i++){
				map.addMarker(new MarkerOptions()
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.gpsmap))
					.position(latLngArray.get(i))
					.flat(true)
					.rotation(0));
			}
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngArray.get(0), 13));
			CameraPosition cameraPosition = CameraPosition.builder()
					.target(latLngArray.get(0))
					.zoom(13)
					.bearing(90)
					.build();
		}
	}
}