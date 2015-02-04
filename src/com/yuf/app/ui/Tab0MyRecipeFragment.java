package com.yuf.app.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.adapter.OutsidePagerAdapter;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.mywidget.FoodViewPage;
import com.yuf.app.ui.indicator.CirclePageIndicator;

@SuppressLint("ValidFragment")
public class Tab0MyRecipeFragment extends Fragment {

	private int pageNumber;//1是家庭套餐，2是情侣套餐，3是食客套餐
	
	private Button detailButton;
	private Button commentbuttoButton;
	private Button collectionButton;
	private CirclePageIndicator foodindiactor;
	private FoodViewPage viewPager;
	private ArrayList<View> mViews;
	private JSONArray mdataArray;
	private ImageLoader mImageLoader;
	private TextView difficultyTextView;
	private TextView doseTextView;
	private TextView skillTextView;
	private TextView timeTextView;
	private TextView nameTextView;
	private int currentPageIndex;
	private  AlertDialog dlg;
	
	private int index ;
	
	public Tab0MyRecipeFragment(JSONArray _dataArray , int pageNumber) {
       //		mdataArray=_dataArray;
		this.pageNumber = pageNumber;
		
		setDataDish();//填充推荐菜谱的信息
		
		
	}
	private void setDataDish() {
		//自己添加的
		JSONObject zm1 = new JSONObject();
		try {
			zm1.put("dishdifficulty", 3.0);
			zm1.put("dishcollectionnum", 3);
			zm1.put("dishprice", 2.0);
			zm1.put("dishid", 1);
			zm1.put("dishcommentnum", 12);
			zm1.put("dishname","牛排");
			zm1.put("dishamount", 3);
			zm1.put("dishcooktime", "1小时");
			zm1.put("dishcookmethod", "煎");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		JSONObject zm2 = new JSONObject();
		try {
			zm2.put("dishdifficulty", 3.0);
			zm2.put("dishcollectionnum", 5);
			zm2.put("dishprice", 14.0);
			zm2.put("dishid", 2);
			zm2.put("dishcommentnum", 22);
			zm2.put("dishname","白灼虾");
			zm2.put("dishamount", 3);
			zm2.put("dishcooktime", "2小时");
			zm2.put("dishcookmethod", "煮");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		JSONObject zm3 = new JSONObject();
		try {
			zm3.put("dishdifficulty", 4.0);
			zm3.put("dishcollectionnum", 4);
			zm3.put("dishprice", 7.0);
			zm3.put("dishid", 3);
			zm3.put("dishcommentnum", 15);
			zm3.put("dishname","凤爪");
			zm3.put("dishamount", 3);
			zm3.put("dishcooktime", "3小时");
			zm3.put("dishcookmethod", "烧");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		JSONObject zm4 = new JSONObject();
		try {
			zm4.put("dishdifficulty", 5.0);
			zm4.put("dishcollectionnum", 15);
			zm4.put("dishprice", 20.0);
			zm4.put("dishid", 4);
			zm4.put("dishcommentnum", 3);
			zm4.put("dishname","剁椒鱼头");
			zm4.put("dishamount", 3);
			zm4.put("dishcooktime", "2小时");
			zm4.put("dishcookmethod", "煮");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		JSONObject zm5 = new JSONObject();
		try {
			zm5.put("dishdifficulty", 1.0);
			zm5.put("dishcollectionnum", 11);
			zm5.put("dishprice", 11.0);
			zm5.put("dishid", 5);
			zm5.put("dishcommentnum", 11);
			zm5.put("dishname","麻辣豆腐");
			zm5.put("dishamount", 4);
			zm5.put("dishcooktime", "0.5小时");
			zm5.put("dishcookmethod", "红烧");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		JSONObject zm6 = new JSONObject();
		try {
			zm6.put("dishdifficulty", 2.0);
			zm6.put("dishcollectionnum", 30);
			zm6.put("dishprice", 21.0);
			zm6.put("dishid", 6);
			zm6.put("dishcommentnum", 13);
			zm6.put("dishname","糖醋排骨");
			zm6.put("dishamount", 3);
			zm6.put("dishcooktime", "1小时");
			zm6.put("dishcookmethod", "烧");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		JSONObject zm7 = new JSONObject();
		try {
			zm7.put("dishdifficulty", 4.0);
			zm7.put("dishcollectionnum", 8);
			zm7.put("dishprice", 15.0);
			zm7.put("dishid", 7);
			zm7.put("dishcommentnum", 3);
			zm7.put("dishname","南乳粉蒸肉");
			zm7.put("dishamount", 3);
			zm7.put("dishcooktime", "1.5小时");
			zm7.put("dishcookmethod", "蒸");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		mdataArray=new JSONArray();
		mdataArray.put(zm1);
		mdataArray.put(zm2);
		mdataArray.put(zm3);
		mdataArray.put(zm4);
		mdataArray.put(zm5);
		mdataArray.put(zm6);
		mdataArray.put(zm7);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("Tab0MyRecipeFragment", "onCreateView");
		mImageLoader = new ImageLoader(MyApplication.requestQueue, new BitmapCache()); 	
		
		View view=inflater.inflate(R.layout.tab0_recipe_fragment,container,false);

		difficultyTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment__dishdifficulty_textview);
		doseTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_dishamount_textview);
		timeTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_dishcooktime_textview);
		nameTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_foodname_textview);
		skillTextView=(TextView)view.findViewById(R.id.tab0_recipe_fragment_skill_textview);
		
		commentbuttoButton=(Button)view.findViewById(R.id.comment_button);
		commentbuttoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialg();
			}
		});
		
		collectionButton=(Button)view.findViewById(R.id.collection_button);
		collectionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
			//	addCollectionRelationShip();
			}
		});
		
