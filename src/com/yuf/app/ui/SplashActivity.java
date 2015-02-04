package com.yuf.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;


public class SplashActivity extends Activity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.start_activity, null);
        setContentView(view);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goHome();
                    }
                }, 500);
            }
        });
      
    }

    protected void onResume() {
        super.onResume();
    }

    private void goHome() {
    	SharedPreferences sharepPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    	if (sharepPreferences.getBoolean("isLogined", false)) {
    		Intent intent=new Intent(getApplicationContext(), Main.class);
    		startActivity(intent);
    		finish();
		}
    	else {
			
    		Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
    		startActivity(intent);
    		finish();
		}
    }

   

}
