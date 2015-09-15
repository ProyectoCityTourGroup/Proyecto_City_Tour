package com.example.citytour.tasks;

import java.util.ArrayList;

import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.util.Log;

import com.example.citytour.core.CityTourUtils;
import com.example.citytour.fragments.FragmentHandler;
import com.example.citytour.R;
import com.example.citytour.activities.DisplayOnMapActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class ProximityIntentReceiver extends BroadcastReceiver{

	private int beenThere= DisplayOnMapActivity.getBeenThere();
    private static final String PROX_ALERT_INTENT = "ProximityAlert";
	private static final int NOTIFICATION = 1;

    @Override
	public void onReceive(Context context, Intent intent){
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);
        ArrayList<Marker> checkpoints = DisplayOnMapActivity.getCheckpoints();
		final Marker marker;
		if(entering){
			marker = checkpoints.get(beenThere);
            String title = marker.getTitle();
			LatLng position = marker.getPosition();
			
			createNotification(context, title);
			
			removeProximityAlert(beenThere, context);
			beenThere++;
			// save data into Shared Preferences
			SharedPreferences prefs = context.getSharedPreferences("com.example.citytour", Context.MODE_PRIVATE);
			String visitedCheckpoints = prefs.getString("visitedCheckpoints", "");
            assert visitedCheckpoints != null;
            if(visitedCheckpoints.isEmpty()){
				visitedCheckpoints = title;
			}else{
				visitedCheckpoints = visitedCheckpoints + "," + title;
			}
			String lastCheckpoint = String.valueOf(position.latitude)+","+String.valueOf(position.longitude);
			Editor editor = prefs.edit();
			editor.putInt("beenThere", beenThere);
			editor.putString("visitedCheckpoints", visitedCheckpoints);
			editor.putString("lastCheckpoint", lastCheckpoint);
			editor.apply();
            String url = getURL(context, title);
			
			CityTourUtils.quizzAndInfo(context, title, url);

		}else{
			Log.d("BYEBYE", "exiting");
		}
	}
	
	private void removeProximityAlert(int id, Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Intent intent = new Intent(PROX_ALERT_INTENT);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		locationManager.removeProximityAlert(pendingIntent);
		Log.d("REMOVE","ProximityAlert removed");
	}
	
	private void NotificationWithIntent(Context context, Integer id, String titulo, String contenido, Class<?> activity) {
 
        Builder builder = new Builder(context);
 
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(contenido)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true);
 
        Intent resultIntent = new Intent(context, FragmentHandler.class);
 
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(activity);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
 
        notificationManager.notify(id, builder.build());
    }
	
	private void createNotification(Context context, String name) {
		 
        NotificationWithIntent(context, NOTIFICATION, "Quizz", name, context.getClass());
 
    }
	
	private String getURL(Context context, String name){
		String[] checkpoints = context.getResources().getStringArray(R.array.array_zonas_madrid);
		String[] urls = context.getResources().getStringArray(R.array.array_url_zonas);
		String url = "";
		for(int i=0; i<checkpoints.length; i++){
			if(name.equals(checkpoints[i])){
				url = urls[i];
			}
		}
		return url;
	}
}
