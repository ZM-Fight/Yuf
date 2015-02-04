package com.yuf.app.mywidget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class OutsideViewPager extends ViewPager {

	public OutsideViewPager(Context context) {
		super(context);
	}

	public OutsideViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		
		Log.d("liow", "onTouchEven");
		 return false;
	}
	 @Override
	    public boolean onInterceptTouchEvent(MotionEvent arg0) {
	        // TODO Auto-generated method stub
			Log.d("liow", "onInterceptTouchEvent");
		 return false;
	    }   
	
}