package com.example.citytour;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
		final Context mContext = context;
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);
		hitos = DisplayOnMapActivity.getHitos();
		final Marker marker;
		if(entering){
			Log.d("UEUEUE", "entering");
			marker = hitos.get(yaHePasadoPorAqui);
			Log.d("MARKER NAME", marker.getTitle());
			createNotification(context, marker.getTitle());
			removeProximityAlert(yaHePasadoPorAqui, mContext);
			yaHePasadoPorAqui++;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
	 
			// set title
			alertDialogBuilder.setTitle(mContext.getResources().getString(R.string.estasEn)+" "+marker.getTitle());
	 
			// set dialog message
			alertDialogBuilder
				.setMessage(mContext.getResources().getString(R.string.quizzOinfo))
				.setCancelable(false)
				.setPositiveButton(mContext.getResources().getString(R.string.irAInfo),new DialogInterface.OnClickListener() {
					Context context = mContext;
					public void onClick(DialogInterface dialog,int id) {
						Intent intent = new Intent (context,InfoActivity.class);
						String url = getURL(context, marker.getTitle());
						intent.putExtra("url", url);
						context.startActivity(intent);
						dialog.cancel();
					}
				  })
				.setNegativeButton(mContext.getResources().getString(R.string.irAlQuizz),new DialogInterface.OnClickListener() {
					Context context = mContext;
					public void onClick(DialogInterface dialog,int id) {
						gotoQuizzActivity(context, marker.getTitle());
						dialog.cancel();
					}
				});
	 
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
	 		// show it
			alertDialog.show();
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
	
	private String getURL(Context context, String name){
		String[] hitos = context.getResources().getStringArray(R.array.array_zonas_madrid);
		String[] urls = context.getResources().getStringArray(R.array.array_url_zonas);
		String url = "";
		for(int i=0; i<hitos.length; i++){
			if(name.equals(hitos[i])){
				url = urls[i];
			}
		}
		return url;
	}
}
