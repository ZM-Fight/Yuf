package com.yuf.app.mywidget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class FoodViewPage extends ViewPager {

	public FoodViewPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public FoodViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		Log.d("foodviewpage", "onInterceptTouchEvent"); 
		getParent().requestDisallowInterceptTouchEvent(true);
		 super.onInterceptTouchEvent(arg0);
		 return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		Log.d("foodviewpage", "onTouchevent");
		
		
		
		int action = arg0.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			showMsg("ACTION_DOWN" + action);
			break;
		case MotionEvent.ACTION_UP:
			showMsg("ACTION_UP" + action);
			break;
		case MotionEvent.ACTION_POINTER_UP:
			showMsg("ACTION_POINTER_UP" + action);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			showMsg("ACTION_POINTER_DOWN" + action);
			break;
		case MotionEvent.ACTION_MOVE:
			showMsg("ACTION_MOVE");
			break;
		case MotionEvent.ACTION_CANCEL:
			showMsg("ACTION_CANCEL");
			break;
			
		}
		super.onTouchEvent(arg0);
		
		return true;
	}
	private void showMsg(String string) {
		// TODO Auto-generated method stub
		Log.d("foodviewpage",string); 
	}
	
	
}
