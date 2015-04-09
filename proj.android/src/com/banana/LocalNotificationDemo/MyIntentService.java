package com.banana.LocalNotificationDemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class MyIntentService extends Service {
	public static final String TAG = "MyIntentService";

	private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
    	MyIntentService getService() {
            return MyIntentService.this;
        }
    }	
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {  
		int nType = intent.getIntExtra(MyReceiver.TYPE_KEY, 0);
		Log.i(TAG, "onHandleIntent Type " + nType);
		if (MyReceiver.TYPE_NOTIFY == nType) {
			Intent broadcastIntent = new Intent("android.intent.action.MY_BROADCAST_LND");
			broadcastIntent.putExtra(MyReceiver.TYPE_KEY, MyReceiver.TYPE_NOTIFY);
			broadcastIntent.putExtra(MyReceiver.TITLE_KEY, intent.getStringExtra(MyReceiver.TITLE_KEY));
			broadcastIntent.putExtra(MyReceiver.BODY_KEY, intent.getStringExtra(MyReceiver.BODY_KEY));

			PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);

			// Schedule the alarm
			AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarmMgr.set(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis() + 5000, alarmIntent);
			Log.i(TAG, "alarmMgr.set");			
		}
		
		return START_REDELIVER_INTENT;  
    }
    
	@Override
	public void onDestroy() {
		Log.d(TAG, "MyIntentService onDestroy() executed");
	    Toast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();
		Intent localIntent = new Intent();
		localIntent.setClass(this, MyIntentService.class); // restart service here
		this.startService(localIntent);
	}
}