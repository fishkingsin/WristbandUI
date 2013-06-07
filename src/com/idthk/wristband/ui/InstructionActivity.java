package com.idthk.wristband.ui;

import java.util.ArrayList;

import com.idthk.wristband.ui.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class InstructionActivity extends FragmentActivity implements ScreenSlidePageFragment.OnSkipClickedListener{
	static final String TAG = "InstructionActivity";
	static int numberOfPages = 3;
	ViewPager myViewPager;
	MyFragmentPagerAdapter myFragmentPagerAdapter;
	    String text = "test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    setContentView(R.layout.instruction_fragment);

	    myViewPager = (ViewPager) findViewById(R.id.pager);
	    myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
	    myViewPager.setAdapter(myFragmentPagerAdapter);
	    if(myViewPager.findViewById(R.id.slide3_skip_textview)!=null)
	    {
	    	Log.v(TAG,"slide3_skip_textview found");
	    }
	}
	 public void onSelected() {
//		 finish();
		 Intent intent = new Intent(this, UserProfileActivity.class);
			startActivityForResult(intent,Main.TO_USER_PROFILE_REQUEST);
	        // The user selected the headline of an article from the HeadlinesFragment
	        // Do something here to display that article
	 }
	 protected void onActivityResult(int requestCode, int resultCode,
	            Intent data) {
	        
	        Log.v(TAG,"requestCode " + requestCode +" resultCode "+ resultCode);
	        if(requestCode==Main.TO_USER_PROFILE_REQUEST)
	        {
	        	finish();
	        }
	    }
	// Adapters
	private static class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	    public MyFragmentPagerAdapter(FragmentManager fm)   {
	        super(fm);
	    }

	    @Override
	    public ScreenSlidePageFragment getItem(int index) {

	        return ScreenSlidePageFragment.create(index);
	    }

	    @Override
	    public int getCount() {
	        return numberOfPages;
	    }
	}

}