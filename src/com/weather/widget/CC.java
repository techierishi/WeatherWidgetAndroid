package com.weather.widget;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

public class CC {

	public static final String GETWEATHER = "http://api.openweathermap.org/data/2.5/weather?";

	public static final String LOGTAG = "WW";

	public static boolean iNa(Context ctx) {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public static void appendLog(String text) {
		File logFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/debugger.txt");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:");
			String v_time = sdf.format(cal.getTime());

			buf.append(" [" + v_time + "] " + text);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String dateFromUTS(long timeStamp) {
		Log.d(CC.LOGTAG, " UTS is >> " + timeStamp);
		String UNIX_DATE_FORMAT = "dd-MM-yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(UNIX_DATE_FORMAT);
		java.util.Date time = new java.util.Date((long) timeStamp * 1000L);

		Log.d(CC.LOGTAG, " UTS date is >> " + formatter.format(time));
		return formatter.format(time);
	}
}
