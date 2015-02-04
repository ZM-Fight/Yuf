package com.yuf.app.ui;
import com.yuf.app.ui.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class Tab1ShareDetailActivity extends Activity {

	private Context mContext;
	private ListView listView;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1_share_detail_activity);
		
		init();
		list();
		
	}
	
	private void init() {
		// TODO Auto-generated method stub
		mContext = Tab1ShareDetailActivity.this;
				
	}
	
	
	private void list()
	{
		  
        listView = (ListView) findViewById(R.id.recipe_listview);

	}
	
	

			
	
	
}
