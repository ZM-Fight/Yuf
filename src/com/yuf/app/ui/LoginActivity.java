package com.yuf.app.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.android.volley.Response;
import android.util.Log;
import com.android.volley.toolbox.StringRequest;
public class LoginActivity extends Activity {

	private Button loginBtn;
	private Button logonBtn;
	private EditText accountET;
	private EditText passwordET;
	private Button testBtn;
	private CheckBox rememberCheckBox;
	private SharedPreferences sharepPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		sharepPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
		loginBtn=(Button)findViewById(R.id.login_button);
		logonBtn=(Button)findViewById(R.id.logon_button);
		accountET=(EditText)findViewById(R.id.account_editText);
		passwordET=(EditText)findViewById(R.id.password_editText);
		accountET.setText(sharepPreferences.getString("account", ""));
		passwordET.setText(sharepPreferences.getString("password", ""));
		
		rememberCheckBox=(CheckBox)findViewById(R.id.remember_checkbox);
		if (sharepPreferences.getBoolean("isRemeber", false)) {
			rememberCheckBox.setChecked(true);
		}
		testBtn=(Button)findViewById(R.id.test_button_login);
		testBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),Main.class);
				finish();
				startActivity(intent);
				
			}
		});
		
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				login();
			}
			});
		
		
		logonBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent=new Intent(getApplicationContext(), LogonActivity.class);
				startActivity(intent);
			}
		});
	}


private void saveAccountPassword()
{
	
	Editor editor = sharepPreferences.edit();//获取编辑器
	editor.putString("account", accountET.getText().toString());
	editor.putString("password", passwordET.getText().toString());
	editor.putBoolean("isRemeber", true);
	editor.commit();//提交修改
}
private void login()
{

	if(rememberCheckBox.isChecked())
	{
		saveAccountPassword();
	}
	else {
		Editor editor = sharepPreferences.edit();//获取编辑器
		editor.putString("account","");
		editor.putString("password","");
		editor.putBoolean("isRemeber", false);
		editor.commit();//提交修改
	}
			
			// TODO Auto-generated method stub
	UserInfo.getInstance().setSessionid("sessionid");
	UserInfo.getInstance().setUserid("110");
	Editor editor = sharepPreferences.edit();//获取编辑器
	editor.putBoolean("isLogined", true);
	editor.commit();
	Intent intent=new Intent(getApplicationContext(),Main.class);
	startActivity(intent);
	finish();
			
	
	}
}
