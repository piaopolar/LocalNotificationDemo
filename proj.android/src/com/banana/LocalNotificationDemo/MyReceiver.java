package com.banana.LocalNotificationDemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class MyReceiver extends BroadcastReceiver {
	public static final String TAG = "MyReceiver";
	public static final int TYPE_NOTIFY = 1;

	public static final String TYPE_KEY = "TYPE_KEY";
	public static final String TITLE_KEY = "TITLE_KEY";
	public static final String BODY_KEY = "BODY_KEY";

	@Override
	public void onReceive(Context context, Intent intent) {
		int nType = intent.getIntExtra(TYPE_KEY, 0);
		Log.i(TAG, "onReceive Type " + nType);
		if (TYPE_NOTIFY == nType) {

			String strTitle = intent.getStringExtra(TITLE_KEY);
			String strBody = intent.getStringExtra(BODY_KEY);
			
			PendingIntent pending = PendingIntent.getActivity(
					context, 0,
					new Intent(context, LocalNotificationDemo.class), 0);
			
	        Notification notification;
	        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(context);
	        notiBuilder.setContentIntent(pending);
	        notiBuilder.setContentTitle(strTitle);
	        notiBuilder.setContentText(strBody);
	        notiBuilder.setSmallIcon(R.drawable.icon);
	        notiBuilder.setAutoCancel(true);
	        notiBuilder.setWhen(System.currentTimeMillis());
        	notification = notiBuilder.build();
			
			NotificationManager NM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			NM.notify(0, notification);					
		}
	}

}
