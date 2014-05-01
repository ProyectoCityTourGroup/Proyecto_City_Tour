package com.example.citytour;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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


public class DisplayOnMapActivity extends Activity{
	ArrayList<LatLng> coordinates;
	public static LatLng coordinatesBar, userPosition;
	ArrayList<Bar> bares;
	GoogleMap map;
	String[] coord, route;
	String coordBar;
	String nameBar, descriptionRoute;
	CameraPosition cameraPosition;
	Marker user;
	public static ArrayList<Marker> checkpoints;
	public static int numCheckpoints, beenThere, cityIndex, tipoRecorrido, timeIndex;
	int id;

	private Context mContext = this;
	File tempPhoto;
	private Uri imageUri;
	String mCurrentPhotoPath;
	static final int TAKE_PICTURE = 1;

	private static final long POINT_RADIUS = 75; // in meters
	private static final long EXPIRATION = -1; // no expiration
	private static final String PROX_ALERT_INTENT = "com.example.citytour.ProximityActivity";
	private static LocationManager locationManager;
	private LocationListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_on_map);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		id = 0;
		checkpoints = new ArrayList<Marker>();
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				userPosition = new LatLng(location.getLatitude(),location.getLongitude());
			}
            // Other overrides are empty.
        };
		/* CAL METHOD requestLocationUpdates */

		// Parameters :
		//   First(provider)    :  the name of the provider with which to register 
		//   Second(minTime)    :  the minimum time interval for notifications, 
		//                         in milliseconds. This field is only used as a hint 
		//                         to conserve power, and actual time between location 
		//                         updates may be greater or lesser than this value. 
		//   Third(minDistance) :  the minimum distance interval for notifications, in meters 
		//   Fourth(listener)   :  a {#link LocationListener} whose onLocationChanged(Location) 
		//                         method will be called for each location update 


//		locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, (long)3000, (float)10, (LocationListener)listener);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		// get data from SharedPreferences
		SharedPreferences prefs = getSharedPreferences("com.example.citytour", Context.MODE_PRIVATE);
		cityIndex = prefs.getInt("cityIndex", 0);
		tipoRecorrido = prefs.getInt("routeIndex", 0);
		timeIndex = prefs.getInt("timeIndex", 0);
		numCheckpoints = prefs.getInt("numCheckpoints", 0);
		beenThere = prefs.getInt("beenThere", 0);

		// get handle of the map fragment
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

		if(tipoRecorrido==0){
			if(beenThere==0){
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
							id++;
						}
					}
				}
				drawRoute(coordinates);
				cameraPosition = CameraPosition.builder()
//						.target(getUserPosition())
						.target(coordinates.get(beenThere))
						.zoom(17)
						.bearing(90)
						.build();
			}else{
				if(beenThere==numCheckpoints){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
					alertDialogBuilder.setMessage(getResources().getString(R.string.gpsDisabled))
					.setCancelable(false)
					.setPositiveButton(getResources().getString(R.string.newRoute),
							new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int id){
							Intent intent = new Intent(getBaseContext(), SecondActivity.class);
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
				cameraPosition = CameraPosition.builder()
						.target(getUserPosition())
						.zoom(17)
						.bearing(90)
						.build();
			}
		}else if(tipoRecorrido==1){
			map.clear();
			coordBar = b.getString("coordinates");
			nameBar = b.getString("bar");
			coordinatesBar = getCoordinates(coordBar);
			// aumentar id cuando sea ruta de bares y no uno solo
			placeMarker(coordinatesBar, nameBar, id);
			cameraPosition = CameraPosition.builder()
					.target(getUserPosition())
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

		IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
		registerReceiver(new ProximityIntentReceiver(), filter);
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
			cameraClick();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
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
		checkpoints.add(marker);
		if((!name.contains("Twin"))&&(tipoRecorrido==0)){
			addProximityAlert(coordinates.latitude, coordinates.longitude, id);
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

	public static int getNumCheckpoints(){
		return numCheckpoints;
	}

	public static int getBeenThere(){
		return beenThere;
	}

	public static ArrayList<Marker> getCheckpoints(){
		return checkpoints;
	}

	public static LocationManager getLocationManager(){
		return locationManager;
	}

	public static LatLng getUserPosition(){
		return userPosition;
	}


	private void cameraClick(){
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			tempPhoto = createTemporaryFile("picture", ".jpg");
			if (tempPhoto != null) {
				tempPhoto.delete();
			}
		} catch (Exception e){
			Toast toast = Toast.makeText(mContext, "Unable to create temporary file.", Toast.LENGTH_SHORT);
			toast.show();
		}
		if (tempPhoto != null) {
			imageUri = Uri.fromFile(tempPhoto);
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

			if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
				startActivityForResult(takePictureIntent, TAKE_PICTURE);
			}
		} else {
			Toast toast = Toast.makeText(mContext, "Unable to create temporary file.", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private File createTemporaryFile(String part, String ext) throws Exception {
		if (isExternalStorageWritable()) {
			File tempDir = Environment.getExternalStorageDirectory();
			tempDir = new File(tempDir.getAbsolutePath()+"/.temp/");
			if(!tempDir.exists()) {
				tempDir.mkdir();
			}
			return File.createTempFile(part, ext, tempDir);
		} else {
			return null;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
			try {
				grabImage();
			} catch (Exception ex){
				Toast toast = Toast.makeText(this, "Unable to grab image.", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}

	public void grabImage() {
		this.getContentResolver().notifyChange(imageUri, null);
		ContentResolver cr = this.getContentResolver();
		Bitmap bitmap = null;
		try {
			bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, imageUri);
			createImageFile(bitmap);
		} catch (Exception e) {
			Toast toast = Toast.makeText(this, "Unable to create file.", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private void createImageFile(Bitmap bitmap) throws IOException {

		// Convert bitmap to JPEG and store it in a byte array
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
		byte[] bitmapdata = bytes.toByteArray();

		// Generate file name and directory paths
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
		String imageFileName = "JPEG_" + timeStamp + ".jpg";
		if (isExternalStorageWritable()){
			String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "My Activity";
			// Generate storage directory
			File storageDir = new File(path);
			if (!storageDir.exists()) {
				storageDir.mkdir();
			}

			// Create file to store the image
			File imageFile = new File(path + File.separator + imageFileName);
			imageFile.createNewFile();

			// Write image data to image file
			FileOutputStream output = new FileOutputStream(imageFile);
			output.write(bitmapdata);

			// Make image file visible from gallery
			mCurrentPhotoPath = "file:" + imageFile.getAbsolutePath();
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(mCurrentPhotoPath)));
			output.close();
		} else {
			Toast toast = Toast.makeText(this, "Unable to access external storage.", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}


	public void onProviderDisabled(String provider) {

		/******** Called when User off Gps *********/

		Toast.makeText(getBaseContext(), "GPS off ", Toast.LENGTH_LONG).show();
	}

	public void onProviderEnabled(String provider) {

		/******** Called when User on Gps  *********/

		//        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public void quit(){
		// shows Home screen
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}