package com.yuf.app.ui;

import java.util.ArrayList;
import java.util.Timer;

import org.apache.http.client.UserTokenHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.CategorysEntity;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.adapter.MiddlePageAdapter;
import com.yuf.app.adapter.OutsidePagerAdapter;
import com.yuf.app.db.DBComment;
import com.yuf.app.db.DatabaseHelper;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.ui.indicator.TitlePageIndicator;

public class Main extends FragmentActivity {

	//全局变量
	
	private ViewPager mTabPager;
	private ImageView mTab0, mTab1, mTab2, mTab3;
	private int currIndex = 0;
	private ImageLoader mImageLoader;
	
	//四个tab 的view
	View view0;
	View view1;
	View view2;
	View view3;

	//tab0的变量
	private ArrayList<Fragment> tab0fragments;
	private ArrayList<CategorysEntity> tab0categorysEntities;
	private TitlePageIndicator tab0Indicator;
	private ViewPager tab0Viewpage;
	private ImageView tab0SearchImageView;
	private JSONObject recomnddish;
	//tab1的变量
	private ListView tab1ShareListView;
	private ListView tab1FocusListView;
	private TitlePageIndicator tab1Indicator;
	private ViewPager tab1Viewpage;
	private ArrayList<Fragment> tab1fragments;
	private ArrayList<CategorysEntity> tab1categorysEntities;
	private ImageView addImageView;

	//tab3
	private NetworkImageView tab3profileImageView;
	private TextView tab3nicknameTextView;
	private TextView tab3accountTextView;
	private TextView tab3levelnametTextView;
	private TextView tab3userfollowsTextView;
	private TextView tab3usercollectionTextView;

	public static FragmentActivity mainActivity;
	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		getUserInfo();
		setTab3UserInfo();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		mainActivity=this;
		setContentView(R.layout.main);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		//图片加载器
		mImageLoader=new ImageLoader(MyApplication.requestQueue,new BitmapCache());	
		
		initViewPage();
		//四模块初始化
		
		initTab0();
		initTab1();
		initTab2();
		initTab3();
	}

	
	private void initViewPage() {
		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mTabPager.setOffscreenPageLimit(4);
		
		LayoutInflater mLi = LayoutInflater.from(this);
 //导航界面布局初始化
		 view0=mLi.inflate(R.layout.main_tab_0, null);
		 view1 = mLi.inflate(R.layout.main_tab_1, null);
		 view2 = mLi.inflate(R.layout.main_tab_2, null);
		 view3 = mLi.inflate(R.layout.main_tab_3, null);	
		 
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view0);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		mTabPager.setAdapter(new OutsidePagerAdapter(views));
	
		
		mTab0 = (ImageView) findViewById(R.id.img_0);
		mTab1 = (ImageView) findViewById(R.id.img_1);
		mTab2 = (ImageView) findViewById(R.id.img_2);
		mTab3 = (ImageView) findViewById(R.id.img_3);
		mTab0.setOnClickListener(new MyOnClickListener(0));
		mTab1.setOnClickListener(new MyOnClickListener(1));
		mTab2.setOnClickListener(new MyOnClickListener(2));
		mTab3.setOnClickListener(new MyOnClickListener(3));
	}

	//tab2 onclick 函数的关联在xml文件中
	public void cookRemind(View view){
		Intent intent=new Intent(mainActivity,Tab2RemindActivity.class);
		startActivity(intent);
	}
	public void passBought(View view){
		Intent intent=new Intent(mainActivity,Tab2BougthActivity.class);
		startActivity(intent);
	}
	public void waitForPay(View view){
		Intent intent=new Intent(mainActivity,Tab2WaitForPayActivity.class);
		startActivity(intent);

	}
	

	//tab3 onclick 函数的关联在xml文件中
	public void onClickSettingInfo(View view){
		Intent	intent=new Intent(mainActivity,Tab3SetUserInfoActivity.class);
		startActivity(intent);
	}
public  void onClickMycollection(View view) {
	
	Intent	intent=new Intent(mainActivity,Tab3MyCollectionActivity.class);
	startActivity(intent);
}
public void onClickMywork(View view) {
	
	Intent	intent=new Intent(mainActivity,Tab3MyWorkActivity.class);
	startActivity(intent);
}
public void onClickMyInfo(View view){
	
	Intent	intent=new Intent(mainActivity,Tab3DegreeActivity.class);
	startActivity(intent);
}
public void onClickAboutus(View view){
	
	Intent	intent=new Intent(mainActivity,Tab3AboutUsActivity.class);
	startActivity(intent);
}
public void onClickMyfocus(View view){
	
	mTabPager.setCurrentItem(1);
	tab1Viewpage.setCurrentItem(1);
}	
	
