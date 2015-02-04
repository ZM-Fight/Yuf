package com.yuf.app.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.mywidget.MyDialog;
import com.yuf.app.ui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodMaterialGVAdapter extends BaseAdapter {

	 //上下文对象 
    private Context context; 
    //测试图片数组 
    private JSONArray jsonArray;
    private ImageLoader mImageLoader;
    
    public FoodMaterialGVAdapter(Context context,JSONArray _jsonArray){ 
        this.context = context; 
        jsonArray=_jsonArray;
        mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());
    } 
    public int getCount() { 
        return jsonArray.length(); 
    } 

    public Object getItem(int item) { 
        return item; 
    } 

    public long getItemId(int id) { 
        return id; 
    } 
     
    //创建View方法 
    @SuppressLint("ViewHolder") public View getView(int position, View convertView, ViewGroup parent) { 
       
    	View view;
    	if (convertView==null) {
			//
    		LayoutInflater myinflater=LayoutInflater.from(context);
    		 view=myinflater.inflate(R.layout.tab2_food_material_gridview_item, null);
		}
    	else {
			view=convertView;
		}
    	//加载数据
            
    	NetworkImageView imageView=(NetworkImageView) view.findViewById(R.id.tab0_materil_grideview_item_img);
    	imageView.setDefaultImageResId(R.drawable.no_pic);
    	TextView doseTextView=(TextView) view.findViewById(R.id.tab0_material_gridview_item_dose_textview);
    	TextView namTextView=(TextView) view.findViewById(R.id.tab0_material_gridview_item_name_textview);
    	try {
    		JSONObject jsonObject=jsonArray.getJSONObject(position);
			imageView.setImageUrl("http://110.84.129.130:8080/Yuf"+jsonObject.getString("foodpicurl"), mImageLoader);
			namTextView.setText(jsonObject.getString("foodname"));
			doseTextView.setText(String.valueOf(jsonObject.getDouble("foodamount")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
            view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MyDialog myDialog=new MyDialog(context);
					myDialog.showDialog(R.layout.material_detail_dialg,0, 0);
					
					
					// TODO Auto-generated method stub
					
				}
			});
            return view; 
    } 

}
