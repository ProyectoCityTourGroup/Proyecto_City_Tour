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
import android.util.Log;

import com.google.android.gms.maps.model.Marker;

public class ProximityIntentReceiver extends BroadcastReceiver{

	int numHitos= DisplayOnMapActivity.getNumHitos(), yaHePasadoPorAqui= DisplayOnMapActivity.getYaHePasadoPorAqui();;
	ArrayList<Marker> hitos;
	LocationManager locationManager;
	private static final String PROX_ALERT_INTENT = "com.example.citytour.ProximityActivity";
	private static final int NOTIFICACION_1 = 1;


	@Override
	public void onReceive(Context context, Intent intent){
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);
		hitos = DisplayOnMapActivity.getHitos();
		Marker marker;
		if(entering){
			Log.d("UEUEUE", "entering");
			marker = hitos.get(yaHePasadoPorAqui);
			Log.d("MARKER NAME", marker.getTitle());
			createNotification(context, marker.getTitle());
			removeProximityAlert(yaHePasadoPorAqui, context);
			yaHePasadoPorAqui++;
			gotoQuizzActivity(context, marker.getTitle());
		}else{
			Log.d("BYEBYE", "exiting");
		}
	}

	public void gotoQuizzActivity(Context context, String name){
    	Intent intent = new Intent(context, QuizzActivity.class);
    	intent.putExtra("hito", name);
    	context.startActivity(intent);
    }
	
	private void removeProximityAlert(int id, Context context){
//		locationManager = DisplayOnMapActivity.getLocationManager();
		locationManager = DisplayOnMapActivity.getLocationManager();
		Intent intent = new Intent(PROX_ALERT_INTENT);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		locationManager.removeProximityAlert(pendingIntent);
		Log.d("REMOVE","ProximityAlert removed");
	}
	
	/*
	 * http://alessandrycruz.wordpress.com/2014/01/20/crear-una-notificacion-con-pending-intent-en-android/
	 */
	public void NotificacionConIntent(Context context, Integer id, String titulo, String contenido, Class<?> actividad) {
 
        Builder builder = new Builder(context);
 
        // Configuración de la Notificación
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(contenido)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
 
        // Actividad a Abrir
        Intent resultIntent = new Intent(context, actividad);
 
        // Evita que Cree Nuevamente la Misma Actividad
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(actividad);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
 
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
 
        // Crea la Notificación
        notificationManager.notify(id, builder.build());
    }
	
	public void createNotification(Context context, String name) {
		 
        NotificacionConIntent((Activity)context, NOTIFICACION_1, "Quizz",
                name, context.getClass());
 
    }
}
