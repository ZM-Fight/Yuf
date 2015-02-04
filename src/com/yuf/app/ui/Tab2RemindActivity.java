package com.yuf.app.ui;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageView;

public class Tab2RemindActivity extends Activity {
	private ImageView backImageView;
	private static int choose;
	private Chronometer ch;
	private MediaPlayer mMediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_time_remind);
		choose=0;
		mMediaPlayer = MediaPlayer.create(Tab2RemindActivity.this,  
	            RingtoneManager.getActualDefaultRingtoneUri(Tab2RemindActivity.this,RingtoneManager.TYPE_RINGTONE));  
		ch = (Chronometer) findViewById(R.id.Chronometer);
		ch.setOnChronometerTickListener(new OnChronometerTickListener()
		{
			@Override
			public void onChronometerTick(Chronometer ch)
			{
				// 如果从开始计时到现在超过了20s。
				if (SystemClock.elapsedRealtime() - ch.getBase() > choose * 1000* 60)
				{
					ch.stop();
					mMediaPlayer.setLooping(false);  
				    mMediaPlayer.start();  
				}
			}
		});
		backImageView=(ImageView)findViewById(R.id.tab2_remind_back_ImageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
	public void Tab2RemindRadioButton(View view){
		switch(view.getId()){
		case R.id.Tab2RemindRadioButton1:
			choose = 10;
			break;
		case R.id.Tab2RemindRadioButton2:
			choose = 5;
			break;
		case R.id.Tab2RemindRadioButton3:
			choose = 3;
			break;
		default:
			break;
		}
	}
	
	public void start(View view){
		// 设置开始计时时间
		ch.setBase(SystemClock.elapsedRealtime());
		// 启动计时器
		ch.start();
	}
	public void stop(View view){
	 	ch.stop();
		mMediaPlayer.stop();
	}

}
