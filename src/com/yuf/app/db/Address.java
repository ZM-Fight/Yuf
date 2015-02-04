package com.yuf.app.db;

import java.util.ArrayList;

import com.yuf.app.MyApplication;

import android.R.integer;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class Address {
	public static final String ID = "_id";
	public static final String NAMESTRING = "nameString";
	public static final String PHONESTRING = "phoneString";
	public static final String ZONESTRING = "zoneString";
	public static final String DETAILSTRING = "detailString";
	public static final String ISDEFAULT = "isDefault";
	public  static  int positionOfStartAddress =-1;//确定每一次数据库读取的起始位置
	public static int defaultId;
	public static Address defaultAddress;
	
	public String nameString;
	public String phoneString;
	public String zoneString;
	public String detailString;
	public int isDefault; //1表示默认地址；0非默认地址
	public int _id;
	
	public void writeToDb() {
		ContentResolver contentResolver = MyApplication.myapplication.getContentResolver();  
        Uri url = Uri.parse("content://com.yuf.app.myprovider/address");  
        ContentValues values = new ContentValues();  
        values.put(Address.NAMESTRING, nameString);  
        values.put(Address.PHONESTRING, phoneString);  
        values.put(Address.ZONESTRING, zoneString);  
        values.put(Address.DETAILSTRING, detailString);  
        values.put(Address.ISDEFAULT, isDefault); 
        Uri result = contentResolver.insert(url, values); 
        if (ContentUris.parseId(result)>0) {  
            Log.d("address","添加成功！");        
        }  
	}
	public static ArrayList<Address> readFromDb() {
		ArrayList<Address> list = new ArrayList<Address>();
		Uri url = Uri.parse("content://com.yuf.app.myprovider/addresses");  
        Cursor cursor = MyApplication.myapplication.getContentResolver().query(url,  
                  new String[] { "_id", "nameString", "phoneString","zoneString","detailString","isDefault"}, null, null, "_id");  
        
      if(cursor!=null){
    	cursor.moveToPosition(positionOfStartAddress);
    	int number =0;
	    while (cursor.moveToNext()&&number<5) {  
		number++;
       	Address address = new Address();
       	address._id=cursor.getInt(cursor.getColumnIndex("_id"));
       	address.nameString = cursor.getString(cursor.getColumnIndex("nameString"));  
       	address.phoneString = cursor.getString(cursor.getColumnIndex("phoneString"));  
       	address.zoneString = cursor.getString(cursor.getColumnIndex("zoneString"));  
       	address.detailString = cursor.getString(cursor.getColumnIndex("detailString"));  
       	address.isDefault = cursor.getInt(cursor.getColumnIndex("isDefault"));  
       	if (address.isDefault==1) {
			defaultId=address._id;
			defaultAddress=address;
		}
       	
       	list.add(address); 
       	positionOfStartAddress++;
       }  
       }
        cursor.close();  
		return list;
	}
	//未使用
	public static void setDefaultAddress(int i) {
		
		ContentResolver contentResolver1= MyApplication.myapplication.getContentResolver();  
		Uri url1 = Uri.parse("content://com.yuf.app.myprovider/address/"+defaultId);  
		ContentValues values1 = new ContentValues();
		values1.put(Address.ISDEFAULT, 0);
		int result1 = contentResolver1.update(url1, values1,null, null);
	    if (result1>0) {  
	        Log.d("liow","修改成功成功！");        
	    }  
		
		
		ContentResolver contentResolver = MyApplication.myapplication.getContentResolver();  
		Uri url = Uri.parse("content://com.yuf.app.myprovider/address/"+String.valueOf(i));  
		ContentValues values = new ContentValues();
		values.put(Address.ISDEFAULT, 1);
		int result = contentResolver.update(url, values,null, null);
	    if (result>0) {  
	        Log.d("liow","修改成功成功！");        
	    }  
	    defaultId=i;
	    
	    
	}

	
	//地址栏的信息数目
	public static int numberOfAddress(){
		Uri url = Uri.parse("content://com.yuf.app.myprovider/addresses");  
        Cursor cursor = MyApplication.myapplication.getContentResolver().query(url,  
                  new String[] {"_id", "nameString", "phoneString","zoneString","detailString","isDefault"}, null, null, "_id");  
        return cursor.getCount();	
	}
	public static void deleteFromDb(int i)
	{
		ContentResolver contentResolver = MyApplication.myapplication.getContentResolver();  
	    Uri url = Uri.parse("content://com.yuf.app.myprovider/address/"+String.valueOf(i));  
	    
	  
	    int result = contentResolver.delete(url, null, null);
	    if (result>0) {  
	        Log.d("liow","删除成功！");        
	    }  
		}
		
}
