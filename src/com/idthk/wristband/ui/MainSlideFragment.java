/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.idthk.wristband.ui;

import java.io.File;
import java.util.Random;

import com.idthk.wristband.ui.R;
import com.idthk.wristband.ui.ScrollPagerMain.ScrollPagerMainCallback;
import com.tomoki.iwai.ScrollPagerHorizontal;
import com.tomoki.iwai.ScrollPagerVertical;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.AsyncTask;
//import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy
 * title indicating the page number, along with some dummy text.
 * 
 * <p>
 * This class is used by the {@link CardFlipActivity} and
 * {@link ScreenSlideActivity} samples.
 * </p>
 */
public class MainSlideFragment extends Fragment implements
		SharedPreferences.OnSharedPreferenceChangeListener {
	public static final String FACEBOOK = "Facebook";
	public static final String TWITTER = "Twitter";
	/**
	 * The argument key for the page number this fragment represents.
	 */
	public static final String ARG_PAGE = "page";
	public static final String TAG = "ScreenSlidePageFragment";
	ViewGroup mRootView;
	OnShareButtonClickedListener mCallback;
	CustomProgressBar m_regularProgressBar;
	
	View targetView;
	View nonTargetView;
	TextView goalStepsTv;
	TextView goalCaloriesTv;
	TextView goalDistancesTv;

	public interface OnShareButtonClickedListener {
		public void onShareButtonClicked(String s);
	}

	/**
	 * The fragment's page number, which is set to the argument value for
	 * {@link #ARG_PAGE}.
	 */
	private int mPageNumber;

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given page number.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnShareButtonClickedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	public static MainSlideFragment create(int pageNumber) {
		MainSlideFragment fragment = new MainSlideFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public MainSlideFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
		Log.v(TAG, "ScreenSlidePageFragment : ID " + this.getId());
	}
	@Override
	public void onDestroyView()
	{
		SharedPreferences prefs = this.getActivity().getSharedPreferences(
				getString(R.string.pref_name), 0);
		prefs.unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroyView();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mPageNumber == 0) {
			mRootView = (ViewGroup) inflater.inflate(
					R.layout.main_scrollview_activity, container, false);
			
			SharedPreferences prefs = this.getActivity().getSharedPreferences(
					getString(R.string.pref_name), 0);
			publishSettings(prefs);
			

			prefs.registerOnSharedPreferenceChangeListener(this);
			String path = prefs.getString("profile_pic", "");
			Log.v(TAG, "profile path : " + path);
			if (path != "") {
				Bitmap myBitmap = Utilities.decodeFile(new File(path),
						this.getActivity());
				((ImageView) mRootView.findViewById(R.id.profile_pic))
						.setImageBitmap(myBitmap);
			}
			((Button) mRootView.findViewById(R.id.button_facebook_share))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View m) {

							mCallback.onShareButtonClicked(FACEBOOK);
						}
					});

			((Button) mRootView.findViewById(R.id.button_twitter_share))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View m) {

							mCallback.onShareButtonClicked(TWITTER);
						}
					});

			final ScrollView scrollView = (ScrollView) mRootView
					.findViewById(R.id.main_activity_scroll_view);

			scrollView.post(new Runnable() {
				public void run() {
					scrollView.scrollTo(0, 0);
				}
			});

			m_regularProgressBar = (CustomProgressBar) mRootView
					.findViewById(R.id.target_progress_bar_large);

			m_regularProgressBar.setTarget(80);
			m_regularProgressBar.setProgressInMins(40);
			new UpdateBarTask().execute();
		} else {
			mRootView = (ViewGroup) inflater.inflate(
					R.layout.main_scrollview_sleep, container, false);
			((Button) mRootView.findViewById(R.id.button_facebook_share))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View m) {

							mCallback.onShareButtonClicked(FACEBOOK);
						}
					});
			((Button) mRootView.findViewById(R.id.button_twitter_share))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View m) {

							mCallback.onShareButtonClicked(TWITTER);
						}
					});
			SharedPreferences prefs = this.getActivity().getSharedPreferences(
					getString(R.string.pref_name), 0);
			String path = prefs.getString("profile_pic", "");
			Log.v(TAG, "profile path : " + path);
			if (path != "") {
				Bitmap myBitmap = Utilities.decodeFile(new File(path),
						this.getActivity());
				((ImageView) mRootView.findViewById(R.id.profile_pic))
						.setImageBitmap(myBitmap);
			}

			populateGraph(mRootView);

		}
		return mRootView;
	}

	private void publishSettings(SharedPreferences prefs) {
		// TODO Auto-generated method stub
		
		boolean target = prefs.getBoolean(getString(R.string.key_target),
				true);
		targetView = ((View) mRootView.findViewById(R.id.target_layout_on));
		nonTargetView = ((View) mRootView.findViewById(R.id.target_layout_off));
			
		targetView.setVisibility((target)?View.VISIBLE:View.GONE);
			
		nonTargetView.setVisibility((target)?View.GONE:View.VISIBLE);
		
		int targetSteps = prefs.getInt(getString(R.string.key_targetSteps), 0);
		int targetCalories = prefs.getInt(getString(R.string.key_targetCalories), 0);
		int targetDistances = prefs.getInt(getString(R.string.key_targetDistances), 0);
		
		goalStepsTv = ((TextView)mRootView.findViewById(R.id.goal_steps_indicat_textview));
		goalStepsTv.setText(""+targetSteps);
		goalCaloriesTv = ((TextView)mRootView.findViewById(R.id.goal_calories_indicate_textview));
		goalCaloriesTv.setText(""+targetCalories);
		goalDistancesTv = ((TextView)mRootView.findViewById(R.id.goal_distances_indicat_textview));
		goalDistancesTv.setText(""+targetDistances);
	}

	private void populateGraph(View mRootView) {
		// TODO Auto-generated method stub
		// init example series data
		Random random = new Random();
		GraphViewSeries exampleSeries = new GraphViewSeries(
				new GraphViewData[] {
						new GraphViewData(1, 2.0d),
						new GraphViewData(2, 1.5d),
						new GraphViewData(2.5, 3.0d), 
						new GraphViewData(3, 2.5d),
						new GraphViewData(4, 1.0d),
						new GraphViewData(5, random.nextInt(10)),
						new GraphViewData(6, random.nextInt(10)),
						new GraphViewData(7, random.nextInt(10)),
						new GraphViewData(8, random.nextInt(10)),
						new GraphViewData(9, random.nextInt(10)),});

		// graph with dynamically genereated horizontal and vertical labels
		GraphView graphView;
		graphView = new LineGraphView(getActivity(), "In bed time 9 hrs");
		graphView.setHorizontalLabels(new String[] { "11:00 pm", "8:00 am" });
		graphView.setVerticalLabels(new String[] { "high", "middle", "low" });
		graphView.addSeries(exampleSeries); // data
		((ViewGroup) mRootView.findViewById(R.id.graph1)).addView(graphView);
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if(key.equals(getString(R.string.key_target)))
		{
			boolean target = sharedPreferences.getBoolean(key, false);
			targetView.setVisibility((target)?View.VISIBLE:View.GONE);
			
			nonTargetView.setVisibility((target)?View.GONE:View.VISIBLE);
		}
		else if(key.equals(getString(R.string.key_target)))
		{
		int targetSteps = sharedPreferences.getInt(getString(R.string.key_targetSteps), 0);
		goalStepsTv.setText(targetSteps);
		}
		else if(key.equals(getString(R.string.key_target)))
		{
		int targetCalories = sharedPreferences.getInt(getString(R.string.key_targetCalories), 0);
		goalCaloriesTv.setText(targetCalories);
		}
		else if(key.equals(getString(R.string.key_target)))
		{
		int targetDistances = sharedPreferences.getInt(getString(R.string.key_targetDistances), 0);
		goalDistancesTv.setText(targetDistances);
		}
		
		
	}

	private class UpdateBarTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			int max = 104;
			for (int i = 0; i <= max; i++) {
				try {
					// update every second
					Thread.sleep(100);
					if (i == max - 1) {
						i = 0;
					}
					//

				} catch (InterruptedException e) {

				}

				publishProgress(i);
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			m_regularProgressBar.setProgressInMins(values[0]);
			// m_regularProgressBar.setProgress(values[0]);
		}
	}
}
