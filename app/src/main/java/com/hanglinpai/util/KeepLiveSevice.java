package com.hanglinpai.util;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class KeepLiveSevice extends Service{
	private final static int KEEPLIVE_SERVICE_ID = 1001;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (Build.VERSION.SDK_INT < 18){
			Log.i("viclee","77777");
			startForeground(KEEPLIVE_SERVICE_ID, new Notification());
		}else{
			Log.i("viclee","8888");
			Intent _intent = new Intent(this,SecondService.class);
			startService(_intent);
			startForeground(KEEPLIVE_SERVICE_ID, new Notification());
		}
		
		return super.onStartCommand(intent, flags, startId);
	}


	public static class SecondService extends Service{
		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			// TODO Auto-generated method stub
				Log.i("viclee","99999");
			startForeground(KEEPLIVE_SERVICE_ID, new Notification());
			stopForeground(true);
			stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}
	}
}
