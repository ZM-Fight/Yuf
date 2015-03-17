package com.yuf.app.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleView extends RelativeLayout{

	private ImageView leftView;
	private TextView titleText;
	private ImageView rightView;
	
	public TitleView(Context context,AttributeSet attrs) {
		super(context,attrs);
		LayoutInflater.from(context).inflate(R.layout.title, this ,true);
		
		leftView = (ImageView)findViewById(R.id.imageView);
		titleText = (TextView)findViewById(R.id.text);
		
		leftView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				((Activity)getContext()).finish();
			}
		});
	}
	
	public void setTitleText(String text){
		titleText.setText(text);
	}

	public void setLeftListener(OnClickListener listener ){
		leftView.setOnClickListener(listener);
	}

	public void setRightListener(OnClickListener listener ){
		rightView.setOnClickListener(listener);
	}


	@SuppressLint("NewApi")
	public void setRightButton(int id){
		RelativeLayout.LayoutParams params  = new RelativeLayout.LayoutParams(
				(int) getDefaultSize(TypedValue.COMPLEX_UNIT_DIP, 30),
				(int) getDefaultSize(TypedValue.COMPLEX_UNIT_DIP, 30));
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		
		rightView = new ImageView(getContext());
		rightView.setLayoutParams(params);
		rightView.setBackgroundResource(id);
	}
	
}
