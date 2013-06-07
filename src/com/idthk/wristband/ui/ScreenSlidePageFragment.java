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
import com.idthk.wristband.ui.R;

import android.support.v4.app.Fragment;
import android.app.Activity;
//import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragment extends Fragment{
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String TAG = "ScreenSlidePageFragment";
    OnSkipClickedListener mCallback;
    public interface OnSkipClickedListener {
        public void onSelected();
    }
    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnSkipClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSkipClickedListener");
        }
    }
    
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
    	ViewGroup rootView;
    	if(getPageNumber()==0)
        {
        	rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page1, container, false);
        }
    	else if(getPageNumber()==1)
        {
        	rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page2, container, false);
        }
    	else if(getPageNumber()==2)
        {
        	rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page3, container, false);
        	TextView tv = (TextView)rootView.findViewById(R.id.slide3_skip_textview);
        	tv.setOnClickListener( new OnClickListener() {
                public void onClick(View m) {
                	Log.v(TAG,"TextView clicked");
                	mCallback.onSelected();
//                    Toast.makeText(collection.getContext(),"click",Toast.LENGTH_LONG).show();
                 }
             });
        }
    	else
    	{
    		rootView = (ViewGroup) inflater
                    .inflate(R.layout.fragment_screen_slide_page1, container, false);
    	}
        // Set the title view to show the page number.
//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(getString(R.string.title_template_step, mPageNumber + 1));
        
        return rootView;
    }
    public void onClick(View arg0) {
        Log.v(TAG,"onClick");
    }
    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
