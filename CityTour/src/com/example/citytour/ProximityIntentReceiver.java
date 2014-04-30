package com.example.citytour;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;

public class ProximityIntentReceiver extends BroadcastReceiver{

	int numCheckpoints= DisplayOnMapActivity.getNumCheckpoints(), yaHePasadoPorAqui= DisplayOnMapActivity.getYaHePasadoPorAqui();;
	ArrayList<Marker> checkpoints;
	LocationManager locationManager;
	private static final String PROX_ALERT_INTENT = "com.example.citytour.ProximityActivity";
	private static final int NOTIFICACION_1 = 1;
	private String title, url;

	@Override
	public void onReceive(Context context, Intent intent){
		final Context mContext = context;
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);
		checkpoints = DisplayOnMapActivity.getCheckpoints();
		final Marker marker;
		if(entering){
			Log.d("UEUEUE", "entering");
			marker = checkpoints.get(yaHePasadoPorAqui);
			title = marker.getTitle();
			Log.d("MARKER NAME", title);
			createNotification(context, title);
			removeProximityAlert(yaHePasadoPorAqui, mContext);
			yaHePasadoPorAqui++;
			url = getURL(context, title);
			gotoCheckpoint(context, title, url);

		}else{
			Log.d("BYEBYE", "exiting");
		}
	}
	
	public void gotoCheckpoint(Context context, String name, String url){
    	Intent intent = new Intent(context, FragmentHandler.class);
    	Bundle extras = new Bundle();
    	extras.putString("checkpoint", name);
    	extras.putString("url", url);
    	intent.putExtras(extras);
    	context.startActivity(intent);
    }
	
	private void removeProximityAlert(int id, Context context){
		locationManager = DisplayOnMapActivity.getLocationManager();
		Intent intent = new Intent(PROX_ALERT_INTENT);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		locationManager.removeProximityAlert(pendingIntent);
		Log.d("REMOVE","ProximityAlert removed");
	}
	
	/*
	 * http://alessandrycruz.wordpress.com/2014/01/20/crear-una-notificacion-con-pending-intent-en-android/
	 */
	public void NotificationWithIntent(Context context, Integer id, String titulo, String contenido, Class<?> activity) {
 
        Builder builder = new Builder(context);
 
        // Notification's configuration
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(contenido)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
 
        // Activity to open
        Intent resultIntent = new Intent(context, activity);
 
        // This avoids creating a new instance of the activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(activity);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
 
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
 
        // Creates notification
        notificationManager.notify(id, builder.build());
    }
	
	public void createNotification(Context context, String name) {
		 
        NotificationWithIntent((Activity)context, NOTIFICACION_1, "Quizz",
                name, context.getClass());
 
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
