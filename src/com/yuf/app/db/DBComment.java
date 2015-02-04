package com.yuf.app.db;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBComment extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "comment";
	private static final String TABLE_NAME = "tb_comment";
	private static final int DATABASE_VERSION = 1;

	public DBComment(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql_tb_comment = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_NAME
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,dish_id INTEGER  NOT NULL,user_name TEXT NOT NULL,dish_grade INTEGER NOT NULL,dish_comment TEXT NOT NULL)";
		db.execSQL(sql_tb_comment);
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		if (cursor.getCount() == 0) {

			this.insert(db, 1, "王安", 4, "有营养");
			this.insert(db, 1, "林峰", 4, "不错");
			this.insert(db, 1, "蓝灵", 3, "重口味");
			this.insert(db, 1, "张丹丹", 5, "好吃");
			this.insert(db, 2, "李淼", 1, "材料少");
			this.insert(db, 2, "何琳", 4, "好吃");
			this.insert(db, 13, "方世玉", 2, "材料少");
			this.insert(db, 14, "王尼玛", 1, "挺好的");
			this.insert(db, 13, "王蜜桃", 1, "难做");
			this.insert(db, 12, "金三胖", 1, "不建议");
			this.insert(db, 13, "trec", 1, "太油腻");
			this.insert(db, 16, "ffnbs", 2, "难");
			this.insert(db, 17, "hdfgsx", 2, "费事");
			this.insert(db, 10, "an_ge", 4, "不错");
			this.insert(db, 1, "GFDHG", 4, "好吃");
			this.insert(db, 2, "fdgfd", 4, "yummy");
			this.insert(db, 5, "dfgbnhgfFD", 5, "好吃");
			this.insert(db, 5, "Dfgrg", 5, "有营养");
			this.insert(db, 6, "DFGG", 2, "味道奇葩");
			this.insert(db, 7, "hfj", 4, "重口,收藏,不错");
			this.insert(db, 8, "fghhFF", 5, "好吃");
			this.insert(db, 9, "VGRFB", 2, "难做，费事，有营养");
			this.insert(db, 5, "VRFGR", 4, "收藏，delicious");
			this.insert(db, 4, "RTGFR", 4, "收藏，delicious");
			this.insert(db, 8, "FGBFB", 5, "强烈推荐");
			this.insert(db, 8, "VFFR", 4, "有营养，但是太淡了，不建议");
			this.insert(db, 4, "FFBFBF", 4, "有营养");
			this.insert(db, 4, "FGGDgfhh", 3, "有营养，但是太淡了，不建议");
			this.insert(db, 7, "nhgn", 3, "有营养，但是太淡了，不建议");
			this.insert(db, 7, "nhgnhgmk", 3, "有营养，就是太油腻");
			this.insert(db, 5, "fjhfjnh", 1, "难吃，难做，费事，材料少");
			this.insert(db, 2, "yyfdfDD", 5, "强烈推荐");
			this.insert(db, 1, "hjjh", 4, "难做，费事，有营养");
			this.insert(db, 4, "FFG", 4, "有营养");
			this.insert(db, 1, "hghh", 1, "太淡了，不建议");
			this.insert(db, 7, "BFFF", 3, "有营养，但是太淡了，不建议");
			this.insert(db, 7, "hjhVF", 2, "太淡了，不建议");
			this.insert(db, 4, "FBFB", 1, "太淡了，不建议");
			this.insert(db, 8, "DDDfgh", 3, "难吃，难做");
			this.insert(db, 5, "FVFF", 4, "收藏，delicious");
			this.insert(db, 4, "ghhccc", 5, "强烈推荐");
			this.insert(db, 5, "AWWsss", 4, "味道好，强烈推荐，收藏");
			this.insert(db, 2, "DDff", 4, "有营养");
			this.insert(db, 8, "Fdggjhj", 5, "味道好，强烈推荐，收藏");
			this.insert(db, 7, "hjjG", 4, "味道好，强烈推荐，收藏");
			this.insert(db, 4, "Yuuj", 3, "太油腻，做的时候油烟大");
			this.insert(db, 1, "ffgfeer", 2, "太油腻，做的时候油烟大");
			this.insert(db, 5, "regdfd", 4, "收藏，yummy");
			this.insert(db, 5, "FDSdf", 4, "收藏，delicious");
			this.insert(db, 7, "anddghge", 4, "有营养，就是太油腻");
			this.insert(db, 4, "gfhtrg", 3, "有营养，就是太油腻");
			this.insert(db, 5, "FGFGD", 4, "收藏，yummy");

		}

		cursor.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public long insert(SQLiteDatabase db, int dish_id, String user_name,
			int dish_grade, String dish_comment) {
		// ContentValues类似map，存入的是键值对
		ContentValues contentValues = new ContentValues();

		contentValues.put("dish_id", dish_id);
		contentValues.put("user_name", user_name);
		contentValues.put("dish_grade", dish_grade);
		contentValues.put("dish_comment", dish_comment);

		long new_id = db.insert(TABLE_NAME, null, contentValues);
		return new_id;
	}

	public void delete(int _id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_NAME, "_id = ?", new String[] { _id + "" });
		db.close();
	}

	public ArrayList<JSONObject> getComments(int dish_id) throws JSONException {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.query(TABLE_NAME, null, "dish_id = ?",
				new String[] { dish_id + "" }, null, null, null);
		ArrayList<JSONObject> comments = new ArrayList<JSONObject>();
		while (c.moveToNext()) {
			JSONObject m = new JSONObject();
			m.put("_id", c.getInt(c.getColumnIndex("_id")));
			m.put("dish_id", c.getInt(c.getColumnIndex("dish_id")));
			m.put("user_name", c.getString(c.getColumnIndex("user_name")));
			m.put("dish_grade", c.getInt(c.getColumnIndex("dish_grade")));
			m.put("dish_comment", c.getString(c.getColumnIndex("dish_comment")));
			comments.add(m);
		}
		c.close();
		db.close();
		return comments;
	}

	public int getCommentCount(int dish_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.query(TABLE_NAME, new String[] { "count(*) as cnt" },
				"dish_id = ?", new String[] { dish_id + "" }, null, null, null);
		if (c.moveToFirst()) {
			int cnt = c.getInt(c.getColumnIndex("cnt"));
			db.close();
			return cnt;
		} else {
			db.close();
			return 0;
		}
	}

	public int getAvgGrade(int dish_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.query(TABLE_NAME,
				new String[] { "avg(dish_grade) as avg" }, "dish_id = ?",
				new String[] { dish_id + "" }, null, null, null);
		if (c.moveToFirst()) {
			int cnt = c.getInt(c.getColumnIndex("avg"));
			db.close();
			return cnt;
		} else {
			db.close();
			return 0;
		}
	}
}
