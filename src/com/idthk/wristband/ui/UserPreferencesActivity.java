package com.idthk.wristband.ui;

import com.idthk.wristband.ui.R;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class UserPreferencesActivity extends Activity {
	static final String TAG = "UserProfileActivity";
//	private Context mContext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		int targetPreferenceResource = getIntent().getIntExtra(UserPrefsFragment.ARG_XML, R.xml.userprofile_preferences);
		getFragmentManager().beginTransaction().replace(android.R.id.content,
                UserPrefsFragment.create(targetPreferenceResource)).commit();
	}

	
	public static class UserPrefsFragment extends PreferenceFragment 
	implements SharedPreferences.OnSharedPreferenceChangeListener {
		static final String ARG_XML = "xml";
		private int  targetPreferenceFile;
		
		
		public static UserPrefsFragment create(int targetPreferenceFile) {
			UserPrefsFragment fragment = new UserPrefsFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_XML, targetPreferenceFile);
			fragment.setArguments(args);
			return fragment;
		}
		
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            targetPreferenceFile = getArguments().getInt(ARG_XML);
            // Load the preferences from an XML resource
            addPreferencesFromResource(targetPreferenceFile);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
            Preference pref = findPreference(getString(R.string.key_user_name));
        	pref.setSummary(sharedPreferences.getString(getString(R.string.key_user_name), getString(R.string.default_user_name)));
        	pref = findPreference("prefUserGender");
        	pref.setSummary(sharedPreferences.getString("prefUserGender", getString(R.string.default_user_gender)));
        }
        @Override
    	public void onDestroyView()
    	{
        	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    		super.onDestroyView();
    	}
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            // handle the preference change here'
        	Log.v(TAG,"key : "+sharedPreferences.toString() +" "+key +" "+sharedPreferences.getAll().toString());
        	Preference pref = findPreference(key);
        	if (key.equals("prefUserGender")) {
        		
        		pref.setSummary(sharedPreferences.getString(key, getString(R.string.default_user_gender)));
            } else if (key.equals(getString(R.string.key_user_name))) {
            	pref.setSummary(sharedPreferences.getString(key, getString(R.string.default_user_name)));
            }else if (key.equals("prefDateOfBirth")) {
            	pref.setSummary(sharedPreferences.getString(key, "1991.01.01"));
            }
            else if (key.equals("prefHeight")) {
            	pref.setSummary(sharedPreferences.getInt(key, 178));
            }else if (key.equals("prefWeight")) {
            	pref.setSummary(sharedPreferences.getInt(key, 50));
            }
        }
    }
}