//查看全文		
		detailButton=(Button)view.findViewById(R.id.detail_button);
		detailButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Main.mainActivity,Tab0FoodActivity.class);
				
				Bundle bundle = new Bundle();                           //创建Bundle对象   
				try {
					bundle.putString("dishid",mdataArray.getJSONObject(currentPageIndex).getString("dishid") );
					bundle.putString("dishname",mdataArray.getJSONObject(currentPageIndex).getString("dishname") );
					bundle.putDouble("dishprice",mdataArray.getJSONObject(currentPageIndex).getDouble("dishprice") );
				} catch (JSONException e) {
					e.printStackTrace();
				}     //装入数据   
				
				intent.putExtras(bundle);                                //把Bundle塞入Intent里面  
				startActivity(intent);			
			}
		});

		try {
			initView();//填充数据
		} catch (JSONException e) {
			e.printStackTrace();
		}
		viewPager=(FoodViewPage)view.findViewById(R.id.recipe_fragment_viewpage);
		viewPager.setAdapter(new OutsidePagerAdapter(mViews) );
		viewPager.setOffscreenPageLimit(7);
		foodindiactor=(CirclePageIndicator)view.findViewById(R.id.circle_page_indicator);
		foodindiactor.setViewPager(viewPager);
		foodindiactor.setOnPageChangeListener(new MyPageChangeAdapter1());
	
		return  view;
	}
	
	protected void addCollectionRelationShip() {
	
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("userId",Long.valueOf( UserInfo.getInstance().userid));
			jsonObject.put("sessionId", UserInfo.getInstance().sessionid);
			jsonObject.put("dishId", mdataArray.getJSONObject(currentPageIndex).getLong("dishid"));
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/collection/addCollection", jsonObject, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				try {
					if (response.getInt("code")==0) {
						
						int currentCollectionnum=mdataArray.getJSONObject(currentPageIndex).getInt("dishcollectionnum");
						currentCollectionnum++;
						mdataArray.getJSONObject(currentPageIndex).put("dishcollectionnum",currentCollectionnum );
						collectionButton.setText(String.format("收藏(%s)",String.valueOf(currentCollectionnum)));
						Toast toast = Toast.makeText(Tab0MyRecipeFragment.this.getActivity(),
							     "收藏成功", Toast.LENGTH_SHORT);
							   toast.setGravity(Gravity.CENTER, 0, 0);
							   toast.show();
					}
					else {
						Toast toast = Toast.makeText(Tab0MyRecipeFragment.this.getActivity(),
							     "已收藏", Toast.LENGTH_SHORT);
							   toast.setGravity(Gravity.CENTER, 0, 0);
							   toast.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
		MyApplication.requestQueue.add(request);
		MyApplication.requestQueue.start();
	}
	
	protected void showDialg() {

		//显示评论对话框
		LayoutInflater factory = LayoutInflater.from(getActivity());
		final View textEntryView = factory.inflate(R.layout.dialog, null);
		final EditText editText=(EditText)textEntryView.findViewById(R.id.comment_comment_editText);
		Button commentButton =(Button)textEntryView.findViewById(R.id.comment_dialog_comment_button);
		commentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				commentDish(editText.getText().toString());
				dlg.dismiss();
			}
		});
		Button cancleButton=(Button)textEntryView.findViewById(R.id.comment_dialog_cancle_buttoon);
       cancleButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			dlg.dismiss();
		}
	});
		dlg = new AlertDialog.Builder(getActivity())
        .setView(textEntryView)
        .create();
        dlg.show();
		
	}

	private void initView() throws JSONException
	{
		mViews=new ArrayList<View>();
		//初始化7张图片
		
	     index = 0;
		
		for(int i=0;i<7;i++)
		{
			NetworkImageView imageView=new NetworkImageView(getActivity());
			if(pageNumber == 1){					//家庭套餐
				switch(i){
				case 0:
					imageView.setImageUrl("http://i3.meishichina.com/attachment/recipe/2014/06/11/20140611140717217157565.jpg", mImageLoader);
					
					break;
				case 1:
					imageView.setImageUrl("http://i3.meishichina.com/attachment/r/2012/06/07/p800_20120607131332315744565.jpg", mImageLoader);		
					
					break;
				case 2:				
					imageView.setImageUrl("http://pic14.nipic.com/20110502/558804_232038509000_2.jpg", mImageLoader);		
					break;
				case 3:
					imageView.setImageUrl("http://i3.meishichina.com/attachment/r/2012/06/07/p800_20120607131332315744565.jpg", mImageLoader);
					break;
				case 4:
					imageView.setImageUrl("http://pic37.nipic.com/20140106/4418117_204831367168_2.jpg", mImageLoader);		
					break;
				case 5:
					imageView.setImageUrl("http://pic35.nipic.com/20131113/7852156_124915686179_2.jpg", mImageLoader);
					break;
				case 6:
					imageView.setImageUrl("http://cp1.douguo.net/upload/caiku/9/d/2/yuan_9d44aefaf7d4518d0b0126b04fc2f0c2.jpg", mImageLoader);	
					break;	
				default:
					break;
				}
			}
			
			if(pageNumber == 2){					//情侣套餐
				switch(i){
				case 0:
					imageView.setImageUrl("http://pic35.nipic.com/20131113/7852156_124915686179_2.jpg", mImageLoader);
					break;
				case 1:
					imageView.setImageUrl("http://i3.meishichina.com/attachment/r/2012/06/07/p800_20120607131332315744565.jpg", mImageLoader);
					break;
				case 2:				
					imageView.setImageUrl("http://pic14.nipic.com/20110502/558804_232038509000_2.jpg", mImageLoader);	
					break;
				case 3:
					imageView.setImageUrl("http://i3.meishichina.com/attachment/r/2012/06/07/p800_20120607131332315744565.jpg", mImageLoader);		
					break;
				case 4:
					imageView.setImageUrl("http://pic37.nipic.com/20140106/4418117_204831367168_2.jpg", mImageLoader);		
					break;
				case 5:
					imageView.setImageUrl("http://i3.meishichina.com/attachment/recipe/2014/06/11/20140611140717217157565.jpg", mImageLoader);		
					break;
				case 6:
					imageView.setImageUrl("http://cp1.douguo.net/upload/caiku/9/d/2/yuan_9d44aefaf7d4518d0b0126b04fc2f0c2.jpg", mImageLoader);	
					break;	
				default:
					break;
				}
			}
			
			if(pageNumber == 3){					//食客分享
				switch(i){
				case 0:
					imageView.setImageUrl("http://cp1.douguo.net/upload/caiku/9/d/2/yuan_9d44aefaf7d4518d0b0126b04fc2f0c2.jpg", mImageLoader);	
				
					
					break;
				case 1:
					imageView.setImageUrl("http://i3.meishichina.com/attachment/r/2012/06/07/p800_20120607131332315744565.jpg", mImageLoader);
					break;
				case 2:				
					imageView.setImageUrl("http://pic35.nipic.com/20131113/7852156_124915686179_2.jpg", mImageLoader);
					break;
				case 3:
					imageView.setImageUrl("http://i3.meishichina.com/attachment/r/2012/06/07/p800_20120607131332315744565.jpg", mImageLoader);	
					break;
				case 4:
					imageView.setImageUrl("http://pic37.nipic.com/20140106/4418117_204831367168_2.jpg", mImageLoader);		
					break;
				case 5:
					imageView.setImageUrl("http://i3.meishichina.com/attachment/recipe/2014/06/11/20140611140717217157565.jpg", mImageLoader);		
					break;
				case 6:
					imageView.setImageUrl("http://pic14.nipic.com/20110502/558804_232038509000_2.jpg", mImageLoader);	
					break;	
				default:
					break;
				}
			}
			
			mViews.add(imageView);
		}

		JSONObject jObject = mdataArray.getJSONObject(index);
		difficultyTextView.setText(String.valueOf(jObject.getDouble("dishdifficulty")));
		doseTextView.setText(String.valueOf(jObject.getInt("dishamount")));
		timeTextView.setText(jObject.getString("dishcooktime"));
		commentbuttoButton.setText(String.format("评论(%s)",String.valueOf(jObject.getInt("dishcommentnum"))));
		collectionButton.setText(String.format("收藏(%s)",String.valueOf(jObject.getInt("dishcollectionnum"))));
		nameTextView.setText(jObject.getString("dishname"));
		skillTextView.setText(jObject.getString("dishcookmethod"));
	}
	
	private void commentDish(String commentContent) {
			JSONObject jsonObject=new JSONObject();
			try {
				jsonObject.put("userId",Integer.valueOf(UserInfo.getInstance().getUserid()));
				JSONObject mjsonObject=mdataArray.getJSONObject(currentPageIndex);
				jsonObject.put("dishId", mjsonObject.getInt("dishid"));
				jsonObject.put("dishcommentContent", commentContent);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			JsonObjectRequest request=new JsonObjectRequest(Method.POST,"http://110.84.129.130:8080/Yuf/dishcomment/postDishcomment/", jsonObject, new Response.Listener<JSONObject>()  
			        {  
	
	            @Override  
	            public void onResponse(JSONObject response)  
	            {
	            	try {
						if (response.getInt("code")==0) {
							
							int currentDishCommentnum=mdataArray.getJSONObject(currentPageIndex).getInt("dishcommentnum");
							currentDishCommentnum++;
							mdataArray.getJSONObject(currentPageIndex).put("dishcommentnum",currentDishCommentnum );
							commentbuttoButton.setText(String.format("评论(%s)",String.valueOf(currentDishCommentnum)));
							Toast toast=Toast.makeText(getActivity().getApplicationContext(), "评论成功", Toast.LENGTH_SHORT);
							toast.show();
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
	
	class MyPageChangeAdapter1 implements OnPageChangeListener
	{

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			currentPageIndex=arg0;
			JSONObject jObject;
			try {
				jObject = mdataArray.getJSONObject(arg0);
				difficultyTextView.setText(String.valueOf(jObject.getDouble("dishdifficulty")));
				doseTextView.setText(String.valueOf(jObject.getInt("dishamount")));
				timeTextView.setText(jObject.getString("dishcooktime"));
				commentbuttoButton.setText(String.format("评论(%s)", String.valueOf(jObject.getInt("dishcommentnum"))));
				collectionButton.setText(String.format("收藏(%s)",String.valueOf(jObject.getInt("dishcollectionnum"))));
				nameTextView.setText(jObject.getString("dishname"));
				skillTextView.setText(jObject.getString("dishcookmethod"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}		
		}
}
