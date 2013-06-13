package com.idthk.wristband.ui;

import com.idthk.wristband.socialnetwork.FacebookShareActivity;
import com.idthk.wristband.socialnetwork.TwitterShareActivity;
import com.idthk.wristband.ui.R;
import com.idthk.wristband.ui.MainSlideFragment.OnShareButtonClickedListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
//import android.content.res.Configuration;
//import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import android.widget.Toast;

public class Main extends FragmentActivity implements
		ScrollPagerMain.ScrollPagerMainCallback,
		MainFragmentPager.PagerChangedCallback,
		StatisticFragmentPager.PagerChangedCallback,
		MainSlideFragment.OnShareButtonClickedListener,
		TabsFragment.OnFragmentTabbedListener {
	static final int TO_INSTRUCTION_REQUEST = 0x10;
	static final int TO_USER_PROFILE_REQUEST = 0x20;
	static final int LANSCAPE_REQUEST = 0x30;
	static final int FACEBOOK_REQUEST = 0x40;
	static final int TWITTER_REQUEST = 0x50;
	static final int IMAGE_GALLERY_REQUEST = 0x60;
	static final int TAKE_PHOTO_CODE = 0x70;
	static final int SELECT_IMAGE_CODE = 0x90;
	static final int THRESHOLD = 5;
	static final String TAG = "Main";

	static final String FIRST_TIME = "firsttime";
	public static final String TITLE = "title";
	public static final String TARGET_ORIENTTION = "target_orientation";

	OrientationEventListener orientationListener;
	OnShareButtonClickedListener myShareButtonClickedListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean firstTime = prefs.getBoolean(FIRST_TIME, true);
		if (firstTime) {

			Intent intent = new Intent(this, InstructionActivity.class);
			intent.putExtra(ScreenSlidePageFragment.ARG_FIRSTTIME, firstTime);
			startActivityForResult(intent, TO_INSTRUCTION_REQUEST);

			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean(FIRST_TIME, false);

			// Commit the edits!
			editor.commit();
		} else {

			orientationListener = new OrientationEventListener(this,
					SensorManager.SENSOR_DELAY_UI) {

				@Override
				public void onOrientationChanged(int orientation) {
					// TODO Auto-generated method stub
					if (canShow(orientation)) {
						startLandscapeActivity(orientation);

					}

				}

			};
		}
		// Intent intent = new Intent(this, FragmentPreferences
		// .class);
		// startActivityForResult(intent,ACTIVITY_REQUEST);

		// Intent intent = new Intent(this, FacebookShareActivity.class);
		// intent.putExtra(SlidePageFragment.FACEBOOK,
		// "I'm going for my daily goal");
		// intent.putExtra(TITLE , SlidePageFragment.FACEBOOK);
		// this.startActivityForResult(intent,ACTIVITY_REQUEST);//,bundle);
		// this.startActivityForResult(new Intent(this,
		// TwitterShareActivity.class),ACTIVITY_REQUEST);
		// this.startActivity(new Intent(this, SimpleGraph.class));

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.v(TAG, "requestCode " + requestCode + " resultCode " + resultCode);

	}

	private void startLandscapeActivity(int orientation) {

		Intent intent = new Intent(this, LandscapeActivity.class);
		if (isLandscapeLeft(orientation)) {
			intent.putExtra(Main.TARGET_ORIENTTION,
					ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			intent.putExtra(Main.TARGET_ORIENTTION,
					ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		}
		startActivityForResult(intent, LANSCAPE_REQUEST);
	}

	private boolean isLandscapeRight(int orientation) {
		return orientation >= (90 - THRESHOLD)
				&& orientation <= (90 + THRESHOLD);
	}

	private boolean isLandscapeLeft(int orientation) {
		return orientation >= (270 - THRESHOLD)
				&& orientation <= (270 + THRESHOLD);
	}

	private boolean isPortrait(int orientation) {
		return (orientation >= (360 - THRESHOLD) && orientation <= 360)
				|| (orientation >= 0 && orientation <= THRESHOLD);
	}

	public boolean canShow(int orientation) {
		return isLandscapeLeft(orientation) || isLandscapeRight(orientation);
	}

	// public void onConfigurationChanged(Configuration newConfig) {
	// super.onConfigurationChanged(newConfig);
	//
	// // Checks the orientation of the screen
	// if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	// Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	// } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	// Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	// }
	// }

	public void initUI() {
		Log.v(TAG, "initUI");
	}

	public void onScrollPagerMainCallback() {
		Log.v(TAG, "onScrollPagerMainCallback");

	}

	@Override
	public void onPagerChangedCallback(int page) {
		// TODO Auto-generated method stub
		if (page == MainFragmentPager.ACTIVITY) {
			((TextView) findViewById(R.id.titlebar_textview))
					.setText("Activity");
			((Button) findViewById(R.id.btn_settings_done))
					.setVisibility(View.GONE);
		} else if (page == MainFragmentPager.SLEEP) {
			((TextView) findViewById(R.id.titlebar_textview)).setText("Sleep");
			((Button) findViewById(R.id.btn_settings_done))
					.setVisibility(View.GONE);
		}

	}

	@Override
	public void onShareButtonClicked(String s) {
		// TODO Auto-generated method stub
		if (s.equals(MainSlideFragment.FACEBOOK)) {
			Intent intent = new Intent(this, FacebookShareActivity.class);
			intent.putExtra(MainSlideFragment.FACEBOOK,
					"I'm going for my daily goal");
			intent.putExtra(TITLE, MainSlideFragment.FACEBOOK);
			startActivityForResult(intent, FACEBOOK_REQUEST);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);

		} else if (s.equals(MainSlideFragment.TWITTER)) {
			Intent intent = new Intent(this, TwitterShareActivity.class);

			intent.putExtra(MainSlideFragment.TWITTER,
					"I'm going for my daily goal");
			intent.putExtra(TITLE, MainSlideFragment.TWITTER);
			startActivityForResult(intent, TWITTER_REQUEST);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
		}

	}

	@Override
	public void onTabbed(String s) {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.titlebar_textview)).setText(s);

	}

	@Override
	public void onResume() {
		super.onResume();
		if (orientationListener != null)
			orientationListener.enable();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (orientationListener != null)
			orientationListener.disable();
	}
}