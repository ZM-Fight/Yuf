package com.yuf.app.ui;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.interpolator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.db.Address;
import com.yuf.app.db.Order;

public class Tab2AddressActivity extends Activity {
	private ImageView backImageView;
	private ImageView addImageView;
	private PullToRefreshListView addressListView;
	private MyListAdapter mAdapter;
	private ArrayList<Address> addressList;
	private String TAG="Tab2AddressActivity";
	
	private TextView zoneTextView;
	private TextView detailTextView;
	private TextView recevierTextView;
	private TextView phoneTextView;
	private SharedPreferences sharepPreferences;
	private Button okButton;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Address.positionOfStartAddress=-1;
		addressList = Address.readFromDb();
		mAdapter.notifyDataSetChanged();
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化数据
		
		addressList = Address.readFromDb();
		mAdapter=new MyListAdapter();
		sharepPreferences=getSharedPreferences("address", Context.MODE_PRIVATE);
		setContentView(R.layout.tab2_address_list);
		
		
		okButton=(Button)findViewById(R.id.tab2_address_list_ok_button);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				payOrders();
			}
		});
		
		zoneTextView=(TextView)findViewById(R.id.tab2_address_list_zone_textview);
		detailTextView=(TextView)findViewById(R.id.tab2_address_list_detail_textview);
		recevierTextView=(TextView)findViewById(R.id.tab2_address_list_receivername_textview);
		phoneTextView=(TextView)findViewById(R.id.tab2_address_list_phone_textview);
		
		//初始化默认地址
		zoneTextView.setText("区地址："+sharepPreferences.getString(Address.ZONESTRING, ""));
		detailTextView.setText("详细地址："+sharepPreferences.getString(Address.DETAILSTRING, ""));
		recevierTextView.setText("姓名："+sharepPreferences.getString(Address.NAMESTRING, ""));
		phoneTextView.setText("电话号码："+sharepPreferences.getString(Address.PHONESTRING, ""));
		
		
		
		backImageView=(ImageView)findViewById(R.id.tab2_address_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Address.positionOfStartAddress = -1;
				onBackPressed();
			}
		});
		addImageView=(ImageView)findViewById(R.id.tab2_address_list_add_imageview);
		addImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Address.positionOfStartAddress = -1;
				Intent intent=new Intent(Tab2AddressActivity.this,Tab2AddressEditActivity.class);
				startActivity(intent);
			}
		});
		
		
		addressListView=(PullToRefreshListView)findViewById(R.id.tab2_address_list);
		addressListView.setMode(Mode.PULL_FROM_END);
		addressListView.setAdapter(mAdapter);
		addressListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(Tab2AddressActivity.this.getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				refreshListView();
			}
       });
		addressListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
            @Override
			public void onLastItemVisible() {
				Log.d(TAG, "onLastItemVisible");
				if (addressList.size()<Address.numberOfAddress()) {
					loadingNextPage();
				}		
			}	});
    }
	private void payOrders() {
		new AlertDialog.Builder(this).setTitle("列表框").setItems(
			     new String[] { "货到付款", "在线支付" }, new DialogInterface.OnClickListener() {  
			    	    @Override  
			    	    public void onClick(DialogInterface dialog, int which) {  
			    	        // TODO Auto-generated method stub  
			    	        switch (which) {  
			    	        case 0:  
			    	        	payOnDelivery();
			    	            break;  
			    	        case 1:  
			    	            break;  
			    	        }  
			    	    } } ).setNegativeButton(
			     "取消", null).show();
		
		
	}
	protected void payOnDelivery() {
		ArrayList<Order>orders=Order.readAllSelectOrderFromDb();
		Log.d("ZM", orders.toString());
		for (int i = 0; i < orders.size(); i++) {
			Order.deleteFromDb(orders.get(i)._id);
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("userId", Integer.valueOf(UserInfo.getInstance().userid));
			jsonObject.put("orderPaymethod", "货到付款");
			jsonObject.put("orderAmount", orders.get(i).orderAmount);
			jsonObject.put("dishId", orders.get(i).dishId);
			jsonObject.put("orderPrice", orders.get(i).orderPrice);
			jsonObject.put("orderTime", orders.get(i).orderTime);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Method.POST,"http://110.84.129.130:8080/Yuf/order/insertOrder", jsonObject, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				Log.d(TAG, response.toString());
				try {
					if (response.getString("insertOrder").equals("success")) {
						Toast.makeText(Tab2AddressActivity.this, "下单成功", Toast.LENGTH_SHORT).show();
					}
					else {
						Toast.makeText(Tab2AddressActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		MyApplication.requestQueue.add(jsonObjectRequest);
		MyApplication.requestQueue.start();
		}
		
		// TODO Auto-generated method stub
		
	}
	protected void refreshListView() {
		Log.d(TAG, "Refresh!");
		if(addressList.size()<Address.numberOfAddress())
		{loadingNextPage();}
		else {
			Toast.makeText(Tab2AddressActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			DataBaseTask task=new DataBaseTask();
			task.execute();
		}
	}
	protected void loadingNextPage() {
		Log.d(TAG, "loadnextpage");
		ArrayList<Address> list0 = addressList;
		ArrayList<Address> list1 = Address.readFromDb();
		for(int i=0;i<list1.size();i++){
			list0.add(list1.get(i));
		}
		addressList=list0;
		DataBaseTask task=new DataBaseTask();
		task.execute();
	}
	
	private class MyListAdapter extends BaseAdapter
	{
		@Override
		public int getCount() {
			return addressList.size();
		}

		@Override
		public Object getItem(int position) {
			return addressList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null) {
				convertView=Tab2AddressActivity.this.getLayoutInflater().inflate(R.layout.tab2_address_list_item,null);
				
			}
			final int  index=position;
			Address address = addressList.get(position);
			Button defaultButton=(Button)convertView.findViewById(R.id.tab2_address_list_item_setdefault_button);
			defaultButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					setDefaultAddress(index);
					
					
					// TODO Auto-generated method stub
					
				}
			});
			
			TextView deleteTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_delete_textview);
			deleteTextView.setText("删除");
			deleteTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					deleteAddress(index);
					
				}
			});
			
			
			TextView nameTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_name_textview);
			nameTextView.setText(address.nameString);
			TextView zonetTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_zone_textview);
			zonetTextView.setText(address.zoneString);
			TextView detailTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_detailaddress_textview);
			detailTextView.setText(address.detailString);
			TextView phoneTextView=(TextView)convertView.findViewById(R.id.tab2_address_list_item_phone_textview);
			phoneTextView.setText(address.phoneString);
			return convertView;
		}

	}
	protected void deleteAddress(int index) {
		// TODO Auto-generated method stub
		Address.deleteFromDb(addressList.get(index)._id);
		addressList.remove(index);
		mAdapter.notifyDataSetChanged();
	}
	private void setDefaultAddress(int index) {
		
		Address address=addressList.get(index);
		Editor editor=sharepPreferences.edit();
		editor.putString(Address.ZONESTRING, address.zoneString);
		editor.putString(Address.DETAILSTRING, address.detailString);
		editor.putString(Address.NAMESTRING, address.nameString);
		editor.putString(Address.PHONESTRING, address.phoneString);
		editor.commit();
		zoneTextView.setText("区地址："+sharepPreferences.getString(Address.ZONESTRING, ""));
		detailTextView.setText("详细地址："+sharepPreferences.getString(Address.DETAILSTRING, ""));
		recevierTextView.setText("姓名："+sharepPreferences.getString(Address.NAMESTRING, ""));
		phoneTextView.setText("电话号码："+sharepPreferences.getString(Address.PHONESTRING, ""));
		
		
	}
	private class DataBaseTask extends AsyncTask<integer, integer, integer>
	{
        @Override
		protected integer doInBackground(integer... params) {
		return null;
		}
        @Override
		protected void onPostExecute(integer result) {
			super.onPostExecute(result);
			addressListView.onRefreshComplete();
			Log.d(TAG, "onRefreshComplete");
			mAdapter.notifyDataSetChanged();
		}
	}
}
