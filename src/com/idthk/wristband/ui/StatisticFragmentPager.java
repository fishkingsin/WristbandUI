//package com.idthk.wristband.ui;
//
//import android.support.v4.app.Fragment;
//
//public class StatisticFragmentPager extends Fragment {
//
//}
//

package com.idthk.wristband.ui;


import java.util.Random;

import com.idthk.wristband.ui.R;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class StatisticFragmentPager extends Fragment {
	private static final int NUMBER_OF_PAGES = 2;
	public static final int ACTIVITY = 0;
	public static final int SLEEP = 1;
	private int mCurrentPage = 0;
	private ViewPager mViewPager;
	private MyStatisticFragmentPagerAdapter mMyStatisticFragmentPagerAdapter;
	
	PagerChangedCallback mCallback;

    // Container Activity must implement this interface
    public interface PagerChangedCallback {
        public void onPagerChangedCallback(int page);
    }
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	   
	   View mRootView = inflater.inflate(R.layout.statistic_fragmentpager, container, false);
	   mViewPager = (ViewPager) mRootView.findViewById(R.id.pager2);
	   mMyStatisticFragmentPagerAdapter = new MyStatisticFragmentPagerAdapter(getFragmentManager());
	   mViewPager.setAdapter(mMyStatisticFragmentPagerAdapter);
	   mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
           @Override
           public void onPageSelected(int position) {
        	   mCurrentPage = position;
        	   mCallback.onPagerChangedCallback(position);
        	   
               // When changing pages, reset the action bar actions since they are dependent
               // on which page is currently active. An alternative approach is to have each
               // fragment expose actions itself (rather than the activity exposing actions),
               // but for simplicity, the activity provides the actions in this sample.
            
           }
       });
	   
	  
	 
	   return mRootView;
	 }
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (PagerChangedCallback) activity;
            mCallback.onPagerChangedCallback(ACTIVITY);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	private static class MyStatisticFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyStatisticFragmentPagerAdapter(FragmentManager fm) {
        	
             super(fm);
             
        }  

        @Override
        public Fragment getItem(int index) { 
			if(index==0)
			{
				return new ActivityStatisticTabFragment();
			}
			else
			{
				return new SleepStatisticTabFragment();
			}
			
        }  

        @Override 
        public int getCount() {

             return NUMBER_OF_PAGES;
        }
   }
	public int getCurrentPage() {
		// TODO Auto-generated method stub
		return mCurrentPage;
	}
}
