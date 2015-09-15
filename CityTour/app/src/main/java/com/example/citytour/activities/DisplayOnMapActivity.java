package com.example.citytour.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citytour.JSONParser;
import com.example.citytour.R;
import com.example.citytour.core.CityTour;
import com.example.citytour.core.CityTourApi;
import com.example.citytour.core.CityTourUtils;
import com.example.citytour.models.Bar;
import com.example.citytour.tasks.ObtainRouteForMap;
import com.example.citytour.tasks.ProximityIntentReceiver;
import com.google.android.gms.location.LocationListener;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DisplayOnMapActivity extends FragmentActivity implements LocationListener{

	private final String TAG = DisplayOnMapActivity.class.getCanonicalName();
	private static ArrayList<LatLng> coordinates;
    private ProximityIntentReceiver receiver;
	ArrayList<Bar> bares;
	private GoogleMap mMap;
    private String[] route;
    private CameraPosition cameraPosition;
	Marker user;
	private static ArrayList<Marker> checkpoints;
	private static int numCheckpoints;
	private static int beenThere;
    private static int routeIndex;

    private static final int TAKE_PICTURE = 1;

	private Context mContext = this;

	private static final long POINT_RADIUS = 75; // in meters
	private static final long EXPIRATION = -1; // no expiration
	private static LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_on_map);

        new CityTourUtils(this);

        int id = 0;

		checkpoints = new ArrayList<>();
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		// get data from SharedPreferences
		SharedPreferences prefs = getSharedPreferences("com.example.citytour", Context.MODE_PRIVATE);
        int cityIndex = prefs.getInt("cityIndex", 0);
		routeIndex = prefs.getInt("routeIndex", 0);
        int timeIndex = prefs.getInt("timeIndex", 0);
		numCheckpoints = prefs.getInt("numCheckpoints", 0);
		beenThere = prefs.getInt("beenThere", 0);

		// get handle of the map fragment
		mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

		if(routeIndex==0){
            String[] coord1;
            String descriptionRoute;
            if(beenThere==0){
				mMap.clear();
				coord1 = b.getStringArray("coordinates");
				descriptionRoute = b.getString("description");
				route = descriptionRoute.split(", ");
				coordinates = CityTourUtils.getCoordinates(coord1);
				String[] zonas = getResources().getStringArray(R.array.array_zonas_madrid);
				String[] coord = getResources().getStringArray(R.array.array_coordinates);
				for (String aRoute : route) {
					for (int j = 0; j < zonas.length; j++) {
						if (aRoute.equals(zonas[j])) {
							LatLng latLng = CityTourUtils.getCoordinates(coord[j]);
							placeMarker(latLng, zonas[j], id);
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
			}else{
				if(beenThere==numCheckpoints-1){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
					alertDialogBuilder.setMessage(getResources().getString(R.string.gpsDisabled))
					.setCancelable(false)
					.setPositiveButton(getResources().getString(R.string.newRoute),
							new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id){
							Intent intent = new Intent(getBaseContext(), MainActivity.class);
							startActivity(intent);
							dialog.cancel();
						}
					});
					alertDialogBuilder.setNegativeButton(getResources().getString(R.string.quit),
							new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id){
							quit();
						}
					});
					AlertDialog alert = alertDialogBuilder.create();
					alert.show();
				}
				mMap.clear();
				String descriptionCoord = prefs.getString("checkpointCoordinates", "");
                assert descriptionCoord != null;
                if(!descriptionCoord.isEmpty()) {
                    coord1 = descriptionCoord.split(";");
                    coordinates = CityTourUtils.getCoordinates(coord1);
                }
				descriptionRoute = prefs.getString("routeCheckpoints", "");
                if(!descriptionRoute.isEmpty()) {
                    route = descriptionRoute.split(", ");
                }
				String[] zonas = getResources().getStringArray(R.array.array_zonas_madrid);
				String[] coord = getResources().getStringArray(R.array.array_coordinates);
				for (String aRoute : route) {
					for (int j = 0; j < zonas.length; j++) {
						if (aRoute.equals(zonas[j])) {
							LatLng latLng = CityTourUtils.getCoordinates(coord[j]);
							placeMarker(latLng, zonas[j], id);
							id++;
						}
					}
				}
				drawRoute(coordinates);
				cameraPosition = CameraPosition.builder()
						.target(getLastCheckpoint())
						.zoom(17)
						.bearing(90)
						.build();
			}
		}else if(routeIndex==1){
			mMap.clear();
            String coordBar = b.getString("coordinates");
            String nameBar = b.getString("bar");
            LatLng coordinatesBar = CityTourUtils.getCoordinates(coordBar);
			// aumentar id cuando sea ruta de bares y no uno solo
			placeMarker(coordinatesBar, nameBar, id);
			cameraPosition = CameraPosition.builder()
					.target(coordinatesBar)
					.zoom(17)
					.bearing(90)
					.build();

		}

		mMap.setMyLocationEnabled(true);

		// animate change in camera
		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);

		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
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
				note.setText(marker.getTitle());

				return v;
			}
		});

		IntentFilter filter = new IntentFilter(TAG);
		registerReceiver(receiver, filter);
		
	}

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new ProximityIntentReceiver();
        IntentFilter filter = new IntentFilter(TAG);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_on_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if (item.getItemId() == R.id.action_camera) {
			CityTourUtils.cameraClick();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	public void onInfoWindowClick(Marker marker){
		ArrayList<LatLng> coord = new ArrayList<>();
		Location userLocation = mMap.getMyLocation();
		LatLng src = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
		coord.add(src);
		LatLng dest = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
		coord.add(dest);
		drawPath(coord);
	}

	private void placeMarker(LatLng coordinates, String name, int id){
		Marker marker = mMap.addMarker(new MarkerOptions()
		.title(name)
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.gpsmap))
		.position(coordinates)
		.flat(true)
		.rotation(90));	
		checkpoints.add(marker);
		// get data from SharedPreferences
		SharedPreferences prefs = getSharedPreferences("com.example.citytour", Context.MODE_PRIVATE);
		String visitedCheckpoints = prefs.getString("visitedCheckpoints", "");
		String[] totalCheck = prefs.getString("routeCheckpoints", "").split(",");
        assert visitedCheckpoints != null;
        if(!visitedCheckpoints.isEmpty()){
			String[] check = visitedCheckpoints.split(",");
			for(int j=0; j<check.length; j++){
				Toast.makeText(getBaseContext(), "BEEN AT: "+check[j], Toast.LENGTH_SHORT).show();
				if((!totalCheck[j].equals(check[j]))&(!name.contains("Twin"))){
					addProximityAlert(coordinates.latitude, coordinates.longitude, id);
				}
			}
		}else{
			if((!name.contains("Twin"))&&(routeIndex==0)){
				addProximityAlert(coordinates.latitude, coordinates.longitude, id);
			}
		}
	}

	private void drawPath(String result) {

		try {
			//Tranform the string into a json object
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = ObtainRouteForMap.decodePoly(encodedString);

			for(int z = 0; z<list.size()-1;z++){
				LatLng src= list.get(z);
				LatLng dest= list.get(z+1);
				mMap.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude, dest.longitude))
						.width(8)
						.color(Color.BLUE).geodesic(true));
			}

		}catch (JSONException ignored) {

		}
	}

	private void drawRoute(ArrayList<LatLng> coordinates){
		for(int i = 0; i<coordinates.size()-1;i++){
			LatLng src= coordinates.get(i);
			LatLng dest= coordinates.get(i + 1);
			mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude, dest.longitude))
                    .width(8)
                    .color(Color.BLUE).geodesic(true));
		}
	}

	private void drawPath(ArrayList<LatLng> coordinates) {

		for(int i=0; i<coordinates.size()-1; i++){
			LatLng src= coordinates.get(i);
			LatLng dest= coordinates.get(i+1);
			String url = CityTourApi.googleMapsRouteURL(src.latitude, src.longitude, dest.latitude, dest.longitude);
			new connectAsyncTask(url).execute();
		}

	}

	@Override
	public void onLocationChanged(Location location) {
        LatLng userPosition = new LatLng(location.getLatitude(), location.getLongitude());
	}

	private class connectAsyncTask extends AsyncTask<Void, Void, String>{
		private ProgressDialog progressDialog;
		final String url;
		connectAsyncTask(String urlPass){
			url = urlPass;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(DisplayOnMapActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.waitCoord));
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}
		@Override
		protected String doInBackground(Void... params) {
			JSONParser jParser = new JSONParser();
			return jParser.getJSONFromUrl(url);
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
		Intent intent = new Intent(TAG);
		PendingIntent proximityIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		locationManager.addProximityAlert(lat, lng, POINT_RADIUS, EXPIRATION, proximityIntent);
	}

	public static int getNumCheckpoints(){
		return numCheckpoints;
	}

	public static int getBeenThere(){
		return beenThere;
	}

	public static ArrayList<Marker> getCheckpoints(){
		return checkpoints;
	}

		private LatLng getLastCheckpoint(){
		SharedPreferences prefs = getSharedPreferences("com.example.citytour", Context.MODE_PRIVATE);
		String[] coord = prefs.getString("lastCheckpoint", "").split(",");
			return new LatLng(Double.parseDouble(coord[0]),Double.parseDouble(coord[1]));
	}

	private void quit(){
		// shows Home screen
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	public void drawPath(List<LatLng> list) {
		for (int z = 0; z < list.size() - 1; z++) {
			LatLng src = list.get(z);
			LatLng dest = list.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
							.add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude, dest.longitude))
							.width(6)
							.color(Color.BLUE).geodesic(true)
			);
		}
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                CityTourUtils.grabImage();
            } catch (Exception ex){
                Toast toast = Toast.makeText(CityTour.getContext(), "Unable to grab image.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}