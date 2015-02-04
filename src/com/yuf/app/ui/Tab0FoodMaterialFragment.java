package com.yuf.app.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yuf.app.MyApplication;
import com.yuf.app.mywidget.MyGridView;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yuf.app.adapter.FoodMaterialGVAdapter;




@SuppressLint("ValidFragment")
public class Tab0FoodMaterialFragment extends Fragment{

	private JSONArray mainMaterial;
	private JSONArray helpMaterial;
	private JSONArray addMaterial;
	private MyGridView gridView1;
	private MyGridView gridView2;
	private MyGridView gridView3;
	
	public Tab0FoodMaterialFragment(int number) {
		super();
		mainMaterial=new JSONArray();
		helpMaterial=new JSONArray();
		addMaterial=new JSONArray();
		
		JSONArray jsonArray = new JSONArray();
		sortMaterial(jsonArray);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		 super.onCreateView(inflater, container, savedInstanceState);
		View view=inflater.inflate(R.layout.tab0_food_material,container,false);
		gridView1=(MyGridView)view.findViewById(R.id.food_material_gridview1);
		gridView2=(MyGridView)view.findViewById(R.id.food_material_gridview2);
		gridView3=(MyGridView)view.findViewById(R.id.food_material_gridview3);
		gridView1.setAdapter(new FoodMaterialGVAdapter(getActivity(),mainMaterial));
		gridView2.setAdapter(new FoodMaterialGVAdapter(getActivity(),helpMaterial));
		gridView3.setAdapter(new FoodMaterialGVAdapter(getActivity(),addMaterial));
		
		
		
		return  view;
	}
	private void sortMaterial(JSONArray jsonArray)
	{
		String typeString=new String();
		JSONObject jsonObject=new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				jsonObject = jsonArray.getJSONObject(i);
				 typeString=jsonObject.getString("foodtype");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (typeString.equals("主料")) {
				mainMaterial.put(jsonObject);
			}
			else if (typeString.equals("辅料")) {
				helpMaterial.put(jsonObject);
			}
			else  {
				addMaterial.put(jsonObject);
			}
		}
	}
}
