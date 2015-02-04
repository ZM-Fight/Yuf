package com.yuf.app.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.onekeyshare.OnekeyShare;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.db.Order;
import com.yuf.app.http.extend.BitmapCache;
@SuppressLint("ValidFragment")
public class Tab0FoodCookFragment extends Fragment{
	private TextView comment;
	 private TextView showComment;
	 private TextView collection;
	 private TextView share;
	 private TextView addcart;
	private ScrollView mScrollView; 
	private LinearLayout mLinearLayout;
	private float start_y=0;
	private boolean isDisappear=false;
	private JSONArray stepJsonObject;
	/**
	 * 菜谱信息
	 * “dishdifficulty”: float,
		“dishcollectionnum”: int,
		“dishid”: int,
		“dishcommentnum”: int,
		“dishname”: String,
		“dishpicurl”: String,
		“dishamount”: int,
		“dishcooktime”: String,
		“dishprice”: double,
		“dishcookmethod”: String

	 */
	private JSONObject dishInfoJsonObject;
	private ImageLoader mImageLoader;
	private  AlertDialog dlg;
	private TextView hardLevelTextview;
	private TextView doseTextView;
	private TextView skillTextView;
	private TextView timeTextView;
	private NetworkImageView foodImageView;
	
	private int dishID ;
	
	public Tab0FoodCookFragment(int number) {
		
		dishID = number;
		
		initDataBase();
		
	}

	private void initDataBase() {
stepJsonObject = new JSONArray();
		
		JSONObject zm1 = new JSONObject();
		try {
			zm1.put("recipedetail", "炒菜");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		JSONObject zm2 = new JSONObject();
		try {
			zm2.put("recipedetail", "炒菜");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		stepJsonObject.put(zm1);
		stepJsonObject.put(zm2);
		
		dishInfoJsonObject = new JSONObject();
		try {
			dishInfoJsonObject.put("dishdifficulty", 4.0);
			dishInfoJsonObject.put("dishcollectionnum", 3);
			dishInfoJsonObject.put("dishprice", 0.0);
			dishInfoJsonObject.put("dishid", 1);
			dishInfoJsonObject.put("dishcommentnum", 22);
			dishInfoJsonObject.put("dishname","红烧茄子");
			dishInfoJsonObject.put("dishamount", 5);
			dishInfoJsonObject.put("dishamount", 2);
			dishInfoJsonObject.put("dishcooktime", "1小时");
			dishInfoJsonObject.put("dishcookmethod", "烧");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}

	@SuppressLint("NewApi") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());			
		View view=inflater.inflate(R.layout.tab0_food_cook,container,false);
		
		foodImageView=(NetworkImageView)view.findViewById(R.id.tab0_cookfood_foodimage);
		
		hardLevelTextview=(TextView)view.findViewById(R.id.tab0_cookfood__dishdifficulty_textview);
		doseTextView=(TextView)view.findViewById(R.id.tab0_cookfood_dose_textview);
		skillTextView=(TextView)view.findViewById(R.id.tab0_cookfood_skill_textview);
		timeTextView=(TextView)view.findViewById(R.id.tab0_cookfood_time_textview);
		
		comment=(TextView)view.findViewById(R.id.comment_textView);
		comment.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//showDialg();
			}
		});
		showComment=(TextView)view.findViewById(R.id.show_comment_textView);
		showComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(), CommentListAcitvity.class);
				Bundle bundle=new Bundle();
				try {
					bundle.putInt("dishid", dishInfoJsonObject.getInt("dishid"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtras(bundle);
				startActivity(intent);
				
				
			}
		});
		//收藏
		collection=(TextView)view.findViewById(R.id.collection_textView);
		collection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//	addCollectionRelationShip();
			}
		});
		
//一键分享
		share=(TextView)view.findViewById(R.id.share_TextView);
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showShare();
			}
		});

//加入购物车
		addcart=(TextView)view.findViewById(R.id.add_cart_textview);
		addcart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addOrder();
			}
		});
		
		mLinearLayout=(LinearLayout)view.findViewById(R.id.scrolllinelayout);
		mScrollView=(ScrollView)view.findViewById(R.id.cook_scrollView);
		mScrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return changeTextViewHideOrAppear(event);
			}
		});
		initFoodInfo();
		addSteps();
		return  view;
	}
