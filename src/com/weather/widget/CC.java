package com.weather.widget;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CC {

	public static final String GETWEATHER = "http://api.openweathermap.org/data/2.5/weather?q=Kolkata,India";

	public static final String LOGTAG = "WW";

	public static boolean iNa(Context ctx) {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
