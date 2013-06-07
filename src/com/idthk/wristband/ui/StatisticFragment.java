package com.idthk.wristband.ui;

import java.util.ArrayList;
import java.util.List;

import com.idthk.wristband.ui.R;
import com.idthk.wristband.ui.ScrollPagerMain.ScrollPagerMainCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatisticFragment extends Fragment implements LoaderCallbacks<Void> {
	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

	public static final StatisticFragment newInstance(String message) {
		StatisticFragment f = new StatisticFragment();
		Bundle bdl = new Bundle(1);
		bdl.putString(EXTRA_MESSAGE, message);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String message = getArguments().getString(EXTRA_MESSAGE);
		View v = inflater.inflate(R.layout.statistic_fragment, container, false);
		TextView messageTextView = (TextView) v.findViewById(R.id.textView1);
		messageTextView.setText(message);
		
		

		return v;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

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
