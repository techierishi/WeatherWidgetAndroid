package com.weather.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoStart extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d(CC.LOGTAG, "onReceive()");
		Intent serviceIntent = new Intent(context, GetWeatherService.class);
		context.startService(serviceIntent);
	}
}