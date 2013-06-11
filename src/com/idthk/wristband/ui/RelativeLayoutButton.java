package com.idthk.wristband.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RelativeLayoutButton extends RelativeLayout {

	public RelativeLayoutButton(Context context) {
		this(context, null);
	}

	public RelativeLayoutButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RelativeLayoutButton(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RelativeLayoutButton, 0, 0);
//		String titleText = a
//				.getString(R.styleable.RelativeLayoutButton_titleText);
//		int valueColor = a.getColor(
//				R.styleable.RelativeLayoutButton_valueColor,
//				android.R.color.holo_blue_light);
		a.recycle();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.setting_tab, this, true);
		
		
		    
//		Log.v("RelativeLayoutButton" , "construct");
//		TextView title = (TextView)inflater.inflate(R.id.setting_tab_title_textview, this);
//		if(title!=null)
//		{
//			title.setText("title");
//		}
//		TextView title2 = (TextView)inflater.inflate(R.id.setting_tab_goal_textview, this);
//		if(title2!=null)
//		{
//			title2.setText("value");
//		}
//		TextView title = (TextView) inflater.inflate(R.id.setting_tab_goal_textview,this);
//	    title.setText(titleText);
//
//	    
//	    TextView title2 = (TextView) getChildAt(1);
//	    title2.setText(titleText);

	}

	// method for setting texts for the text views
//	public void setText(int id, CharSequence text) {
//		View v = findViewById(id);
//		if (null != v && v instanceof TextView) {
//			((TextView) v).setText(text);
//		}
//
//	}

	// method for setting drawable for the images
//	public void setImageDrawable(int id, Drawable drawable) {
//
//		View v = findViewById(id);
//		if (null != v && v instanceof ImageView) {
//			((ImageView) v).setImageDrawable(drawable);
//		}
//
//	}

	// method for setting images by resource id
//	public void setImageResource(int id, int image_resource_id) {
//
//		View v = findViewById(id);
//		if (null != v && v instanceof ImageView) {
//			((ImageView) v).setImageResource(image_resource_id);
//		}
//
//	}

}