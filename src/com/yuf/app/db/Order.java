package com.yuf.app.db;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.yuf.app.MyApplication;

public class Order {
	public static final String ID = "_id";
	public static final String USERID = "userId";
	public static final String ORDERPRICE = "orderPrice";
	public static final String ORDERTIME = "orderTime";
	public static final String ORDERPAYMETHOD = "orderPaymethod";
	public static final String ORDERAMOUNT = "orderAmount";
	public static final String ORDERDISHID = "dishId";
	public static final String ORDERIMAGE = "orderImage";
	public static final String ORDERNAME = "orderName";
	public static final String ISSELECT = "isSelect";
	public  static  int positionOfStart =-1;//确定每一次数据库读取的起始位置
	public int _id;
	public int userId;
	public int dishId;
	public double orderPrice;
	public String orderTime;
	public String orderPaymethod;
	public int orderAmount;
	public String orderImage;
	public String orderName;
	public int isSelect;
	public JSONObject toJsonObject() {
		JSONObject object = new JSONObject();
		try {
			object.put("userId", userId);
			object.put("dishId", dishId);
			object.put("orderPrice", orderPrice);
			object.put("orderTime", orderTime);
			object.put("orderPaymethod", orderPaymethod);
			object.put("orderAmount",orderAmount);
			object.put("isSelect",isSelect);
			object.put("orderImage",orderImage);
			object.put("orderName",orderName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}
	public void writeToDb() {
		Uri url1 = Uri.parse("content://com.yuf.app.myprovider/orderToBeAdded");  
		Cursor cursor = MyApplication.myapplication.getContentResolver().query(url1,  
                 new String[] { "_id", "userId", "orderPrice","orderTime","orderPaymethod","orderAmount","isSelect","dishId","orderImage","orderName" }, " userId=? and dishId=?", new String[]{userId+"",dishId+""}, "_id");  
		//相同的用户ID，相同的菜
		int count = cursor.getCount();
		if(count > 0 && cursor != null){
		addOrder();
		}
		else {
			
		
		   ContentResolver contentResolver = MyApplication.myapplication.getContentResolver();  
           Uri url = Uri.parse("content://com.yuf.app.myprovider/order");  
           ContentValues values = new ContentValues();  
           values.put(Order.USERID, userId);  
           values.put(Order.ORDERPRICE, orderPrice);  
           values.put(Order.ORDERTIME, orderTime);  
           values.put(Order.ORDERPAYMETHOD, orderPaymethod);  
           values.put(Order.ORDERAMOUNT, orderAmount);  
           values.put(Order.ORDERDISHID, dishId);  
           values.put(Order.ORDERIMAGE, orderImage); 
           values.put(Order.ORDERNAME, orderName);  
           values.put(Order.ISSELECT, isSelect);  
           Uri result = contentResolver.insert(url, values); 
           if (ContentUris.parseId(result)>0) {  
               Log.d("Order-writeToDb","添加成功！");        
           }  
		}
	}
	public  void addOrder(){
		Uri url = Uri.parse("content://com.yuf.app.myprovider/orderToBeAdded");  
		Cursor cursor = MyApplication.myapplication.getContentResolver().query(url,  
                 new String[] { "_id", "userId", "orderPrice","orderTime","orderPaymethod","orderAmount","isSelect","dishId","orderImage","orderName" }, " userId=? and dishId=?", new String[]{userId+"",dishId+""}, "_id");  
		ContentResolver contentResolver= MyApplication.myapplication.getContentResolver();  
		ContentValues values = new ContentValues();
		cursor.moveToNext();
		int number = cursor.getInt(cursor.getColumnIndex("orderAmount"));
		values.put(Order.ORDERAMOUNT,++number);
		Uri url2 = Uri.parse("content://com.yuf.app.myprovider/order_adds");  
		int result = contentResolver.update(url2, values," userId=? and dishId=?", new String[]{userId+"",dishId+""});
	    if (result>0) {Log.d("Success","修改Order份数！");} 
	}
	//modify-isSelect
	public void modifyIsSelected(int a) {
		ContentResolver contentResolver= MyApplication.myapplication.getContentResolver();  
		Uri url1 = Uri.parse("content://com.yuf.app.myprovider/order/"+_id);  
		ContentValues values = new ContentValues();
		values.put(Order.ISSELECT,a);
		int result = contentResolver.update(url1, values,null, null);
	    if (result>0) {  
	        Log.d("Order-modify_isSelect","修改成功！");        
	    }  
	}
	//返回时将所有修改为未选中状态
	public static void modifyAllIsSelected(){
		ContentResolver contentResolver= MyApplication.myapplication.getContentResolver();  
		Uri url1 = Uri.parse("content://com.yuf.app.myprovider/orders");  
		ContentValues values = new ContentValues();
		values.put(Order.ISSELECT,0);
		int result = contentResolver.update(url1, values,null, null);
	    if (result>0) {  
	        Log.d("Order-modifyAllIsSelected","修改成功！");        
	    }  
   }
	//a是0则减一；a是1则加一。
	public void modifyAmount(int a){
		ContentResolver contentResolver= MyApplication.myapplication.getContentResolver();  
		Uri url = Uri.parse("content://com.yuf.app.myprovider/order/"+_id);  
		ContentValues values = new ContentValues();
		Cursor cursor = MyApplication.myapplication.getContentResolver().query(url,  
                new String[] { "_id", "userId", "orderPrice","orderTime","orderPaymethod","orderAmount","isSelect","dishId","orderImage","orderName" }, null, null, "_id");  
		cursor.moveToNext();
		int number = cursor.getInt(cursor.getColumnIndex("orderAmount"));
		if(a==0){values.put(Order.ORDERAMOUNT,--number);}
		else{values.put(Order.ORDERAMOUNT,++number);}
		int result = contentResolver.update(url, values,null, null);
	    if (result>0) {  
	        Log.d("Order-modifyAmount","修改成功！");        
	    } 
	}
	
public static void deleteFromDb(int i)
{
	ContentResolver contentResolver = MyApplication.myapplication.getContentResolver();  
    Uri url = Uri.parse("content://com.yuf.app.myprovider/order/"+String.valueOf(i));  
    
  
    int result = contentResolver.delete(url, null, null);
    if (result>0) {  
        Log.d("Order-deleteFromDb","删除成功！");        
    }  
}

	public static ArrayList<Order> readFromDb() {
		ArrayList<Order> list = new ArrayList<Order>();
		Uri url = Uri.parse("content://com.yuf.app.myprovider/orders");  
        Cursor cursor = MyApplication.myapplication.getContentResolver().query(url,  
                  new String[] { "_id", "userId", "orderPrice","orderTime","orderPaymethod","orderAmount","isSelect","dishId","orderImage","orderName" }, null, null, "_id");  
        
      if(cursor!=null){
    	cursor.moveToPosition(positionOfStart);
    	int number =0;
	    while (cursor.moveToNext()&&number<5) {  
		number++;
       	Order order = new Order();
       	order._id= cursor.getInt(cursor.getColumnIndex("_id"));
       	order.userId = cursor.getInt(cursor.getColumnIndex("userId"));  
       	order.dishId = cursor.getInt(cursor.getColumnIndex("dishId"));  
       	order.orderPrice = cursor.getDouble(cursor.getColumnIndex("orderPrice"));  
       	order.orderTime = cursor.getString(cursor.getColumnIndex("orderTime"));  
       	order.orderPaymethod = cursor.getString(cursor.getColumnIndex("orderPaymethod"));  
       	order.orderAmount = cursor.getInt(cursor.getColumnIndex("orderAmount")); 
       	order.isSelect = cursor.getInt(cursor.getColumnIndex("isSelect"));  
       	order.orderImage = cursor.getString(cursor.getColumnIndex("orderImage"));  
       	order.orderName = cursor.getString(cursor.getColumnIndex("orderName"));  
       	list.add(order); 
       	positionOfStart++;
       }  
       }
        cursor.close();  
		return list;
	}
	//选择所有被选中的Order
	public static ArrayList<Order> readAllSelectOrderFromDb() {
		ArrayList<Order> list = new ArrayList<Order>();
		Uri url = Uri.parse("content://com.yuf.app.myprovider/isselected_order");  
        Cursor cursor = MyApplication.myapplication.getContentResolver().query(url,  
                  new String[] { "_id", "userId", "orderPrice","orderTime","orderPaymethod","orderAmount","isSelect","dishId","orderImage","orderName" }, null, null, "_id");  
        
      if(cursor!=null){
    	while (cursor.moveToNext()) {  
		Order order = new Order();
		order._id= cursor.getInt(cursor.getColumnIndex("_id"));
       	order.userId = cursor.getInt(cursor.getColumnIndex("userId"));  
       	order.dishId = cursor.getInt(cursor.getColumnIndex("dishId"));  
       	order.orderPrice = cursor.getDouble(cursor.getColumnIndex("orderPrice"));  
       	order.orderTime = cursor.getString(cursor.getColumnIndex("orderTime"));  
       	order.orderPaymethod = cursor.getString(cursor.getColumnIndex("orderPaymethod"));  
       	order.orderAmount = cursor.getInt(cursor.getColumnIndex("orderAmount"));  
       	order.isSelect = cursor.getInt(cursor.getColumnIndex("isSelect"));  
       	order.orderImage = cursor.getString(cursor.getColumnIndex("orderImage"));  
       	order.orderName = cursor.getString(cursor.getColumnIndex("orderName"));  
       list.add(order); 
		}  
       }
        cursor.close();  
		return list;
	}
	public static int numberOfOrders(){
		Uri url = Uri.parse("content://com.yuf.app.myprovider/orders");  
        Cursor cursor = MyApplication.myapplication.getContentResolver().query(url,  
                  new String[] { "_id", "userId", "orderPrice","orderTime","orderPaymethod","orderAmount","isSelect","dishId","orderImage","orderName" }, null, null, "_id");  
        return cursor.getCount();	
	}
}

