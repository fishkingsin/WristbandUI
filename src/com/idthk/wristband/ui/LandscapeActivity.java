package com.idthk.wristband.ui;

import android.app.Activity;
//import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
//import android.widget.Toast;

public class LandscapeActivity extends Activity {
	static final String TAG = "LandscapeActivity";
	OrientationEventListener myOrientationEventListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landsape_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		myOrientationEventListener = new OrientationEventListener(this,
				SensorManager.SENSOR_DELAY_NORMAL) {

			@Override
			public void onOrientationChanged(int arg0) {

				switch (getResources().getConfiguration().orientation) {
			    case Configuration.ORIENTATION_LANDSCAPE:
			        Log.v(TAG,"Configuration.ORIENTATION_LANDSCAPE");
			        break;
			    case Configuration.ORIENTATION_PORTRAIT:
			        //xxx
			    	Log.v(TAG,"Configuration.ORIENTATION_PORTRAIT");
			        break;
				}
//				if (arg0 < 90 || arg0 > 270) {
//					 Log.v(TAG,"Orientation :" + String.valueOf(arg0));
//					finish();
//				}

			}
		};
		if (myOrientationEventListener.canDetectOrientation()) {
			myOrientationEventListener.enable();
		}

	}

}
