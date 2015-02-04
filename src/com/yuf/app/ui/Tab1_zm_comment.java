package com.yuf.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuf.app.db.DBComment;


public class Tab1_zm_comment extends Fragment{

	private  ListView listView;
	private List<String> dishNameList;
	private List<String> dishPictureList;
	private ArrayList<JSONObject> dishComment;
	private ArrayList<JSONObject> dishComment1;
	private ArrayList<JSONObject> dishComment2;
	private ArrayList<JSONObject> dishComment3;
	private ArrayList<JSONObject> dishComment4;
	private ArrayList<JSONObject> dishComment5;
	private ArrayList<JSONObject> dishComment6;
	private ArrayList<JSONObject> dishComment7;
	private  AlertDialog dlg;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view  = inflater.inflate(R.layout.tab1_zm_fragment, container, false);
		
		listView = (ListView)view.findViewById(R.id.listView);
		
		List<Map<String, Object>> list=getData();  
		
		listView.setAdapter(new mAdapter(getActivity(),list,dishNameList,dishPictureList,dishComment));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
			
				
				LayoutInflater factory = LayoutInflater.from(getActivity());
				
				final View textEntryView = factory.inflate(R.layout.dialog, null);
				final EditText editText=(EditText)textEntryView.findViewById(R.id.comment_comment_editText);
				Button cancleButton=(Button)textEntryView.findViewById(R.id.comment_dialog_cancle_buttoon);
		       cancleButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					dlg.dismiss();
				}
			});
		       
		       switch(position){
		       case 0:
		    	   dishComment = dishComment1;
		    	   break;
		       case 1:
		    	   dishComment = dishComment2;
		    	   break;
		       case 2:
		    	   dishComment = dishComment3;
		    	   break;   
		       case 3:
		    	   dishComment = dishComment4;
		    	   break;
		       case 4:
		    	   dishComment = dishComment5;
		    	   break;
		       case 5:
		    	   dishComment = dishComment6;
		    	   break;
		       case 6:
		    	   dishComment = dishComment7;
		    	   break;
		    	   default:
		    		   break;
		       }
		       String aString  = null;
		       try {
				aString = "评论员 ："+dishComment.get(0).getString("user_name").toString() +
						"                 "+"酷评 ： "+dishComment.get(position).getString("dish_comment")+"                                                                     ";
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				
				e1.printStackTrace();
			}
		     for(int i=1;i<dishComment.size();i++){
		    	 try {
		    		 
		    		 aString =aString+ "评论员 ："+dishComment.get(i).getString("user_name").toString()+"  " +
								"                 "+"酷评 ： "+dishComment.get(position).getString("dish_comment")+"                                                                     ";
					} catch (Exception e) {
					}
		     }
				
		     editText.setText(aString);
				
				
		       
				dlg = new AlertDialog.Builder(getActivity()) .setView(textEntryView) .create();
		        dlg.show();
			}
		});
		return view;
	}
	
	public List<Map<String, Object>> getData(){  
		dishNameList = new ArrayList<String>();
		dishPictureList = new ArrayList<String>();
		dishComment = new ArrayList<JSONObject>();
		
		dishNameList.add("牛排");
		dishNameList.add("白灼虾");
		dishNameList.add("凤爪");
		dishNameList.add("剁椒鱼头");
		dishNameList.add("麻辣豆腐");
		dishNameList.add("糖醋排骨");
		dishNameList.add("南乳粉蒸肉");

		DBComment dbComment = new DBComment(getActivity());
		try {
			dishComment = dbComment.getComments(1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			dishComment1 = dbComment.getComments(1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			dishComment2 = dbComment.getComments(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			dishComment3 = dbComment.getComments(3);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			dishComment4 = dbComment.getComments(4);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			dishComment5 = dbComment.getComments(5);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			dishComment6 = dbComment.getComments(6);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			dishComment7 = dbComment.getComments(7);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();  
        for (int i = 0; i < 21; i++) {  
            Map<String, Object> map=new HashMap<String, Object>();  
            map.put("image", R.drawable.ic_launcher);  
            map.put("title", "这是一个标题"+i);  
            list.add(map);  
        }  
        return list;  
    }  

	
	private class mAdapter extends BaseAdapter{

		 private List<Map<String, Object>> data;  
		    private LayoutInflater listContainer; 
			private Context context;
			
			private List<String> dishNameList;
			private List<String> dishPictureList;
			private ArrayList<JSONObject >dishComment;
			public mAdapter(Context context,List<Map<String, Object>> data,List<String> dishNameList,List<String> dishPictureList,ArrayList<JSONObject>dishComment){
				this.context = context;
				 this.data=data;  
				
				 this.dishNameList=dishNameList;  
				 this.dishPictureList=dishPictureList;  
				 this.dishComment = dishComment;
				 
				listContainer = LayoutInflater.from(context);
			}
			
			@Override
			public int getCount() {
				return data.size();
			}

			@Override
			public Object getItem(int arg0) {
				return data.get(arg0);
			}

			@Override
			public long getItemId(int arg0) {
				return arg0;
			}

			public final class Zujian{  
		        public ImageView image;  
		        public TextView textView;  
		    }  
			
			@Override
			public View getView(int arg0, View convertView, ViewGroup arg2) {
				Zujian zujian=null;  
				
				if(convertView == null)
				{
					 zujian=new Zujian(); 
					convertView  = 	listContainer.inflate(R.layout.tab1_zm_comment_item,null);
					zujian.image=(ImageView)convertView.findViewById(R.id.tab1_zm_comment_item_imageView1);  
		            zujian.textView=(TextView)convertView.findViewById(R.id.tab1_zm_comment_item_editText1);  
		            convertView.setTag(zujian);  
					
				}else{  
		            zujian=(Zujian)convertView.getTag();  
		        }
				
				switch(arg0){
				case 0:
					zujian.image.setBackgroundResource(R.drawable.p1);
					zujian.textView.setText(dishNameList.get(arg0).toString());
					break;
				case 1:
					zujian.image.setBackgroundResource(R.drawable.p2);
					zujian.textView.setText(dishNameList.get(arg0).toString());
					break;	
				case 2:
					zujian.image.setBackgroundResource(R.drawable.p3);
					zujian.textView.setText(dishNameList.get(arg0).toString());
					break;
				case 3:
					zujian.image.setBackgroundResource(R.drawable.p4);
					zujian.textView.setText(dishNameList.get(arg0).toString());
					break;
				case 4:
					zujian.image.setBackgroundResource(R.drawable.p5);
					zujian.textView.setText(dishNameList.get(arg0).toString());
					break;
				case 5:
					zujian.image.setBackgroundResource(R.drawable.p6);
					zujian.textView.setText(dishNameList.get(arg0).toString());
					break;
				case 6:
					zujian.image.setBackgroundResource(R.drawable.p7);
					zujian.textView.setText(dishNameList.get(arg0).toString());
					break;
				default:
						break;
				}
				
				return convertView;
			}
			
		}


}
