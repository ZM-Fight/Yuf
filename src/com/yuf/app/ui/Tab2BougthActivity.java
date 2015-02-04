package com.yuf.app.ui;


import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.BitmapCache;

public class Tab2BougthActivity extends Activity {
	private ImageView backImageView;
	private ImageLoader mImageLoader;
	private PullToRefreshListView listView;
	private MyListAdapter mAdapter;
	private JSONArray jsonArray;
	private Boolean isEnd;
	private int currentPage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mImageLoader=new ImageLoader(MyApplication.requestQueue,new BitmapCache());
		currentPage=0;
		isEnd=false;
		jsonArray=new JSONArray();
		mAdapter=new MyListAdapter();
		setContentView(R.layout.tab2_bought);
		listView=(PullToRefreshListView)findViewById(R.id.tab2_bougth_listview);
		listView.setAdapter(mAdapter);
		listView.setMode(Mode.PULL_FROM_START);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
           @Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        	   String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				jsonArray=new JSONArray();
				currentPage=0;
				isEnd=false;
				getBougthNextPage();
			}
});
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				if (!isEnd) {
					getBougthNextPage();
				}
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(Tab2BougthActivity.this,
				Tab0FoodActivity.class);
				Bundle bundle = new Bundle();  
				try {
					bundle.putString("dishid",jsonArray.getJSONObject(position-1).getString("dishid").toString());
					bundle.putString("dishname",jsonArray.getJSONObject(position-1).getString("dishname").toString());
					bundle.putBoolean("isSeeJust",false);
				} catch (Exception e) {
					e.printStackTrace();
				}//创建Bundle对象   
				intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
				startActivity(intent);
			}
		});
		
		getBougthNextPage();
		
		backImageView=(ImageView)findViewById(R.id.tab2_bougth_back_imageView);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	private	void getBougthNextPage()
	{
	
 
		JsonObjectRequest request=new JsonObjectRequest(Method.GET, String.format("http://110.84.129.130:8080/Yuf/order/getOrderInfo/%s/%d", UserInfo.getInstance().userid,++currentPage), null,  new Response.Listener<JSONObject>()  
        {  

            @Override  
            public void onResponse(JSONObject response)  
            {  
					try {
						jsonArray=MyApplication.joinJSONArray(jsonArray, response.getJSONArray("orderData"));
						if (response.getInt("currentPage")>=response.getInt("orderMaxPage")) {
							
							Toast.makeText(Tab2BougthActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
							isEnd=true;
							
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					listView.onRefreshComplete();
					mAdapter.notifyDataSetChanged();
                 
        }}, new Response.ErrorListener()  
        {  

            @Override  
            public void onErrorResponse(VolleyError error)  
            {  
                Log.e("TAG", error.getMessage(), error);  
            }  
        });
		//将JsonObjectRequest 加入RequestQuene
        MyApplication.requestQueue.add(request);
        Log.d("zm","request start");
        MyApplication.requestQueue.start();
}
	class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return jsonArray.length();
		}

		@Override
		public Object getItem(int position) {
			try {
				return jsonArray.getJSONObject(position);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null) {
				convertView=Tab2BougthActivity.this.getLayoutInflater().inflate(R.layout.tab2_bougth_item,null);
			}
			JSONObject jsonObject;
			try {
				jsonObject = jsonArray.getJSONObject(position);
				TextView nameOfOrder=(TextView) convertView.findViewById(R.id.tab2_bought_item_name);
				nameOfOrder.setText(jsonObject.getString("dishname"));
				TextView priceOfOrder=(TextView)convertView.findViewById(R.id.tab2_bought_item_price);
			    priceOfOrder.setText( jsonObject.getString("orderprice"));
				NetworkImageView orderImageView=(NetworkImageView)convertView.findViewById(R.id.tab2_bought_item_img);
				orderImageView.setDefaultImageResId(R.drawable.meat);
				orderImageView.setImageUrl(String.format("http://110.84.129.130:8080/Yuf%s", jsonObject.getString("dishpicurl")),mImageLoader);
				TextView statusOfOrder=(TextView) convertView.findViewById(R.id.status);
				statusOfOrder.setText(jsonObject.getString("orderstatus"));
				TextView timeTextView=(TextView)convertView.findViewById(R.id.tab2_bought_item_time);
				timeTextView.setText(timeString(jsonObject.getLong("ordertime")));
				TextView amountTextView=(TextView)convertView.findViewById(R.id.tab2_bought_item_amount);
				amountTextView.setText("份数："+jsonObject.getString("orderamount"));
			
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return convertView;
		}
	}
	private String timeString(long i) {
		// TODO Auto-generated method stub
		long currentTime = i;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(currentTime);
		return formatter.format(date);
	}
}
