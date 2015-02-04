package com.yuf.app.ui;

import java.security.GeneralSecurityException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuf.app.MyApplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentListAcitvity extends Activity {
private PullToRefreshListView listView;
private ImageView backImageView;
private TextView titleTextView;
private JSONArray jsonArray;
private boolean isEnd;
private int dishid;
private int currentpage;
private MylistAdapter listAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab0_comment_acitivity);
		dishid=getIntent().getExtras().getInt("dishid");
		currentpage=0;
		jsonArray=new JSONArray();
		titleTextView=(TextView)findViewById(R.id.tab0_comment_title_textview);
		listView=(PullToRefreshListView)findViewById(R.id.tab0_comment_acitivity_listview);
		backImageView=(ImageView)findViewById(R.id.tab0_comment_activity_back_imageview);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				onBackPressed();// TODO Auto-generated method stub
				
}
});
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				isEnd=false;
				jsonArray=new JSONArray();
				listView.setMode(Mode.BOTH);
				currentpage=0;
				getNextPage();
			}
		});
		
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener(
				) {

					@Override
					public void onLastItemVisible() {
						if(!isEnd)
						getNextPage();
						// TODO Auto-generated method stub
						
					}
		});
		listAdapter=new MylistAdapter();
		listView.setAdapter(listAdapter);
		listView.setMode(Mode.BOTH);
		getNextPage();
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
				convertView=CommentListAcitvity.this.getLayoutInflater().inflate(R.layout.tab0_comment_item,null);
				
			}
			JSONObject jsonObject;
			try {
				jsonObject = jsonArray.getJSONObject(position);
				TextView nameTextView=(TextView)convertView.findViewById(R.id.tab0_comment_item_name_textview);
				TextView contentTextView=(TextView)convertView.findViewById(R.id.tab0_comment_item_content_textview);
				nameTextView.setText(jsonObject.getString("username"));
				contentTextView.setText(jsonObject.getString("dishcommentcontent"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// TODO Auto-generated method stub
			return convertView;
		}
		
		}


	private void getNextPage()
	{
		JsonObjectRequest request=new JsonObjectRequest(Method.GET,String.format("http://110.84.129.130:8080/Yuf/dishcomment/getDishcomment/%d/%d",dishid,++currentpage), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if (response.getInt("code")==0) {
						jsonArray=MyApplication.joinJSONArray(jsonArray, response.getJSONArray("dishcomment"));
						if (currentpage>=response.getInt("maxpagenum")) {
							listView.setMode(Mode.PULL_FROM_START);
							Toast.makeText(CommentListAcitvity.this, "End of List!", Toast.LENGTH_SHORT).show();
							isEnd=true;
						}
						
					
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listView.onRefreshComplete();
				listAdapter.notifyDataSetChanged();
			}
		}, new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}});
		MyApplication.requestQueue.add(request);
		MyApplication.requestQueue.start();
		
	}

}
