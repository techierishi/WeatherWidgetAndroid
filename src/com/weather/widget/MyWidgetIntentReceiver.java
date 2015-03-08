package com.weather.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Created by Jacek Milewski looksok.wordpress.com
 */

public class MyWidgetIntentReceiver extends BroadcastReceiver {

	private static int clickCount = 0;

	private Context ctx;
	SharedPreferences sharedPref;

	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;
		sharedPref = ctx.getSharedPreferences("weather", Context.MODE_PRIVATE);

		// Start the service here
		Intent wService = new Intent(ctx, AutoStart.class);
		ctx.sendBroadcast(wService, null);

		if (intent.getAction().equals(
				"com.weather.widget.intent.action.CHANGE_PICTURE")) {
			updateWidgetPictureAndButtonListener(context);
		}
	}

	private void updateWidgetPictureAndButtonListener(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_demo);
		remoteViews.setImageViewResource(R.id.widget_image, getImageToSet());

		remoteViews.setTextViewText(R.id.place_name, getPlace());
		remoteViews.setTextViewText(R.id.place_temp, getTemprature());

		// REMEMBER TO ALWAYS REFRESH YOUR BUTTON CLICK LISTENERS!!!
		remoteViews.setOnClickPendingIntent(R.id.widget_button,
				MyWidgetProvider.buildButtonPendingIntent(context));

		MyWidgetProvider.pushWidgetUpdate(context.getApplicationContext(),
				remoteViews);
	}

	private String getPlace() {
		String place = sharedPref.getString("place", "");
		return place;
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
		clickCount++;
		return clickCount % 2 == 0 ? R.drawable.me : R.drawable.wordpress_icon;
	}
}
