package com.weather.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetWeatherTask extends AsyncTask<String, String, String> {
	HttpRequest jsonParser = new HttpRequest();

	Context ctx;

	String email_id_from_sms;
	String latLong_url;

	public GetWeatherTask(Context _ctx,String _latong_url) {
		ctx = _ctx;
		latLong_url = _latong_url;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... args) {

		// Check if connected to internet
		if (CC.iNa(ctx)) {

			Log.d("request!", "starting");

			String json = jsonParser
					.makeHttpRequest(latLong_url, "GET", null);

			Log.d("Login attempt", json.toString());
			return json.toString();
		} else {
			return "Internet Not Available";
		}

	}

	protected void onPostExecute(String file_url) {
		WeatherReceivedListener listner = (WeatherReceivedListener) ctx;
		listner.onWeatherReceived(file_url);
	}

	public interface WeatherReceivedListener {
		public void onWeatherReceived(String str);
	}

}