//一键分享
	   private void showShare() {
	        ShareSDK.initSDK(getActivity());
	        OnekeyShare oks = new OnekeyShare();
	        //关闭sso授权
	        oks.disableSSOWhenAuthorize();
	        // 分享时Notification的图标和文字
	        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
	        oks.setTitle(getString(R.string.share));// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
	        oks.setTitleUrl("http://sharesdk.cn"); // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
	       
	       // oks.setImageUrl("http://www.iyi8.com/uploadfile/2014/0506/20140506085929652.jpg");
	        oks.setUrl("http://sharesdk.cn");// url仅在微信（包括好友和朋友圈）中使用
	        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
	        oks.setComment("我是测试评论文本");
	        oks.setSite(getString(R.string.app_name));// site是分享此内容的网站名称，仅在QQ空间使用
	        oks.setSiteUrl("http://sharesdk.cn");// siteUrl是分享此内容的网站地址，仅在QQ空间使用
	        String a ="";
	        for (int i = 0; i <stepJsonObject.length(); i++) {
	    		JSONObject mJsonObject;
	    		try {mJsonObject = stepJsonObject.getJSONObject(i);
	    			a=a+String.valueOf(mJsonObject.getInt("recipeorder"))+":"+mJsonObject.getString("recipedetail")+";";
	    		} catch (JSONException e) {e.printStackTrace();}}
	        try {
	        	// text是分享文本，所有平台都需要这个字段
		        oks.setText("#煮乜嘢#"+dishInfoJsonObject.getString("dishname")+a);
		        BitmapDrawable drawable=(BitmapDrawable)foodImageView.getDrawable();
				Bitmap bitmap = drawable.getBitmap();
				try {
					saveMyBitmap("share",bitmap);
					 oks.setImagePath("/sdcard/yuf_image/" + "share" + ".PNG");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    } catch (JSONException e) {e.printStackTrace();}
	        // 启动分享GUI
	        oks.show(getActivity());
	   }
	   
	   public void saveMyBitmap(String bitName,Bitmap mBitmap) throws IOException {  
	        File f = new File("/sdcard/yuf_image/" + bitName + ".PNG");  
	        f.createNewFile();  
	        FileOutputStream fOut = null;  
	        try {  
	                fOut = new FileOutputStream(f);  
	        } catch (FileNotFoundException e) {  
	                e.printStackTrace();  
	        }  
	        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);  
	        try {  
	                fOut.flush();  
	        } catch (IOException e) {  
	                e.printStackTrace();  
	        }  
	        try {  
	                fOut.close();  
	        } catch (IOException e) {  
	                e.printStackTrace();  
	        }  
	    }  

	   protected void addOrder() {
	try {
		
	
        	Order order=new Order();
			order.dishId=dishInfoJsonObject.getInt("dishid");
			order.orderAmount=1;
			order.orderImage=dishInfoJsonObject.getString("dishpicurl");
			order.orderName=dishInfoJsonObject.getString("dishname");
			order.orderPaymethod="货到付款";
			order.orderPrice=dishInfoJsonObject.getDouble("dishprice");
			order.orderTime=timeString();
			order.userId=Integer.valueOf(UserInfo.getInstance().userid);
			order.writeToDb();
			Toast.makeText(getActivity(), "加入购物车成功", Toast.LENGTH_SHORT).show();
        
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


protected void appearTextView() {
		
	Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_left);
	animation.setFillAfter(true);
	animation.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			Log.d("liow", "commenttextview before left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			
			
			comment.setClickable(true);
			Log.d("liow", "commenttextview left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			
		}
	});
	
	Animation animation2= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_left);
animation2.setFillAfter(true);
	animation2.setAnimationListener(new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
showComment.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	Animation animation3= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_right);
	animation3.setFillAfter(true);
	animation3.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
collection.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation4= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_right);
	animation4.setFillAfter(true);
	animation4.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
share.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation5= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_in_from_right);
	animation5.setFillAfter(true);
	animation5.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
addcart.setClickable(true);			
			// TODO Auto-generated method stub
			
		}
	});
	
	
	comment.startAnimation(animation);
	showComment.startAnimation(animation2);
	collection.startAnimation(animation3);
	share.startAnimation(animation4);
	addcart.startAnimation(animation5);
	}


private void disappearTextView() {
	Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_left);
	animation.setFillAfter(true);
	animation.setAnimationListener(new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			Log.d("liow", "commenttextview before left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			comment.setClickable(false);
			Log.d("liow", "commenttextview left:"+String.valueOf(comment.getLeft())+  "right:"+String.valueOf(comment.getRight()));
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation2= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_left);
	animation2.setFillAfter(true);
	animation2.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
showComment.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	Animation animation3= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_right);
	animation3.setFillAfter(true);
	animation3.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
