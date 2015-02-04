package com.yuf.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.yuf.app.Entity.CategorysEntity;

public class MiddlePageAdapter extends FragmentStatePagerAdapter {


	public MiddlePageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Fragment> fragments = new ArrayList<Fragment>();;
	public List<CategorysEntity> tabs = new ArrayList<CategorysEntity>();
	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);// TODO Auto-generated method stub
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
	return fragments.size();
	}
public void addFragments(ArrayList<Fragment>fra,ArrayList<CategorysEntity>cat){
	fragments.addAll(fra);
	tabs.addAll(cat);
	notifyDataSetChanged();
	
}
@Override
public CharSequence getPageTitle(int position) {
	return tabs.get(position).getName();
}
}
