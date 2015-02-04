package com.yuf.app.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

import com.yuf.app.MyApplication;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
public class Tab3AboutUsActivity extends Activity {

	private ImageView backImageView;
	private Button checkRefreshButton;
	 private String downloadUrlString;
	 private String savePath;
	 private String saveFileName;
	 private Dialog noticeDialog;
	     private Dialog downloadDialog;
	     private ProgressBar mProgress;
		protected boolean interceptFlag=false;
	     private static final int DOWN_UPDATE = 1;

	     private static final int DOWN_OVER = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.tab3_about_our);
	savePath=Environment.getExternalStorageDirectory().toString();
//	savePath=getApplicationContext().getCacheDir().getAbsolutePath();
	checkRefreshButton=(Button)findViewById(R.id.tab3_about_our_check_refresh_button);
	checkRefreshButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		checkRefresh();
			
		}
	});
	
	backImageView=(ImageView)findViewById(R.id.tab3_about_our_backimageview);
	backImageView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			onBackPressed();
			// TODO Auto-generated method stub
			
		}
	});
	

	}

 private void checkRefresh() {
	
		JsonObjectRequest request=new JsonObjectRequest(Method.GET, "http://110.84.129.130:8080/Yuf/update/getNewApkVersion/"+getVersion(), null,  new Response.Listener<JSONObject>()  
     {  

         @Override  
         public void onResponse(JSONObject response)  
         {  
         	
         		
						Log.d("TAG", response.toString());
						try {
//							saveFileName=savePath+"/yuf.apk";
							saveFileName=savePath+response.getString("apkUrl");
							downloadUrlString="http://110.84.129.130:8080/Yuf"+response.getString("apkUrl");
							showNoticeDialog();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
         	
              
         }  
     }, new Response.ErrorListener()  
     {  

         @Override  
         public void onErrorResponse(VolleyError error)  
         {  
             Log.e("TAG", error.getMessage(), error);  
         }  
     });

		//将JsonObjectRequest 加入RequestQuene
MyApplication.requestQueue.add(request);
Log.d("TAG",request.toString());
MyApplication.requestQueue.start();
	 
}	
 
	private void showNoticeDialog(){
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("软件版本更新");
		builder.setMessage("");
		builder.setPositiveButton("下载", new  android.content.DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();			
			}
		});
		builder.setNegativeButton("以后再说", new android.content.DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}
	
	private void showDownloadDialog(){
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("软件版本更新");
		
		final LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar)v.findViewById(R.id.progress);
		
		builder.setView(v);
		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();
		
		downloadApk();
	}
 
 /**
  * 获取版本号
  * @return 当前应用的版本号
  */
 private String getVersion() {
     try {
         PackageManager manager = this.getPackageManager();
         PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
         String version = info.versionName;
         return  version;
     } catch (Exception e) {
         e.printStackTrace();
        return null;
     }
 }
 private void downloadApk() {
	 new ProgressBarAsyncTask(mProgress).execute(1000);
	
}
 
 
 /**
  * 安装apk
  * @param url
  */
	private void installApk(){
		File apkfile = new File(saveFileName);
     if (!apkfile.exists()) {
         return;
     }  

     Intent i = new Intent(Intent.ACTION_VIEW);

 
    i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive"); 
     startActivity(i);
//     apkfile.delete();
	
	}
	
	
	public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {  
		  
//	    private TextView textView;  
	    private ProgressBar progressBar;  
	      
	      
	    public ProgressBarAsyncTask( ProgressBar progressBar) {  
	        super();  
//	        this.textView = textView;  
	        this.progressBar = progressBar;  
	    }  
	  
	  
	    /**  
	     * 这里的Integer参数对应AsyncTask中的第一个参数   
	     * 这里的String返回值对应AsyncTask的第三个参数  
	     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改  
	     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作  
	     */  
	    @SuppressLint("NewApi") @Override  
	    protected String doInBackground(Integer... params) {  
//	       //down
	    	try {
	    	URL url = new URL(downloadUrlString);
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			int length = conn.getContentLength();
			InputStream is = conn.getInputStream();
			
//			File file = new File(savePath);
//			if(!file.exists()){
//				file.mkdir();
//			}
			
			File ApkFile = new File(saveFileName);
			ApkFile.setReadable(true);
			ApkFile.setExecutable(true);
			ApkFile.setWritable(true);
			Log.d("Tag", "savefilename="+saveFileName);
			if(!ApkFile.getParentFile().exists()){
		ApkFile.getParentFile().mkdirs();
	
			}
			
			
			
			
			FileOutputStream fos;
			
			fos = new FileOutputStream(ApkFile);
		
			
			int count = 0;
			byte buf[] = new byte[1024];
			
			do{   		   		
	    		int numread = is.read(buf);
	    		count += numread;
	    	    publishProgress((int)(((float)count / length) * 100));
	    	    //更新进度
	    		fos.write(buf,0,numread);
	    		
	    	}while(!interceptFlag&((int)(((float)count / length) * 100)<100));//点击取消就停止下载.
			
			fos.close();
			is.close();
	    	
	    	} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return  "";
	    }  
	  
	  
	    /**  
	     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）  
	     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置  
	     */  
	    @Override  
	    protected void onPostExecute(String result) {  
	    	downloadDialog.dismiss();
	    	installApk();
//	        textView.setText("异步操作执行结束" + result);  
	    }  
	  
	  
	    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置  
	    @Override  
	    protected void onPreExecute() {  
//	        textView.setText("开始执行异步线程");  
	    }  
	  
	  
	    /**  
	     * 这里的Intege参数对应AsyncTask中的第二个参数  
	     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行  
	     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作  
	     */  
	    @Override  
	    protected void onProgressUpdate(Integer... values) {  
	        int vlaue = values[0];  
	        progressBar.setProgress(vlaue);  
	    }  
	  
	      
	      
	      
	  
	}  
}
