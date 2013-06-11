package com.idthk.wristband.ui;

import java.util.ArrayList;
import java.util.List;

import com.idthk.wristband.ui.R;
import com.idthk.wristband.ui.ScrollPagerMain.ScrollPagerMainCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Switch;
//@SuppressLint("ValidFragment")
public class SettingsFragment extends Fragment implements LoaderCallbacks<Void> {
	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	public static final String TARGET = "target";
	static final int ACTIVITY_REQUEST = 0;
	private SharedPreferences prefs;
	public static final SettingsFragment newInstance(String message) {
		SettingsFragment f = new SettingsFragment();
		Bundle bdl = new Bundle(1);
		bdl.putString(EXTRA_MESSAGE, message);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		String message = getArguments().getString(EXTRA_MESSAGE);
		View mRootView = inflater.inflate(R.layout.settings_fragment, container, false);
		
		Switch targetSwitch = (Switch) mRootView.findViewById(R.id.switch_target);
		prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
 		boolean target = prefs.getBoolean(getString(R.string.key_target), true);
 		targetSwitch.setChecked(target);
		targetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		    	  Log.v("SettingsFragment " ,"isChecked "+ isChecked);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(getString(R.string.key_target), isChecked);

				// Commit the edits!
				editor.commit();
				
//		        if (isChecked) {
//		        	
//		            // The toggle is enabled
//		        } else {
//		            // The toggle is disabled
//		        }
		    }
		});
		
//		TextView messageTextView = (TextView) v.findViewById(R.id.textView1);
//		messageTextView.setText(message);
//		((Button) mRootView.findViewById(R.id.btn_general_settings)).setOnClickListener( new OnClickListener() {
//            public void onClick(View m) {
//            	getActivity().startActivityForResult(new Intent(getActivity(), FragmentPreferences.class),ACTIVITY_REQUEST);
//             }
//         } );
		((Button) mRootView.findViewById(R.id.btn_user_profile)).setOnClickListener( new OnClickListener() {
            public void onClick(View m) {
            	getActivity().startActivityForResult(new Intent(getActivity(), UserProfileActivity.class),ACTIVITY_REQUEST);
             }
         } );
		

		return mRootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		prefs = activity.getSharedPreferences(getString(R.string.pref_name), 0);
		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
	}

	@Override
	public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Void> arg0, Void arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoaderReset(Loader<Void> arg0) {
		// TODO Auto-generated method stub

	}
}
