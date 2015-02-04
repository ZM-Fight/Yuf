package com.yuf.app.db;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyProvider extends ContentProvider {

	private SQLiteHelper dbHelper;
	// 定义一个UriMatcher类
	private static final UriMatcher MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);
	private static final int ORDER = 1;
	private static final int ORDERS = 2;
	private static final int ADDRESS = 3;
	private static final int ADDRESSES = 4;
	private static final int ADDRESS_ID=5;
	private static final int ORDER_ID=6;
    private static final int ORDER_SELECTED = 7; 
    private static final int ORDER_TOBEADDED = 8;
    private static final int ORDER_TOBEADDEDD = 9;
	static {
		MATCHER.addURI("com.yuf.app.myprovider", "orders", ORDERS);
		MATCHER.addURI("com.yuf.app.myprovider", "order", ORDER);
		MATCHER.addURI("com.yuf.app.myprovider", "order/#", ORDER_ID);
		MATCHER.addURI("com.yuf.app.myprovider", "isselected_order", ORDER_SELECTED);
		MATCHER.addURI("com.yuf.app.myprovider", "orderToBeAdded", ORDER_TOBEADDED);
		MATCHER.addURI("com.yuf.app.myprovider", "order_adds", ORDER_TOBEADDEDD);
		MATCHER.addURI("com.yuf.app.myprovider", "addresses", ADDRESSES);
		MATCHER.addURI("com.yuf.app.myprovider", "address", ADDRESS);
		MATCHER.addURI("com.yuf.app.myprovider", "address/#", ADDRESS_ID);
	}
	@Override
	public boolean onCreate() {
		System.out.println("---oncreate----");
		dbHelper = new SQLiteHelper(this.getContext());
		return false;
	}

	// 查询数据
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (MATCHER.match(uri)) {
		case ORDERS:
			// 查询所有的数据
			return db.query("orders", projection, selection, selectionArgs,
					null, null, sortOrder);
        case ORDER_ID:
			// 查询某个ID的数据
			// 通过ContentUris这个工具类解释出ID
			long id = ContentUris.parseId(uri);
			String where = " _id=" + id;
			if (!"".equals(selection) && selection != null) {
				where = selection + " and " + where;
            }
            return db.query("orders", projection, where, selectionArgs, null,
					null, sortOrder);
            
        case ORDER_SELECTED:
        	String where0 = "isSelect=1";
			if (!"".equals(selection) && selection != null) {
				where0 = selection + " and " + where0;

			}
            return db.query("orders", projection, where0, selectionArgs, null,
					null, sortOrder);
        case ORDER_TOBEADDED:
        	String whereToBeAdded ="";
			if (!"".equals(selection) && selection != null) {
				whereToBeAdded = selection + whereToBeAdded;
			}
			return db.query("orders", projection, whereToBeAdded, selectionArgs, null,
					null, sortOrder);
        case ADDRESSES:// 查询所有的数据
			return db.query("addresses", projection, selection, selectionArgs,
					null, null, sortOrder);

		case ADDRESS_ID:// 查询某个ID的数据 ，通过ContentUris这个工具类解释出ID
			long address_id = ContentUris.parseId(uri);
			String address_where = " _id=" + address_id;
			if (!"".equals(selection) && selection != null) {
				address_where = selection + " and " + address_where;

			}
            return db.query("addresses", projection, address_where, selectionArgs, null,
					null, sortOrder);
		default:

			throw new IllegalArgumentException("unknow uri" + uri.toString());
		}

	}

	// 返回当前操作的数据的mimeType
	@Override
	public String getType(Uri uri) {
		switch (MATCHER.match(uri)) {
		case ORDERS:
			return "vnd.android.cursor.dir/ORDER";
		case ORDER:
			return "vnd.android.cursor.item/ORDER";
		case ADDRESSES:
			return "vnd.android.cursor.dir/ADDRESSES";
		case ADDRESS:
			return "vnd.android.cursor.item/ADDRESS";
		default:
			throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
	}

	// 插入数据
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Uri insertUri = null;
		switch (MATCHER.match(uri)) {
		case ORDER:
			long rowid1 = db.insert("orders", "name", values);
			insertUri = ContentUris.withAppendedId(uri, rowid1);
			break;
		case ADDRESS:
			long rowid2 = db.insert("addresses", "name", values);
			insertUri = ContentUris.withAppendedId(uri, rowid2);
			break;
		default:
			throw new IllegalArgumentException("Unkwon--- Uri:" + uri.toString());
		}
		return insertUri;
	}

	// 删除数据
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = 0;
		switch (MATCHER.match(uri)) {
		case ORDERS:
			count = db.delete("orders", selection, selectionArgs);
			return count;

		case ORDER_ID:
			long id = ContentUris.parseId(uri);
			String where = "_id=" + id;
			if (selection != null && !"".equals(selection)) {
				where = selection + " and " + where;
			}
			count = db.delete("orders", where, selectionArgs);
			return count;
			
		case ADDRESSES:
			count = db.delete("addresses", selection, selectionArgs);
			return count;

		case ADDRESS_ID:
			long id2 = ContentUris.parseId(uri);
			String where2 = "_id=" + id2;
			if (selection != null && !"".equals(selection)) {
				where2 = selection + " and " + where2;
			}
			count = db.delete("addresses", where2, selectionArgs);
			return count;
		default:
			throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
	}

	// 更新数据
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		int count = 0;
		switch (MATCHER.match(uri)) {
		case ORDERS:
			count = db.update("orders", values, selection, selectionArgs);
			break;
		case ORDER_ID:
			// 通过ContentUri工具类得到ID
			long id = ContentUris.parseId(uri);
			String where = "_id=" + id;
			if (selection != null && !"".equals(selection)) {
				where = selection + " and " + where;
			}
			count = db.update("orders", values, where, selectionArgs);
			break;
	    case ORDER_TOBEADDEDD:
	    	String whereToBeAdded ="";
	        	if (!"".equals(selection) && selection != null) {
	        		whereToBeAdded =selection+whereToBeAdded;
				}count = db.update("orders", values, whereToBeAdded, selectionArgs);
				break;
		case ADDRESSES:
			count = db.update("addresses", values, selection, selectionArgs);
			break;
		case ADDRESS_ID:
			// 通过ContentUri工具类得到ID
			long id2 = ContentUris.parseId(uri);
			String where2 = "_id=" + id2;
			if (selection != null && !"".equals(selection)) {
				where = selection + " and " + where2;
			}
			count = db.update("addresses", values, where2, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
		}
		return count;
	}

}