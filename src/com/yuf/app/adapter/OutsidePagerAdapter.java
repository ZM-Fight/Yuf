package com.yuf.app.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class OutsidePagerAdapter extends PagerAdapter{
	
	private ArrayList<View> mViews;
	
	public OutsidePagerAdapter(ArrayList<View> views){
		mViews = views;
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getCount() {
		return mViews.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(mViews.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(mViews.get(position));
		return mViews.get(position);
	}
}
