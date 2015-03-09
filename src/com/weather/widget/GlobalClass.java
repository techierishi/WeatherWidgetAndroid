package com.weather.widget;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(formKey = "", // This is required for backward compatibility but
// not used
formUri = "http://karmicksol.comeze.com/report.php")
public class GlobalClass extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// The following line triggers the initialization of ACRA
		ACRA.init(this);
	}

	
}
