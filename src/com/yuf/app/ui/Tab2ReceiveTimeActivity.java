package com.yuf.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Tab2ReceiveTimeActivity extends Activity{

	
	private ImageView backImageView;
	private Button okButton;
	public static Tab2ReceiveTimeActivity mActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActivity=this;
		setContentView(R.layout.tab2_recevice_time);
		backImageView=(ImageView)findViewById(R.id.tab2_recevice_time_back_imageview);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		
		okButton=(Button)findViewById(R.id.tab2_receive_time_ok_button);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mActivity,Tab2AddressActivity.class);
				startActivity(intent);
				
				// TODO Auto-generated method stub
				
			}
		});
	}
}
