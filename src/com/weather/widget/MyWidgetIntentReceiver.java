package com.weather.widget;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Created by Jacek Milewski looksok.wordpress.com
 */

public class MyWidgetIntentReceiver extends BroadcastReceiver {

	private Context ctx;
	SharedPreferences sharedPref;

	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;
		sharedPref = ctx.getSharedPreferences("weather", Context.MODE_PRIVATE);

		if (intent.getAction().equals(
				"com.weather.widget.intent.action.CHANGE_PICTURE")) {
			updateWidgetPictureAndButtonListener(context);
		}
	}

	public MyWidgetIntentReceiver(Context _ctx) {
		ctx = _ctx;
		sharedPref = ctx.getSharedPreferences("weather", Context.MODE_PRIVATE);
	}

	public void updateWidgetPictureAndButtonListener(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_demo);
		remoteViews.setImageViewResource(R.id.widget_image, getImageToSet());

		remoteViews.setTextViewText(R.id.place_name, getPlace());
		remoteViews.setTextViewText(R.id.place_temp, getTemprature());
		remoteViews.setTextViewText(R.id.temp_unit, getUnit());

		remoteViews.setTextViewText(R.id.humidity, getHumidity());

		remoteViews.setTextViewText(R.id.description, getDescription());

		remoteViews.setTextViewText(R.id.date_today, getDateToday());

		// REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
		remoteViews.setOnClickPendingIntent(R.id.widget_button,
				MyWidgetProvider.buildButtonPendingIntent(context));

		MyWidgetProvider.pushWidgetUpdate(context.getApplicationContext(),
				remoteViews);
	}

	private String getPlace() {
		String place = sharedPref.getString("place", "");
		return place.substring(0, 10);
	}

	private String getDateToday() {

		long timeStamp = (Calendar.getInstance().getTimeInMillis()) / 1000L;
		return CC.dateFromUTS(timeStamp);
	}

	private String getHumidity() {
		String humidity = sharedPref.getString("humidity", "");
		return humidity;
	}

	private String getDescription() {
		String description = sharedPref.getString("description", "");
		return description;
	}

	private String getUnit() {
		return "c";
	}

	private String getTemprature() {
		double kelvin = 0, celsius = 0;
		String temprature = sharedPref.getString("temprature", "");
		if (temprature != null && !temprature.isEmpty()) {
			kelvin = Double.parseDouble(temprature);
			celsius = kelvin - 273.0;
			celsius = round(celsius, 2);
		}
		return "" + celsius;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	private int getImageToSet() {
		String icon = sharedPref.getString("icon", "");
		return ctx.getResources().getIdentifier("" + icon, "drawable",
				ctx.getPackageName());
	}
}
