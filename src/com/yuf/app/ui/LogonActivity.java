 package com.yuf.app.ui;

import javax.xml.datatype.Duration;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuf.app.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LogonActivity extends Activity {

	private Button submitButton;
	private EditText accountEditText;
	private EditText paswordeEditText;
	private EditText confirmEditText;
	private EditText nameEditText;
	private ImageView backImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logon_activity);
		submitButton=(Button)findViewById(R.id.logon_submit_button);
		accountEditText=(EditText)findViewById(R.id.logon_account_edittext);
		paswordeEditText=(EditText)findViewById(R.id.logon_password_editText);
		confirmEditText=(EditText)findViewById(R.id.logon_confirm_edittext);
		nameEditText=(EditText)findViewById(R.id.logon_name_edittext);
		
		backImageView=(ImageView)findViewById(R.id.logon_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				onBackPressed();// TODO Auto-generated method stub
				
			}
		});
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//监测两次密码输入是否一致
				if (!confirmEditText.getText().toString().equals(paswordeEditText.getText().toString())) {
					
					Toast toast = Toast.makeText(getApplicationContext(),
							"密码不一致", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					confirmEditText.setText("");
					paswordeEditText.setText("");
				}
				
				JSONObject logJsonObject=new JSONObject();
				try{
				//
				logJsonObject.put("userAccount", accountEditText.getText().toString());
				logJsonObject.put("userName", nameEditText.getText().toString());
				logJsonObject.put("userPassword",paswordeEditText.getText().toString());
				}catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/user/logon", logJsonObject,  new Response.Listener<JSONObject>()  
	            {  
	  
	                @Override  
	                public void onResponse(JSONObject response)  
	                {  
	                	try {
	                		//注册成功，提示注册成功，后就调用登陆接口进入Main
	                		
							if(response.getInt("code")==0)
							{
								Log.d("liow", "注册成功");

								finish();
							}
							else {
								Toast toast = Toast.makeText(getApplicationContext(),
									     response.getString("msg"), Toast.LENGTH_SHORT);
									   toast.setGravity(Gravity.CENTER, 0, 0);
									   toast.show();
									   if (response.getInt("code")==2) 
										   nameEditText.setText("");
									else 
										accountEditText.setText("");
									
									   
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                    Log.e("TAG", response.toString());  
	                }  
	            }, new Response.ErrorListener()  
	            {  
	  
	                @Override  
	                public void onErrorResponse(VolleyError error)  
	                {  
	                    Log.e("TAG", error.getMessage(), error);  
	                }  
	            });
	//将JsonObjectRequest 加入RequestQuene
	MyApplication.requestQueue.add(request);
	Log.d("liow","request start");
	MyApplication.requestQueue.start();
			}
		});
		
		
	}

}
 