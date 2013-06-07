package com.idthk.wristband.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class MyProgressBar extends ProgressBar {
	 public MyProgressBar(Context context) {
		    this(context, null);
		  }
		  
		  public MyProgressBar(Context context, AttributeSet attrs) {
		    this(context, attrs, 0);
		  }
		  
		  public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
		    super(context, attrs, defStyle);
		  }
}
