package com.idthk.wristband.ui;

import com.idthk.wristband.ui.R;

import com.tomoki.iwai.ScrollPagerHorizontal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;

public class ScrollPagerMain extends Fragment implements OnTouchListener{

	ScrollPagerMainCallback mCallback;

    // Container Activity must implement this interface
    public interface ScrollPagerMainCallback {
        public void onScrollPagerMainCallback();
    }
	private GestureDetector gesturedetector = null;

	private Intent i;
	static final String TAG = "ScrollPager";
	private HorizontalScrollView scrollView;
    private ViewGroup contentView;
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.scroll_pager);
//
//		scrollView = (HorizontalScrollView) findViewById(R.id.scroll_view);
//        contentView = (ViewGroup) findViewById(R.id.content);
//
//        scrollView.setOnTouchListener(new ScrollPager(scrollView, contentView));
//        scrollView.post(new Runnable()
//        {
//                public void run()
//                {
//                        scrollView.scrollTo(contentView.getPaddingRight(), 0);
//                }
//        });
//		
//
//	}
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ScrollPagerMainCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        // Inflate the layout containing a title and body text.
	    	ViewGroup rootView = (ViewGroup) inflater
	                .inflate(R.layout.scroll_pager, container, false);
	    	
	    	contentView = (ViewGroup) rootView.findViewById(R.id.content);
	    	scrollView = (HorizontalScrollView) rootView.findViewById(R.id.scroll_view);
	        scrollView.setOnTouchListener(new ScrollPagerHorizontal(scrollView, contentView));
	        
//	        ((Button)rootView.findViewById(R.id.button_page1)).setOnTouchListener(this);
//	        ((Button)rootView.findViewById(R.id.button_page2)).setOnTouchListener(this);
	         
	        scrollView.post(new Runnable()
	        {
	                public void run()
	                {
//	                        scrollView.scrollTo(contentView.getPaddingRight(), 0);
	                }
	        });
	    	
	    	return rootView;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			mCallback.onScrollPagerMainCallback();
			Log.d(TAG , "onTouchUp" );
		}
		return false;
	}

	
}