public void onClickLogout(View view) {
	logout();
}
	
	private void initTab3() {
	tab3profileImageView=(NetworkImageView)view3.findViewById(R.id.tab3_profile);
	tab3profileImageView.setDefaultImageResId(R.drawable.no_pic);
	
	tab3accountTextView=(TextView)view3.findViewById(R.id.tab3_account);
	tab3levelnametTextView=(TextView)view3.findViewById(R.id.tab3_levelname);
	tab3nicknameTextView=(TextView)view3.findViewById(R.id.tab3_nickname);
	tab3userfollowsTextView=(TextView)view3.findViewById(R.id.tab3_myfocus_textview);
	tab3usercollectionTextView=(TextView)view3.findViewById(R.id.tab3_mycollection_textview);

}



	private void initTab2() {
		// TODO Auto-generated method stub
				// TODO Auto-generated method stub
			
		
	}



	private void initTab1() {
		// TODO Auto-generated method stub
		addImageView=(ImageView) view1.findViewById(R.id.tab1_addshare);
		addImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Main.this,Tab3AddWorkActivity.class);
				startActivity(intent);	
			}
		});
		addTab1ViewpageFragment();
	}



	private void initTab0() {

		tab0SearchImageView=(ImageView)view0.findViewById(R.id.search_imageView);
		tab0SearchImageView.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {	
				Intent intent=new Intent(Main.mainActivity,Tab0SearchActivity.class);
				startActivity(intent);
			}
		});

		getRecommendedDishSet();	//获取套餐信息
	}

	private void addTab1ViewpageFragment() {
		
		 	tab1fragments=new ArrayList<Fragment>();
			tab1categorysEntities=new ArrayList<CategorysEntity>();
			
//			tab1categorysEntities.add(new CategorysEntity("分享区"));
//			tab1fragments.add(new Tab1SocietyShareFragment());
//			tab1categorysEntities.add(new CategorysEntity("我的关注"));
//			tab1fragments.add(new Tab1SocietyForceFragment());
			
			tab1categorysEntities.add(new CategorysEntity("评论区"));
			tab1fragments.add(new Tab1_zm_comment());
			
			tab1Indicator=(TitlePageIndicator) view1.findViewById(R.id.tab1_indicator);
			tab1Viewpage=(ViewPager) view1.findViewById(R.id.tab1_pager);
			MiddlePageAdapter mInsidePageAdapter=new MiddlePageAdapter(getSupportFragmentManager());
			tab1Viewpage.setAdapter(mInsidePageAdapter);
			tab1Viewpage.setOffscreenPageLimit(1);
			mInsidePageAdapter.addFragments(tab1fragments,tab1categorysEntities);
			tab1Indicator.setViewPager(tab1Viewpage);
			tab1Indicator.setOnPageChangeListener( new MyPageChangeListener());

	}



	@Override
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
	}



	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}


@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				mTab0.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_0_pressed));
				if (currIndex == 1) {
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_1_normal));
				} else if (currIndex == 2) {
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_2_normal));
				}else if (currIndex == 3) {
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_3_normal));
				}
				
				break;
			case 1:
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_1_pressed));
				if (currIndex == 0) {
					mTab0.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_0_normal));
				} else if (currIndex == 2) {
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_2_normal));
				}else if (currIndex == 3) {
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_3_normal));
				}
				break;
			case 2:
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_2_pressed));
				if (currIndex == 0) {
					mTab0.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_0_normal));
				} else if (currIndex == 1) {
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_1_normal));
				}else if (currIndex == 3) {
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_3_normal));
				}
				break;
			case 3:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_3_pressed));

				if (currIndex == 0) {
					mTab0.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_0_normal));
				} else if (currIndex == 1) {
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_1_normal));
				}else if (currIndex == 2) {
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_2_normal));
				}
				break;
			default:
				break;
			}
			currIndex = arg0;
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	}
	
	
	private long frist_back_time=0;
	
	
	 
	 //双返回键退出
	// 此部分为了实现按两下返回退出
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		
				if (System.currentTimeMillis() - frist_back_time < 1500) {
					finish();
				
				}
				frist_back_time=System.currentTimeMillis();
	}
		return true;
	}
	
	//预留接口
	 class MyPageChangeListener implements OnPageChangeListener {
	        @Override
	        public void onPageScrollStateChanged(int arg0) {
	            // TODO Auto-generated method stub
	        }

	        @Override
	        public void onPageScrolled(int arg0, float arg1, int arg2) {
	            // TODO Auto-generated method stub
	        }

	        @Override
	        public void onPageSelected(int arg0) {
	            // TODO Auto-generated method stub
	           
	        }
	    }
	 
	 

