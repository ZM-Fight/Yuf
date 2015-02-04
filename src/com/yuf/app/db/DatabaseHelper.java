package com.yuf.app.db;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "Dish";
	private static final String TABLE_NAME = "tb_dish";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql_tb_dish = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,dish_name TEXT  NOT NULL,dish_id INTEGER NOT NULL,dish_image TEXT  NOT NULL,dish_difficulty float  NOT NULL,dish_amount INTEGER  NOT NULL,dish_method TEXT  NOT NULL,dish_time INTEGER  NOT NULL,dish_content TEXT NOT NULL)";

		db.execSQL(sql_tb_dish);
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		if (cursor.getCount() == 0) {
			this.insert(
					db,
					"牛排",
					1,
					"http://pic35.nipic.com/20131113/7852156_124915686179_2.jpg",
					3.0, 3, "煎", 1, "好好吃");
			this.insert(
					db,
					"白灼虾",
					2,
					"http://i3.meishichina.com/attachment/r/2012/06/07/p800_20120607131332315744565.jpg",
					3.0, 5, "煮", 2, "味道鲜");
			this.insert(
					db,
					"凤爪",
					3,
					"http://pic14.nipic.com/20110502/558804_232038509000_2.jpg",
					4.0, 4, "烧", 3, "难做");
			this.insert(
					db,
					"剁椒鱼头",
					4,
					"http://i3.meishichina.com/attachment/r/2012/06/07/p800_20120607131332315744565.jpg",
					5.0, 4, "煮", 2, "适中");
			this.insert(
					db,
					"麻辣豆腐",
					5,
					"http://pic37.nipic.com/20140106/4418117_204831367168_2.jpg",
					1.0, 4, "红烧", 1, "容易做");
			this.insert(
					db,
					"糖醋排骨",
					6,
					"http://i3.meishichina.com/attachment/recipe/2014/06/11/20140611140717217157565.jpg",
					2.0, 3, "烧", 1, "甜");
			this.insert(
					db,
					"南乳粉蒸肉",
					7,
					"http://cp1.douguo.net/upload/caiku/9/d/2/yuan_9d44aefaf7d4518d0b0126b04fc2f0c2.jpg",
					3.0, 2, "煮", 3, "辣");
			this.insert(db, "天津麻花", 8,
					"http://image.dianping.com/2008-02-10/340038_b.jpg", 2.0,
					2, "炸", 2, "油腻");
			this.insert(
					db,
					"贵州恋爱豆腐果",
					9,
					"http://img3.imgtn.bdimg.com/it/u=3710539682,660563321&fm=21&gp=0.jpg",
					2.0, 2, "煎", 2, "好吃");
			this.insert(
					db,
					"肉末海参",
					10,
					"http://i3.meishichina.com/attachment/r/2012/06/07/p800_20120607131332315744565.jpg",
					2.0, 2, "烧", 1, "健康养生");
			this.insert(
					db,
					"鱼香肉丝",
					11,
					"http://recipe0.hoto.cn/pic/recipe/l/34/23/271156_bfd10e.jpg",
					2.0, 2, "炒", 1, "鱼香，是四川菜肴主要传统味型之一。成菜具有鱼香味，而其味是调味品调制而成。");
			this.insert(
					db,
					"酱猪蹄",
					12,
					"http://i3.meishichina.com/attachment/r/2012/05/21/p800_201205211247221845812.jpg",
					2.0, 4, "烧", 3, "不油腻，味道浓但不重，肉质软烂，冷着热着都好吃");
			this.insert(
					db,
					"宫保鸡丁",
					13,
					"http://recipe0.hoto.cn/pic/recipe/l/74/44/279668_10723b.jpg",
					1.0, 3, "炒", 1,
					"这是一道川菜中的保留家常菜，由鸡丁、干辣椒、花生米等炒制而成。由于其入口鲜辣，鸡肉的鲜嫩配合花生的香脆，深受大众喜欢。");
			this.insert(
					db,
					"夫妻肺片",
					14,
					"http://cp1.douguo.net/upload/caiku/3/6/b/yuan_36e42ccf91972a8ef9904d2fbfd0173b.jpg",
					1.0, 3, "烧", 2,
					"用成本低廉的牛杂碎边角料，经精加工、卤煮后，切成片，佐以酱油、红油、辣椒、花椒面、芝麻面等拌食，风味别致，价廉物美");
			this.insert(
					db,
					"东坡肘子",
					15,
					"http://i3.meishichina.com/attachment/r/2011/08/08/p800_201108081019300.jpg",
					2.0, 4, "烧", 2,
					"东坡肘子是苏东坡夫人王弗制作的传统名菜，因苏东坡极其喜爱而得名。它有肥而不腻、粑而不烂的特点");
			this.insert(
					db,
					"台式卤饭",
					16,
					"http://i3.meishichina.com/attachment/recipe/201103/p800_201103031616552.jpg",
					2.0,
					1,
					"炒",
					2,
					"卤肉饭也是早期艰苦的台湾人所发明的平民美食，把头皮肉和不能成块的碎肉搅拌成肉馅作为原料，再以酱油慢熬，除了味道很下饭之外，用酱油卤过的肉也能比一般的荤菜保存的时间要长");
			this.insert(
					db,
					"蜜汁烤羊排",
					17,
					"http://images.meishij.net/p/20120802/2aa6eb7caa98021b30d5e9a964e4266e.jpg",
					2.0,
					2,
					"烤",
					3,
					"羊肉性温，冬季常吃羊肉，不仅可以增加人体热量，抵御寒冷，而且还能增加消化酶，保护胃壁，修复胃粘膜，帮助脾胃消化，起到抗衰老的作用。因此这道秘制烤羊排具有很好的保健功效。");
			this.insert(
					db,
					"拔丝鸡盒",
					18,
					"http://images.meishij.net/p/20120724/c3f68ec1786537b9fdacafb4c923bdaa.jpg",
					2.0, 2, "烧", 2, "拔丝鸡盒所属菜系是京菜，口味略甜，色泽美观，香甜适口。");
			this.insert(
					db,
					"沛公狗肉",
					19,
					"http://h.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=3212ac81a586c9171c0e5a6ba8541baa/7af40ad162d9f2d3f0f46c96abec8a136227cce6.jpg",
					2.0, 2, "烧", 2,
					"沛公狗肉别名鼋汁狗肉，沛县狗肉，樊哙狗肉等。是江苏地区汉族传统名菜，属于苏菜系，以狗肉为制作主料，沛公狗肉的烹饪技巧以砂锅为主，口味属于本地咸鲜");
			this.insert(
					db,
					"狮子头",
					20,
					"http://i3.meishichina.com/attachment/recipe/201102/201102161705403.jpg",
					2.0, 2, "红烧", 1, "此小吃因用食碱量比普通酵面团要稍大，所以特别酥香，可贮存数日不会软。");
			this.insert(
					db,
					"鱼丸",
					21,
					"http://h.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=65ee860919d5ad6ebef46cb8e0a252be/9922720e0cf3d7cacc3e82bbf01fbe096a63a937.jpg",
					2.0, 2, "煮", 2,
					"捣成鱼泥，调进薯粉，搅匀后挤成小圆球，入沸汤煮熟。其色如瓷，富有弹性，脆而不腻，为宴席常见菜品。");
		}

		cursor.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public long insert(SQLiteDatabase db, String dish_name, int dish_id,
			String dish_image, double d, int dish_amount, String dish_method,
			int dish_time, String dish_content) {
		// ContentValues类似map，存入的是键值对
		ContentValues contentValues = new ContentValues();

		contentValues.put("dish_name", dish_name);
		contentValues.put("dish_id", dish_id);
		contentValues.put("dish_image", dish_image);
		contentValues.put("dish_difficulty", d);
		contentValues.put("dish_amount", dish_amount);
		contentValues.put("dish_method", dish_method);
		contentValues.put("dish_time", dish_time);
		contentValues.put("dish_content", dish_content);

		long new_id = db.insert(TABLE_NAME, null, contentValues);
		return new_id;
	}

	public void delete(int dish_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_NAME, "dish_id = ?", new String[] { dish_id + "" });
		db.close();
	}

	public ArrayList<JSONObject> query(String whereClause, String[] whereArgs)
			throws JSONException {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.query(TABLE_NAME, null, whereClause, whereArgs, null,
				null, null);
		ArrayList<JSONObject> dishes = new ArrayList<JSONObject>();
		while (c.moveToNext()) {
			JSONObject m = new JSONObject();
			m.put("dish_name", c.getString(c.getColumnIndex("dish_name")));
			m.put("dish_id", c.getString(c.getColumnIndex("dish_id")));
			m.put("dish_image", c.getString(c.getColumnIndex("dish_image")));
			m.put("dish_difficulty",c.getString(c.getColumnIndex("dish_difficulty")));
			m.put("dish_amount", c.getString(c.getColumnIndex("dish_amount")));
			m.put("dish_method", c.getString(c.getColumnIndex("dish_method")));
			m.put("dish_time", c.getString(c.getColumnIndex("dish_time")));
			m.put("dish_content", c.getString(c.getColumnIndex("dish_content")));
			dishes.add(m);
		}
		c.close();
		db.close();
		return dishes;
	}
}
