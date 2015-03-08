package com.weather.widget;

import org.json.JSONObject;

import com.weather.widget.GetWeatherTask.WeatherReceivedListener;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GetWeatherService extends Service implements
		WeatherReceivedListener {

	Intent intent;

	public GetWeatherService() {

	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(this.getClass().getSimpleName(), "onCreate() Show Notification");

	}

	public int onStartCommand(Intent _intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		intent = _intent;

		Log.d(this.getClass().getSimpleName(),
				"onStartCommand() Show Notification");

		new GetWeatherTask(GetWeatherService.this).execute();

		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Schedule service again
		scheduleServicePeriodically(this);

	}

	// Unused
	public void scheduleServicePeriodically(Context context) {
		// I want to restart this service again in two minute
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(
				context, AutoStart.class), PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
				+ (1000 * 2 * 60), pi);
	}

	@Override
	public void onWeatherReceived(String str) {
		stopSelf();
		saveWeather(str);

	}

	protected void saveWeather(String json) {

		try {

			JSONObject jObj = new JSONObject(json);

			String tempr = jObj.getJSONObject("main").getString("temp");
			String place = jObj.getString("name");

			// Toast.makeText(this, " Temprature : " + tempr,
			// Toast.LENGTH_SHORT)
			// .show();

			SharedPreferences sharedPref = getApplicationContext()
					.getSharedPreferences("weather", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("temprature", "" + tempr);
			editor.putString("place", "" + place);
			editor.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
