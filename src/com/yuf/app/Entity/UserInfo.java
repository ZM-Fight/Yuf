package com.yuf.app.Entity;

import com.yuf.app.MyApplication;

import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfo {

	private static UserInfo instance;
	private static Editor editor ;
	private static SharedPreferences sharedPreferences;
	public static UserInfo getInstance() {
		
			
		if (instance==null) {
			sharedPreferences=MyApplication.myapplication.getSharedPreferences("login", Context.MODE_PRIVATE);
			editor=sharedPreferences.edit();//获取编辑器
			instance=new UserInfo();
			initUserInfoWithSharePerfernces();
			return instance;
		}
		else {
			return instance;
		}
		
		
	}
	public double leveldiscout;
	public String username;
	public int userfollows;

	public int userfans;
	public int userpoints;
	public int levelpoints;
	public String useraccount;
	public String levelname;
	public  String sessionid;
	public  String userid;
	public String userpic;
	
public String getUserpic() {
		return userpic;
	}
	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}
private static void initUserInfoWithSharePerfernces()
{
	
	if (sharedPreferences.getBoolean("isLogined", false)) {
		instance.leveldiscout=sharedPreferences.getFloat("leveldiscout", 0);
		instance.username=sharedPreferences.getString("username", "");
		instance.userfollows=sharedPreferences.getInt("userfollows", 0);
		instance.userpoints=sharedPreferences.getInt("userfans", 0);
		instance.levelpoints=sharedPreferences.getInt("userpoints", 0);
		instance.useraccount=sharedPreferences.getString("useraccount", "");
		instance.levelname=sharedPreferences.getString("levelname", "");
		instance.sessionid=sharedPreferences.getString("sessionid", "");
		instance.userid=( "110");
		instance.userpic=sharedPreferences.getString("userpic", "");
	}
}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		editor.putString("sessionid", sessionid);
		editor.commit();
		this.sessionid = sessionid;
	}
	public String getUserid() {

		return userid;
	}
	public void setUserid(String userid) {
		editor.putString("userid", userid);
		editor.commit();
		this.userid = userid;
	}
	public double getLeveldiscout() {
		return leveldiscout;
	}
	public void setLeveldiscout(double leveldiscout) {
		editor.putFloat("leveldiscout", (float)leveldiscout);
		editor.commit();
		this.leveldiscout = leveldiscout;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		editor.putString("username", username);
		editor.commit();
		this.username = username;
	}
	public int getUserfollows() {
		return userfollows;
	}
	public void setUserfollows(int userfollows) {
		editor.putInt("userfollows", userfollows);
		editor.commit();
		this.userfollows = userfollows;
	}
	public int getUserfans() {
		return userfans;
	}
	public void setUserfans(int userfans) {
		editor.putInt("userfans", userfans);
		editor.commit();
		this.userfans = userfans;
	}
	public int getUserpoints() {
		return userpoints;
	}
	public void setUserpoints(int userpoints) {
		editor.putInt("userpoints", userpoints);
		editor.commit();
		this.userpoints = userpoints;
	}
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		editor.putString("useraccount", useraccount);
		editor.commit();
		this.useraccount = useraccount;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		editor.putString("levelname", levelname);
		editor.commit();
		this.levelname = levelname;
	}
	

	public int getLevelpoints() {
		
		return levelpoints;
	}
	public void setLevelpoints(int levelpoints) {
		editor.putInt("levelpoints", levelpoints);
		editor.commit();
		this.levelpoints = levelpoints;
	}

	
}
