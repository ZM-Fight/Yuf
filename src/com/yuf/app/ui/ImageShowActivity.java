package com.yuf.app.ui;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.BitmapCache;

public class ImageShowActivity extends Activity {
	private NetworkImageView imageView;
	private ImageLoader loader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		setContentView(R.layout.imageshow_activity);
		imageView=(NetworkImageView)findViewById(R.id.imageview);
		imageView.setImageUrl(getIntent().getStringExtra("imageurl"),loader);
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.image_zoom_in, R.anim.image_zoom_out);

	}
	
	
	

}
