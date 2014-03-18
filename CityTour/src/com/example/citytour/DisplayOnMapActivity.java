package com.example.citytour;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class DisplayOnMapActivity extends Activity {
	ArrayList<LatLng> coordinates;
	ArrayList<Bar> bares;
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
		map.setMyLocationEnabled(true);
		
		CameraPosition cameraPosition = CameraPosition.builder()
				.target(coordinates.get(0))
				.zoom(13)
				.bearing(90)
				.build();
		
		// animate change in camera
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),2000,null);
		
		map.setInfoWindowAdapter(new InfoWindowAdapter(){
			// Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker marker) {              
                return null;
            }           

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.custom_info_window, null);

                // Getting reference to the TextView to set title
                TextView note = (TextView) v.findViewById(R.id.note);

                note.setText(marker.getTitle() );

                addListenerOnButton();
                // Returning the view containing InfoWindow contents
                return v;
            }
		});
		drawPath(coordinates);
	}
	
	public void addListenerOnButton() {
		 
		ImageButton imageButton = (ImageButton) findViewById(R.id.takeMeThere);
 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
			}
 
		});
 
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
			//bares y tapas
			coord = getResources().getStringArray(R.array.array_coordinates_bars);
			zonas = getResources().getStringArray(R.array.array_bares_madrid);
			bares = getBars(zonasSelec);
		}
		for(int i=0; i<zonasSelec.length;i++){
			if(tipoRecorrido==0){
				// cultural
				for(int j=0; j<zonas.length; j++){
					if(zonasSelec[i].equals(zonas[j])){
						String[] aux = coord[j].split(",");
						LatLng latLng = new LatLng(Double.parseDouble(aux[0]),Double.parseDouble(aux[1]));
						coordinates.add(latLng);
					}
				}
			}else if(tipoRecorrido==1){
				// bares y tapas
				for(int j=0; j<zonas.length; j++){
					int k = j/2;
					if(zonasSelec[i].equals(zonas[j])){
						String[] aux = coord[k].split(",");
						LatLng latLng = new LatLng(Double.parseDouble(aux[0]),Double.parseDouble(aux[1]));
						coordinates.add(latLng);
					}
				}
			}
			
		}
		return coordinates;
	}
	
	private ArrayList<Bar> getBars(String[] baresSelec){
		ArrayList<Bar> bares = new ArrayList<Bar>();
		String[] total = getResources().getStringArray(R.array.array_bares_madrid);
		for(int i=0; i<baresSelec.length; i++){
			for(int j=0; j<total.length-1;j++){
				if(baresSelec[i].equals(total[j])){
					Bar bar = new Bar(total[j], total[j+1]);
					bares.add(bar);
				}
			}
		}
		return bares;
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
	
	// make url para calcular la ruta entre dos puntos
	public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString( destlog));
        urlString.append("&sensor=false&mode=walking&alternatives=true");//mode=transit para transporte publico
        return urlString.toString();
	}
	
	public void drawPath(String  result) {

	    try {
	            //Tranform the string into a json object
	           final JSONObject json = new JSONObject(result);
	           JSONArray routeArray = json.getJSONArray("routes");
	           JSONObject routes = routeArray.getJSONObject(0);
	           JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
	           String encodedString = overviewPolylines.getString("points");
	           List<LatLng> list = decodePoly(encodedString);

	           for(int z = 0; z<list.size()-1;z++){
	                LatLng src= list.get(z);
	                LatLng dest= list.get(z+1);
	                map.addPolyline(new PolylineOptions()
	                	.add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
	                	.width(2)
	                	.color(Color.BLUE).geodesic(true));
	            }

	    } 
	    catch (JSONException e) {

	    }
	}
	
	public void drawPath(ArrayList<LatLng> coordinates) {

		for(int i=0; i<coordinates.size()-1; i++){
	    	LatLng src= coordinates.get(i);
            LatLng dest= coordinates.get(i+1);
            String url = makeURL(src.latitude,src.longitude,dest.latitude,dest.longitude);
            new connectAsyncTask(url).execute();
	    }

	}
	
	private List<LatLng> decodePoly(String encoded) {

	    List<LatLng> poly = new ArrayList<LatLng>();
	    int index = 0, len = encoded.length();
	    int lat = 0, lng = 0;

	    while (index < len) {
	        int b, shift = 0, result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lat += dlat;

	        shift = 0;
	        result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lng += dlng;

	        LatLng p = new LatLng( (((double) lat / 1E5)),
	                 (((double) lng / 1E5) ));
	        poly.add(p);
	    }

	    return poly;
	}
	
	private class connectAsyncTask extends AsyncTask<Void, Void, String>{
	    private ProgressDialog progressDialog;
	    String url;
	    connectAsyncTask(String urlPass){
	        url = urlPass;
	    }
	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	        progressDialog = new ProgressDialog(DisplayOnMapActivity.this);
	        progressDialog.setMessage(getResources().getString(R.string.waitCoord));
	        progressDialog.setIndeterminate(true);
	        progressDialog.show();
	    }
	    @Override
	    protected String doInBackground(Void... params) {
	        JSONParser jParser = new JSONParser();
	        String json = jParser.getJSONFromUrl(url);
	        return json;
	    }
	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);   
	        progressDialog.hide();        
	        if(result!=null){
	            drawPath(result);
	        }
	    }
	}
}