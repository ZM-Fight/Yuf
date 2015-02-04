package com.yuf.app.Entity;

import android.R.string;

/**
 * 杩斿洖鐨�澶х殑json鐨�涓殑categorys鐨勫皝瑁�
 * @author wangxin
 *
 */
public class CategorysEntity {
	
	private String name;
	
	public CategorysEntity(String nameString){
		name=nameString;
	}
		public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


}