private void logout() {

	JSONObject logJsonObject=new JSONObject();
	try{
	logJsonObject.put("userId",UserInfo.getInstance().getUserid());
	logJsonObject.put("sessionId",UserInfo.getInstance().getSessionid());
	}catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/user/logout", logJsonObject,  new Response.Listener<JSONObject>()  
    {  

        @Override  
        public void onResponse(JSONObject response)  
        {  
        	try {
				if(response.getInt("code")==0)
				{
					SharedPreferences sharepPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);
					Editor editor = sharepPreferences.edit();//获取编辑器
					editor.putBoolean("isLogined", false);
					editor.commit();
					
					finish();
					Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
					startActivity(intent);
					
					
				}
				else {
					Toast toast = Toast.makeText(getApplicationContext(),
						    "登出失败", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
             
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
private void setTab3UserInfo() {
	
	tab3accountTextView.setText(UserInfo.getInstance().getUseraccount());
	tab3levelnametTextView.setText(UserInfo.getInstance().getLevelname());
	tab3nicknameTextView.setText(UserInfo.getInstance().getUsername());
	tab3userfollowsTextView.setText("我的关注："+String.valueOf(UserInfo.getInstance().userfollows));
	tab3profileImageView.setDefaultImageResId(R.drawable.no_pic);
	//tab3profileImageView.setImageUrl("http://110.84.129.130:8080/Yuf"+UserInfo.getInstance().userpic, mImageLoader);
	tab3profileImageView.setImageResource(R.drawable.shoucang);
	tab3usercollectionTextView.setText("我的收藏："+String.valueOf(UserInfo.getInstance().userfollows));
	
}

	 private void getUserInfo()
	 {

		UserInfo tmpInfo=UserInfo.getInstance();
	    tmpInfo.setUserpic( "/images");//用户头像
	    tmpInfo.setLeveldiscout(0.9);
		tmpInfo.setUsername("tom");
		tmpInfo.setUserfollows(21);
		tmpInfo.setUserfans(122);
		tmpInfo.setUserpoints(12);
		tmpInfo.setUseraccount("DataBase");
		tmpInfo.setLevelname( "居家好男人");
		tmpInfo.setLevelpoints(100);
	 
		setTab3UserInfo();

	 }
	 private void getShareInfo()
	 {
		 JsonObjectRequest request=new JsonObjectRequest(Method.GET, "http://110.84.129.130:8080/Yuf/user/getUser/"+UserInfo.getInstance().getSessionid(), null,  new Response.Listener<JSONObject>()  
			     {  

			         @Override  
			         public void onResponse(JSONObject response)  
			         {  
			         	
			             Log.e("TAG", response.toString()); 
			             
			             UserInfo tmpInfo=UserInfo.getInstance();
			         

							try {
								tmpInfo.setLeveldiscout(response.getDouble("leveldiscount"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								tmpInfo.setUsername(response.getString("username"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								tmpInfo.setUserfollows(response.getInt("userfollows"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								tmpInfo.setUserfans(response.getInt("userfans"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								tmpInfo.setUserpoints(response.getInt("userpoints"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								tmpInfo.setUseraccount(response.getString("useraccount"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
							try {
								tmpInfo.setLevelname(response.getString("levelname"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								tmpInfo.setLevelpoints(response.getInt("levelpoints"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			             
			             
			             
			             
			             
			         }  
			     }, new Response.ErrorListener()  
			     {  

			         @Override  
			         public void onErrorResponse(VolleyError error)  
			         {  
			             Log.e("TAG", error.getMessage(), error);  
			         }  
			     });
			 	MyApplication.requestQueue.add(request);
			 	Log.d("liow","request start");
			 	MyApplication.requestQueue.start();
	 }
	 private void getRecommendedDishSet() {
		 JSONArray dishsetsArray = new JSONArray();
		 
		 DatabaseHelper db_dish = new DatabaseHelper(this);
		 db_dish.getWritableDatabase().close();
		 
		 DBComment db_comment = new DBComment(this);
		 db_comment.getWritableDatabase().close();
		 
		 
		 
		 for(int i = 0; i <3;i++){
			 dishsetsArray.put(new JSONArray());
		 }
		 tab0fragments=new ArrayList<Fragment>();
		tab0categorysEntities=new ArrayList<CategorysEntity>();
	
		try {
			tab0categorysEntities.add(new CategorysEntity("家庭套餐"));
			tab0fragments.add(new Tab0MyRecipeFragment(dishsetsArray.getJSONArray(0),1));
			tab0categorysEntities.add(new CategorysEntity("情侣套餐"));
			tab0fragments.add(new Tab0MyRecipeFragment(dishsetsArray.getJSONArray(1),2));
			tab0categorysEntities.add(new CategorysEntity("食客分享"));
			tab0fragments.add(new Tab0MyRecipeFragment(dishsetsArray.getJSONArray(2),3));
		} catch (JSONException e) {
			e.printStackTrace();
		}
			
			tab0Indicator=(TitlePageIndicator) view0.findViewById(R.id.tab0_indicator);
			tab0Viewpage=(ViewPager) view0.findViewById(R.id.tab0_pager);
			MiddlePageAdapter mInsidePageAdapter=new MiddlePageAdapter(getSupportFragmentManager());
			tab0Viewpage.setAdapter(mInsidePageAdapter);
			tab0Viewpage.setOffscreenPageLimit(3);
			mInsidePageAdapter.addFragments(tab0fragments,tab0categorysEntities);
			tab0Indicator.setViewPager(tab0Viewpage);
			tab0Indicator.setOnPageChangeListener( new MyPageChangeListener());
			tab0Viewpage.setCurrentItem(1);

			
			
//网络获取数据
//		 JsonObjectRequest request=new JsonObjectRequest(Method.GET, "http://110.84.129.130:8080/Yuf/dishset/getRecommendedDishset", null,  
//				 new Response.Listener<JSONObject>()  
//			     {  
//
//			         @Override  
//			         public void onResponse(JSONObject response)  
//			         {  
//			        	JSONArray dishsetsArray;
//						try {
//							dishsetsArray = response.getJSONArray("dishsets");
//							tab0fragments=new ArrayList<Fragment>();
//							tab0categorysEntities=new ArrayList<CategorysEntity>();
//							tab0categorysEntities.add(new CategorysEntity("家庭套餐"));
//							tab0fragments.add(new Tab0MyRecipeFragment(dishsetsArray.getJSONObject(0).getJSONArray("dishsetdetail")));
//							tab0categorysEntities.add(new CategorysEntity("情侣套餐"));
//							tab0fragments.add(new Tab0MyRecipeFragment(dishsetsArray.getJSONObject(1).getJSONArray("dishsetdetail")));
//							tab0categorysEntities.add(new CategorysEntity("食客分享"));
//							tab0fragments.add(new Tab0MyRecipeFragment(dishsetsArray.getJSONObject(2).getJSONArray("dishsetdetail")));
//							tab0Indicator=(TitlePageIndicator) view0.findViewById(R.id.tab0_indicator);
//							tab0Viewpage=(ViewPager) view0.findViewById(R.id.tab0_pager);
//							MiddlePageAdapter mInsidePageAdapter=new MiddlePageAdapter(getSupportFragmentManager());
//							tab0Viewpage.setAdapter(mInsidePageAdapter);
//							tab0Viewpage.setOffscreenPageLimit(3);
//							mInsidePageAdapter.addFragments(tab0fragments,tab0categorysEntities);
//							tab0Indicator.setViewPager(tab0Viewpage);
//							tab0Indicator.setOnPageChangeListener( new MyPageChangeListener());
//							tab0Viewpage.setCurrentItem(1);
//							Log.e("TAG", response.toString()); 
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//			             
//			             
//			         }  
//			     }, 
//			     new Response.ErrorListener()  
//			     {  
//
//			         @Override  
//			         public void onErrorResponse(VolleyError error)  
//			         {  
//			             Log.e("TAG", error.getMessage(), error);  
//			         }  
//			     });
//			 	MyApplication.requestQueue.add(request);
//			 	Log.d("liow","request start");
//			 	MyApplication.requestQueue.start();
	}
}// end this Main class
