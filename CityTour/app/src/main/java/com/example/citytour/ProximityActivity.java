package com.example.citytour;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ProximityActivity extends Activity {
	String notificationTitle;
	String notificationContent;
	String tickerMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean proximity_entering = getIntent().getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);
		if(proximity_entering){
			Toast.makeText(getBaseContext(),"Entering the region",Toast.LENGTH_LONG).show();
			notificationTitle="Proximity - Entry";
			notificationContent="Entered the region";
			tickerMessage = "Entered the region";
		}else{
			Toast.makeText(getBaseContext(),"Exiting the region",Toast.LENGTH_LONG).show();
			notificationTitle="Proximity - Exit";
			notificationContent="Exited the region";
			tickerMessage = "Exited the region";
		}
		Intent notificationIntent = new Intent(getApplicationContext(),NotificationView.class);
		notificationIntent.putExtra("content", notificationContent );

		/** This inputStream needed to make this intent different from its previous intents */
		notificationIntent.setData(Uri.parse("tel:/"+ (int)System.currentTimeMillis()));

		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationManager nManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
			.setWhen(System.currentTimeMillis())
			.setContentText(notificationContent)
			.setContentTitle(notificationTitle)
			.setSmallIcon(R.drawable.ic_launcher)
			.setAutoCancel(true)
			.setTicker(tickerMessage)
			.setContentIntent(pendingIntent);

		Notification notification = notificationBuilder.build();
		nManager.notify((int)System.currentTimeMillis(), notification);

		this.finish();
	}
}
