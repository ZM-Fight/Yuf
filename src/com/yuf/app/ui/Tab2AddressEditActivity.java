package com.yuf.app.ui;

import com.yuf.app.db.Address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Tab2AddressEditActivity extends Activity{
private ImageView backImageView;
private Button saveButton;
private EditText name,phoneNumber,zoneAddress,detailAddress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_address_edit);
		Initialization();
		backImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			onBackPressed();
			}
		});
		//save the address
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(name.getText().toString().equals("")||phoneNumber.getText().toString().equals("")||zoneAddress.getText().toString().equals("")||detailAddress.getText().toString().equals("")){
					Toast.makeText(Tab2AddressEditActivity.this, "请完善信息！", Toast.LENGTH_SHORT).show();
				}
				else {
					saveToDB();//save the address to DB
				}
			}
	});
	}
	private void Initialization() {
		backImageView=(ImageView)findViewById(R.id.tab2_address_edit_back_imageView);
		saveButton = (Button)findViewById(R.id.tab2_address_ok_button);
		name = (EditText)findViewById(R.id.editText3);
		phoneNumber = (EditText)findViewById(R.id.editText2);
		zoneAddress = (EditText)findViewById(R.id.editText1);
		detailAddress = (EditText)findViewById(R.id.editText4);
	}
	private void saveToDB() {
		Address address = new Address();
		address.nameString = name.getText().toString();
		address.phoneString = phoneNumber.getText().toString();
		address.zoneString = zoneAddress.getText().toString();
		address.detailString = detailAddress.getText().toString();
		address.isDefault = 0;
		address.writeToDb();
		Toast.makeText(Tab2AddressEditActivity.this, "地址已存储！", Toast.LENGTH_SHORT).show();
		onBackPressed();
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Tab2AddressEditActivity.this.finish();
			Intent intent = new Intent(Tab2AddressEditActivity.this, Tab2AddressActivity.class);
			startActivity(intent);
		  }
		return super.onKeyDown(keyCode, event);
	}
    
}
