package com.yuf.app.ui;

import com.yuf.app.Entity.UserInfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Tab3DegreeActivity extends Activity {
	private ImageView backImageView;
	private ImageView profileImageView;
	 private TextView userpointsTextView;
	 private ProgressBar levelpointsBar;
	 private TextView levelnameTextView;
	 private TextView leveldiscountTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.tab3_degree);
	userpointsTextView=(TextView)findViewById(R.id.tab3_degree_userpoints);
	levelpointsBar=(ProgressBar)findViewById(R.id.tab3_degree_bar);
	levelnameTextView=(TextView)findViewById(R.id.tab3_degree_levelname);
	leveldiscountTextView=(TextView)findViewById(R.id.tab3_degree_leveldiscount);
	
	leveldiscountTextView.setText(String.valueOf(UserInfo.getInstance().getLeveldiscout()));
	levelnameTextView.setText(UserInfo.getInstance().getLevelname());
	
	userpointsTextView.setText(String.valueOf(UserInfo.getInstance().getUserpoints()));
	if(UserInfo.getInstance().getLevelpoints()!=0)
	levelpointsBar.setProgress(UserInfo.getInstance().getUserpoints()/UserInfo.getInstance().getLevelpoints()*100);
	
	
	
	backImageView=(ImageView)findViewById(R.id.tab3_degree_back_imageview);
	
	backImageView.setOnClickListener(new OnClickListener() {
	
		
		
		@Override
		public void onClick(View v) {
			onBackPressed();
			// TODO Auto-generated method stub
			
		}
	});
}
}
