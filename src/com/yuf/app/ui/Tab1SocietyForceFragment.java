package com.yuf.app.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.BitmapCache;


import android.R.string;
import android.app.NativeActivity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.pdf.PdfDocument.Page;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Tab1SocietyForceFragment extends Fragment {

	private PullToRefreshListView listView;
	private ImageLoader mImageLoader;
	private JSONArray jsonArray;
	private int currentPage;
	private MylistAdapter mAdaAdapter;
	private Boolean isEnd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		mImageLoader=new ImageLoader(MyApplication.requestQueue,new BitmapCache());
		View view=inflater.inflate(R.layout.tab1_myfocus,container,false);
		mAdaAdapter=new MylistAdapter();
		jsonArray=new JSONArray();
		listView=(PullToRefreshListView)view.findViewById(R.id.tab1_myfocus_listview);
		listView.setMode(Mode.PULL_FROM_START);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				jsonArray=new JSONArray();
				currentPage=0;
				isEnd=false;
				getFocusNextPage();
			
			}
		});
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {@Override
			public void onLastItemVisible() {
			
			if (!isEnd) {
				getFocusNextPage();
			}
			
	}	});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent=new Intent(Tab1SocietyForceFragment.this.getActivity(),
						Tab3MyWorkActivity.class);
				Bundle bundle = new Bundle();   
				try {
					bundle.putInt("userId", jsonArray.getJSONObject(position-1).getInt("userid"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtras(bundle);
				startActivity(intent);
				// TODO Auto-generated method stub
				
			}
		});
		listView.setAdapter(mAdaAdapter);
		getFocusNextPage();
		return  view;
	}

	private	void getFocusNextPage()
		{
		
	
			JsonObjectRequest request=new JsonObjectRequest(Method.GET, String.format("http://110.84.129.130:8080/Yuf/relation/getFollowsInfo/%d/%d", Integer.valueOf(UserInfo.getInstance().userid),++currentPage), null,  new Response.Listener<JSONObject>()  
	        {   @Override  
	            public void onResponse(JSONObject response)  
	            {  
						try {
							jsonArray=MyApplication.joinJSONArray(jsonArray, response.getJSONArray("followsData"));
							if (response.getInt("currentPage")>=response.getInt("followsMaxPage")) {
								listView.setMode(Mode.PULL_FROM_START);
								Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
								isEnd=true;
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						listView.onRefreshComplete();
						mAdaAdapter.notifyDataSetChanged();
	                 
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
	Log.d("liow","request start");
	MyApplication.requestQueue.start();
			
		}

	private class MylistAdapter extends BaseAdapter
	{
	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			Log.d("tab1share", String.valueOf(jsonArray.length()));
			return jsonArray.length();
	
		}
	
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			try {
				return jsonArray.getJSONObject(position);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Log.d("mywork", "get");
			if (convertView==null) {
				convertView=getActivity().getLayoutInflater().inflate(R.layout.tab1_myfocus_list_item,null);
				
			}
			JSONObject jsonObject;
			try {
				jsonObject = jsonArray.getJSONObject(position);
				TextView nameTextView=(TextView) convertView.findViewById(R.id.myfocus_name_text);
				nameTextView.setText(jsonObject.getString("username"));
				TextView accountTextView=(TextView)convertView.findViewById(R.id.myfocus_account_text);
				accountTextView.setText(String.format("账号：%s", jsonObject.getString("useraccount")));
				NetworkImageView headImageView=(NetworkImageView)convertView.findViewById(R.id.myfocus_head_img);
				headImageView.setDefaultImageResId(R.drawable.no_pic);
				headImageView.setImageUrl(String.format("http://110.84.129.130:8080/Yuf%s", jsonObject.getString("useravatarurl")),mImageLoader);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// TODO Auto-generated method stub
			return convertView;
		}
		
		}
	
	


}
