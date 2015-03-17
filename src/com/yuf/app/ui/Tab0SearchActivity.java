package com.yuf.app.ui;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.yuf.app.db.DatabaseHelper;

public class Tab0SearchActivity extends Activity {

	private SearchView mSearchView_dishName;
	private SearchView mSearchView_dishDifficulty;
	private SearchView mSearchView_dishMethod;
	private SearchView mSearchView_dishTime;
	private SearchView mSearchView_dishPeople_amount;

	String dish_nameVal = "";
	String dish_difficultyVal = "";
	String dish_methodVal = "";
	String dish_timeVal = "";
	String dish_peopelamountVal = "";

	private ListView listView;
	private MyAdapter adapter;
	private Cursor mCursor;
	private Context mContext;

	private DatabaseHelper db_dishHelper;
	private ArrayList<JSONObject> dishs;
	
	private TitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab0_search);
		
		mContext = Tab0SearchActivity.this;
		db_dishHelper = new DatabaseHelper(mContext);
		try {
			initView();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void initView() throws JSONException {

		titleView = (TitleView)findViewById(R.id.title);
		titleView.setTitleText("搜索");
		titleView.setRightButton(R.drawable.notify);
		
		listView = (ListView) findViewById(R.id.tab0_search_listview);
		adapter = new MyAdapter();
		listView.setAdapter(adapter);

		mSearchView_dishName = (SearchView) findViewById(R.id.dish_name);
		mSearchView_dishName.setIconifiedByDefault(false);
		mSearchView_dishName.setQueryHint("查询菜名");
		mSearchView_dishName.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				dish_nameVal = newText;
				try {
					query();
					mSearchView_dishName.clearFocus();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				dish_nameVal = query;
				try {
					query();
					mSearchView_dishName.clearFocus();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}

		});

		mSearchView_dishDifficulty = (SearchView) findViewById(R.id.dish_difficulty);
		mSearchView_dishDifficulty.setIconifiedByDefault(false);
		mSearchView_dishDifficulty.setQueryHint("查询难度");
		mSearchView_dishDifficulty
				.setOnQueryTextListener(new OnQueryTextListener() {

					@Override
					public boolean onQueryTextChange(String newText) {
						// TODO Auto-generated method stub
						dish_difficultyVal = newText;
						try {
							query();
							mSearchView_dishDifficulty.clearFocus();
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}

					@Override
					public boolean onQueryTextSubmit(String query) {
						// TODO Auto-generated method stub
						dish_difficultyVal = query;
						try {
							query();
							mSearchView_dishDifficulty.clearFocus();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}

				});

		mSearchView_dishMethod = (SearchView) findViewById(R.id.dish_method);
		mSearchView_dishMethod.setIconifiedByDefault(false);
		mSearchView_dishMethod.setQueryHint("查询制作方法");
		mSearchView_dishMethod
				.setOnQueryTextListener(new OnQueryTextListener() {

					@Override
					public boolean onQueryTextChange(String newText) {
						// TODO Auto-generated method stub
						dish_methodVal = newText;
						try {
							query();
							mSearchView_dishMethod.clearFocus();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}

					@Override
					public boolean onQueryTextSubmit(String query) {
						// TODO Auto-generated method stub
						dish_methodVal = query;
						try {
							query();
							mSearchView_dishMethod.clearFocus();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}

				});

		mSearchView_dishTime = (SearchView) findViewById(R.id.dish_time);
		mSearchView_dishTime.setIconifiedByDefault(false);
		mSearchView_dishTime.setQueryHint("查询制作时间");
		mSearchView_dishTime.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				dish_timeVal = newText;
				try {
					query();
					mSearchView_dishTime.clearFocus();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				dish_timeVal = query;
				try {
					query();
					mSearchView_dishTime.clearFocus();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}

		});

		mSearchView_dishPeople_amount = (SearchView) findViewById(R.id.dish_people_amount);
		mSearchView_dishPeople_amount.setIconifiedByDefault(false);
		mSearchView_dishPeople_amount.setQueryHint("查询用餐人数");
		mSearchView_dishPeople_amount
				.setOnQueryTextListener(new OnQueryTextListener() {

					@Override
					public boolean onQueryTextChange(String newText) {
						// TODO Auto-generated method stub
						dish_peopelamountVal = newText;
						try {
							query();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}

					@Override
					public boolean onQueryTextSubmit(String query) {
						// TODO Auto-generated method stub
						dish_peopelamountVal = query;
						try {
							query();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}

				});

	}

	public void query() throws JSONException {
		String query = "";

		if (dish_nameVal.length() > 0)
			query = query.concat("dish_name = '" + dish_nameVal + "'");
		if (dish_difficultyVal.length() > 0) {
			if (query.equals(""))
				query = query.concat("dish_difficulty = '" + dish_difficultyVal + "'");
			else
				query = query.concat(" and dish_difficulty = '" + dish_difficultyVal
						+ "'");
		}

		if (dish_methodVal.length() > 0) {
			if (query.equals(""))
				query = query.concat("dish_method = '" + dish_methodVal + "'");
			else
				query = query.concat(" and dish_method = '" + dish_methodVal + "'");
		}

		if (dish_timeVal.length() > 0) {
			if (query.equals(""))
				query = query.concat("dish_time = '" + dish_timeVal + "'");
			else
				query = query.concat(" and dish_time = '" + dish_timeVal + "'");
		}

		if (dish_peopelamountVal.length() > 0) {
			if (query.equals(""))
				query = query.concat("dish_content='" + dish_peopelamountVal + "'");
			else
				query = query.concat(" and dish_content='" + dish_peopelamountVal + "'");
		}

		//Toast.makeText(mContext, query, Toast.LENGTH_LONG).show();
		dishs = db_dishHelper.query(query, null);

		adapter.notifyDataSetChanged();
	}

	protected class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public MyAdapter() {
			mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dishs == null ? 0 : dishs.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = mInflater.inflate(R.layout.item_dishs, null);

			TextView tv = (TextView) convertView
					.findViewById(R.id.text_dishName);

			JSONObject jObject = dishs.get(position);

			try {
				tv.setText(jObject.getString("dish_name"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return convertView;
		}

	}
	
	
	  @Override
	  public boolean onKeyDown(int keyCode, KeyEvent event)  
	    {  
	        if (keyCode == KeyEvent.KEYCODE_BACK )  
	        {  
	        	Tab0SearchActivity.this.finish();
	        	//Log.d("ZM", "jjjjjjj");
	  
	        }  
	          
	        return true;  
	          
	    }

}
