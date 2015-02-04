package com.yuf.app.test;

import java.util.ArrayList;

import android.util.Log;

import com.yuf.app.db.Address;
import com.yuf.app.db.MyProvider;
import com.yuf.app.db.Order;


public class TestProvider extends android.test.ProviderTestCase2<MyProvider> {

	
	 public TestProvider()  
	    {  
	        super(MyProvider.class, "com.yuf.app.db.MyProvider");  
	    }  
	public void testSave()
	{

		Order order=new Order();
		order.dishId=6;
		order.orderAmount=6;
		order.orderPaymethod="6";
		order.orderPrice=6;
		order.orderTime="6";
		order.userId=6;
		order.orderImage = "6";
		order.orderName="6";
		order.writeToDb();
		Order order1=new Order();
		order1.dishId=7;
		order1.orderAmount=7;
		order1.orderPaymethod="7";
		order1.orderPrice=7;
		order1.orderTime="7";
		order1.userId=7;
		order1.orderImage = "7";
		order1.orderName="7";
		order1.writeToDb();
		Order order2=new Order();
		order2.dishId=8;
		order2.orderAmount=8;
		order2.orderPaymethod="8";
		order2.orderPrice=8;
		order2.orderTime="8";
		order2.userId=8;
		order2.orderImage = "8";
		order2.orderName="8";
		order2.writeToDb();
		Order order3=new Order();
		order3.dishId=9;
		order3.orderAmount=9;
		order3.orderPaymethod="9";
		order3.orderPrice=9;
		order3.orderTime="9";
		order3.userId=9;
		order3.orderImage = "9";
		order3.orderName="9";
		order3.writeToDb();
		Order order4=new Order();
		order4.dishId=10;
		order4.orderAmount=10;
		order4.orderPaymethod="10";
		order4.orderPrice=10;
		order4.orderTime="10";
		order4.userId=10;
		order4.orderImage = "10";
		order4.orderName="10";
		order4.writeToDb();
	}
	public void testSave1()
	{

		Address address=new Address();
		address.nameString="abc";
		address.phoneString="abc";
		address.zoneString="abc";
		address.detailString="abc";
		address.isDefault=0;
		address.writeToDb();
	}
	public void testOutput(){
		ArrayList<Order> list = Order.readFromDb();
		for (int i = 0; i < list.size(); i++) {
			Order order = (Order)list.get(i);
			Log.d("TEST", order.userId+"-"+order.dishId+"-"+order.orderPrice+"-"+order.orderTime+"-"
					+order.orderPaymethod+"-"+order.orderAmount+""+order.orderImage+order.orderName);
		}
	}

}