collection.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	
	Animation animation4= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_right);
	animation4.setFillAfter(true);
	animation4.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			share.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	Animation animation5= AnimationUtils.loadAnimation(getActivity(),R.anim.textview_out_to_right);
	animation5.setFillAfter(true);
	animation5.setAnimationListener(new AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressLint("NewApi") @Override
		public void onAnimationEnd(Animation animation) {
			addcart.setClickable(false);			
			// TODO Auto-generated method stub
			
		}
	});
	
	comment.startAnimation(animation);
	showComment.startAnimation(animation2);
	collection.startAnimation(animation3);
	share.startAnimation(animation4);
	addcart.startAnimation(animation5);

	
}

private void initFoodInfo()
{
	try {
		hardLevelTextview.setText(dishInfoJsonObject.getString("dishdifficulty"));
		doseTextView.setText(dishInfoJsonObject.getString("dishamount"));
		skillTextView.setText(dishInfoJsonObject.getString("dishcookmethod"));
		timeTextView.setText(dishInfoJsonObject.getString("dishcooktime"));
		foodImageView.setImageUrl("http://pic37.nipic.com/20140106/4418117_204831367168_2.jpg", mImageLoader);	
	} catch (JSONException e) {	
		e.printStackTrace();
	}
}

private void addSteps(){
	for (int i = 0; i <stepJsonObject.length(); i++) {
		
		LayoutInflater inflater=LayoutInflater.from(getActivity());
		LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.tab0_fook_cook_step, null);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		TextView stepOrderTextView=(TextView)linearLayout.findViewById(R.id.tab0_food_cook_step_steporder_textview);
		NetworkImageView imageView=(NetworkImageView)linearLayout.findViewById(R.id.tab0_food_cook_step_image_imageview);
		TextView introduceTextView=(TextView)linearLayout.findViewById(R.id.tab0_food_cook_step_introuduce_textview);
		JSONObject mJsonObject;
		try {
			mJsonObject = stepJsonObject.getJSONObject(i);
			stepOrderTextView.setText("第"+i+"步");
//			imageView.setImageUrl("http://110.84.129.130:8080/Yuf"+mJsonObject.getString("recipepicurl"), mImageLoader);
			imageView.setImageUrl("http://pic37.nipic.com/20140106/4418117_204831367168_2.jpg", mImageLoader);
			introduceTextView.setText(mJsonObject.getString("recipedetail"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mLinearLayout.addView(linearLayout);
	}

}


   
private boolean changeTextViewHideOrAppear(MotionEvent event) {

	// TODO Auto-generated method stub
	if (event.getAction()==MotionEvent.ACTION_DOWN) {
		start_y=event.getY();
	
	}
	
if (event.getAction()==MotionEvent.ACTION_MOVE&&start_y!=0) {
	
	if(event.getY()-start_y<0&&!isDisappear)
		{
		
		disappearTextView();
		Log.d("liow", "disappeartextview");
		isDisappear=true;
		return false;
		}
	
	if(event.getY()-start_y>0&&isDisappear)
		{
			appearTextView();
			Log.d("liow", "appeartextview");
			isDisappear=false;
		}
}
return false;
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


private void commentDish(String commentContent) {
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("userId",Integer.valueOf(UserInfo.getInstance().getUserid()));
			jsonObject.put("dishId", dishInfoJsonObject.getInt("dishid"));
			jsonObject.put("dishcommentContent", commentContent);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JsonObjectRequest request=new JsonObjectRequest(Method.POST,"http://110.84.129.130:8080/Yuf/dishcomment/postDishcomment/", jsonObject, new Response.Listener<JSONObject>()  
		        {  

            @Override  
            public void onResponse(JSONObject response)  
            {
            	try {
					if (response.getInt("code")==0) {
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
private String timeString() {
	// TODO Auto-generated method stub
	long currentTime = System.currentTimeMillis();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date(currentTime);
	return formatter.format(date);
}


protected void addCollectionRelationShip() {
	// TODO Auto-generated method stub
	JSONObject jsonObject=new JSONObject();
	try {
		jsonObject.put("userId",Long.valueOf( UserInfo.getInstance().userid));
		jsonObject.put("sessionId", UserInfo.getInstance().sessionid);
		jsonObject.put("dishId", dishInfoJsonObject.getInt("dishid"));
		
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/collection/addCollection", jsonObject, new Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			// TODO Auto-generated method stub
			try {
				if (response.getInt("code")==0) {
					
					Toast toast = Toast.makeText(getActivity(),
						     "收藏成功", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
				}
				else {
					Toast toast = Toast.makeText(getActivity(),
						     "已收藏", Toast.LENGTH_SHORT);
						   toast.setGravity(Gravity.CENTER, 0, 0);
						   toast.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	},new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			
		}
	});
	MyApplication.requestQueue.add(request);
	MyApplication.requestQueue.start();
}

}
