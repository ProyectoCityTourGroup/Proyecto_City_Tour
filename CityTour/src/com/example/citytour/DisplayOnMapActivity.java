package com.example.citytour;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class DisplayOnMapActivity extends Activity{
	ArrayList<LatLng> coordinates;
	LatLng coordinatesBar, userPosition;
	ArrayList<Bar> bares;
	GoogleMap map;
	String[] coord, route;
	String coordBar;
	String nameBar, descriptionRoute;
	CameraPosition cameraPosition;
	Marker user;
	public static ArrayList<Marker> hitos;
	public static int numHitos, yaHePasadoPorAqui;
	int id;
    
//    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATE = 1; // in meters
//	private static final long MINIMUM_TIME_BETWEEN_UPDATE = 1000; // in miliseconds
	private static final long POINT_RADIUS = 75; // in meters
	private static final long EXPIRATION = -1; // no expiration
	private static final String PROX_ALERT_INTENT = "com.example.citytour.ProximityActivity";
	private static LocationManager locationManager;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_on_map);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		numHitos = 0;
		yaHePasadoPorAqui = 0;
		id = 0;
		hitos = new ArrayList<Marker>();
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		int tipoRecorrido = b.getInt("tipoRecorrido");
		
		// get handle of the map fragment
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		if(tipoRecorrido==0){
			map.clear();
			coord = b.getStringArray("coordinates");
			descriptionRoute = b.getString("description");
			route = descriptionRoute.split(", ");
			coordinates = getCoordinates(coord);
			String[] zonas = getResources().getStringArray(R.array.array_zonas_madrid);
			String[] coord = getResources().getStringArray(R.array.array_coordinates);
			for(int i=0; i<route.length; i++){
				for(int j=0; j<zonas.length; j++){
					if(route[i].equals(zonas[j])){
						LatLng latLng = getCoordinates(coord[j]);
						placeMarker(latLng, zonas[j], id);
						numHitos++;
						id++;
					}
				}
			}
			drawRoute(coordinates);
			cameraPosition = CameraPosition.builder()
					.target(coordinates.get(0))
					.zoom(17)
					.bearing(90)
					.build();
			
		}else if(tipoRecorrido==1){
			map.clear();
			coordBar = b.getString("coordinates");
			nameBar = b.getString("bar");
			coordinatesBar = getCoordinates(coordBar);
			// aumentar id cuando sea ruta de bares y no uno solo
			placeMarker(coordinatesBar, nameBar, id);
			cameraPosition = CameraPosition.builder()
					.target(coordinatesBar)
					.zoom(17)
					.bearing(90)
					.build();
		}
		
		map.setMyLocationEnabled(true);
		
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

                return v;
            }
		});
		map.setOnMapClickListener(new OnMapClickListener(){
			@Override
			public void onMapClick(LatLng point) {
	            placeUser(point);
	        }
		});
		
		IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
		registerReceiver(new ProximityIntentReceiver(), filter);
	}
	
	private void placeUser(LatLng position){
		if (user!=null){
			user.remove();
		}
		user = map.addMarker(new MarkerOptions()
				.title("yo")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_directions))
				.position(position)
				.flat(true)
				.rotation(90));	
	}
	
	public void onInfoWindowClick(Marker marker){
		ArrayList<LatLng> coord = new ArrayList<LatLng>();
		Location userLocation = map.getMyLocation();
		LatLng src = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
		coord.add(src);
		LatLng dest = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
		coord.add(dest);
		drawPath(coord);
	}
	
	public void addListenerOnButton() {
		 
		ImageButton imageButton = (ImageButton) findViewById(R.id.takeMeThere);
 
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				// calcular ruta hasta ahí
			}
		});
 
	}
	
	private ArrayList<LatLng> getCoordinates(String[] coordinates){
		ArrayList<LatLng> coord = new ArrayList<LatLng>();
		for(int i=0; i<coordinates.length; i++){
			String[] aux = coordinates[i].split(",");
			LatLng latLng = new LatLng(Double.parseDouble(aux[0]), Double.parseDouble(aux[1]));
			coord.add(latLng);
		}
		return coord;
	}
	
	private LatLng getCoordinates(String coordinates){
		String[] aux = coordinates.split(",");
		LatLng latLng = new LatLng(Double.parseDouble(aux[0]), Double.parseDouble(aux[1]));
		return latLng;
	}
	
	
	private void placeMarker(LatLng coordinates, String name, int id){
		Marker marker = map.addMarker(new MarkerOptions()
			.title(name)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.gpsmap))
			.position(coordinates)
			.flat(true)
			.rotation(90));	
		Log.d("HITO", name+" added to map");
		hitos.add(marker);
		addProximityAlert(coordinates.latitude, coordinates.longitude, id);
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
	
	public void drawRoute(ArrayList<LatLng> coordinates){
		for(int i = 0; i<coordinates.size()-1;i++){
            LatLng src= coordinates.get(i);
            LatLng dest= coordinates.get(i+1);
            map.addPolyline(new PolylineOptions()
            	.add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
            	.width(8)
            	.color(Color.BLUE).geodesic(true));
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

	// Proximity alert
	private void addProximityAlert(double lat, double lng, int id){
		Intent intent = new Intent(PROX_ALERT_INTENT);
		PendingIntent proximityIntent = PendingIntent.getBroadcast(this, id, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		locationManager.addProximityAlert(lat, lng, POINT_RADIUS, EXPIRATION, proximityIntent);
	}
	
	public static int getNumHitos(){
		return numHitos;
	}
	
	public static int getYaHePasadoPorAqui(){
		return yaHePasadoPorAqui;
	}
	
	public static ArrayList<Marker> getHitos(){
		return hitos;
	}
	
	public static LocationManager getLocationManager(){
		return locationManager;
	}
	
}