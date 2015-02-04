package com.yuf.app.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.PostFile;

public class Tab3AddWorkActivity extends Activity {
	private static final int PHOTO_PICKED_WITH_DATA = 0;
	private ImageView backImageView;
	private Button photoButton;
	private Button albumButton;
	private Button publicButton;
	private AutoCompleteTextView nameEditText;
	private TextView timeTextView;
	private EditText shareContentEditText;
	private String saveDir;
	private File file;
	private Bitmap photo;
	private ImageView foodImageView;
	private String picName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.tab3_add_work);
	saveDir= Environment.getExternalStorageDirectory()
			.getPath() + "/yuf_image";
	photoButton=(Button)findViewById(R.id.tab3_addwork_photo_button);
	albumButton=(Button)findViewById(R.id.tab3_addwork_localphoto_button);
	publicButton=(Button)findViewById(R.id.tab3_addwork_public_button);
	nameEditText=(AutoCompleteTextView)findViewById(R.id.tab3_addwork_name);
	timeTextView=(TextView)findViewById(R.id.tab3_addwork_time_textview);
	shareContentEditText=(EditText)findViewById(R.id.tab3_addwork_content_textview);
	foodImageView=(ImageView)findViewById(R.id.tab3_addwork_photo_image);
	picName=UserInfo.getInstance().getUserid()+String.valueOf(System.currentTimeMillis())+".jpg";
	
	timeTextView.setText(timeString());
	
	
	publicButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			JSONObject json=new JSONObject();
			try {
				json.put("userId",Integer.valueOf(UserInfo.getInstance().getUserid()));
				json.put("postTitle", nameEditText.getText().toString());
				json.put("postTime",timeTextView.getText().toString());
				json.put("postContent", shareContentEditText.getText().toString());
				json.put("postPicture",picName);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/post/insertPost", json, new com.android.volley.Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if (response.getString("insertPost").equals("success")) {
						
						UploadFilesTask task=new UploadFilesTask();
						task.execute(file);
					}
					else { 
						Toast.makeText(Tab3AddWorkActivity.this, "分享失败",
								Toast.LENGTH_SHORT).show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new com.android.volley.Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
MyApplication.requestQueue.add(request);
MyApplication.requestQueue.start();
		}
	});
	albumButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			postImageFromAlbum();
		}
	});
	photoButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			destoryImage();
			String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				file = new File(saveDir,picName);
				file.delete();
				if (!file.exists()) {
					try {
						if (!file.getParentFile().exists()) {
							file.getParentFile().mkdirs();
						}
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
						Toast.makeText(Tab3AddWorkActivity.this, "照片创建失败!",
								Toast.LENGTH_LONG).show();
						return;
					}
				}
				Intent intent = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
				startActivityForResult(intent, 1);
			} else {
				Toast.makeText(Tab3AddWorkActivity.this, "sdcard无效或没有插入!",
						Toast.LENGTH_SHORT).show();
			}
		
		}
	});

	
	backImageView=(ImageView)findViewById(R.id.tab3_add_work_back_imageview);
	backImageView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			onBackPressed();
//			System.gc();
			// TODO Auto-generated method stub
		}
	});
}
	private void destoryImage() {
		if (photo != null) {
			photo.recycle();
			photo = null;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == PHOTO_PICKED_WITH_DATA&& resultCode == RESULT_OK) {
			Uri originalUri = data.getData();        //获得图片的uri 
			ContentResolver resolver = getContentResolver();
            try {
            	photo=MediaStore.Images.Media.getBitmap(resolver, originalUri);
            	foodImageView.setImageBitmap(photo);
				file = new File(saveDir,picName);
				UploadFilesTask task=new UploadFilesTask();
				task.execute(file);
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        //显得到bitmap图片
		}
		
		if (requestCode == 1 && resultCode == RESULT_OK) {
			if (file != null && file.exists()) {
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inSampleSize = 2;
				photo = BitmapFactory.decodeFile(file.getPath(), option);
				foodImageView.setImageBitmap(photo);
			}
		}
	}
	@Override
	protected void onDestroy() {
		destoryImage();
//		backImageView=null;
//		publicButton=null;
//		nameEditText=null;
//		timeTextView=null;
//		shareContentEditText=null;
//		saveDir=null;
//		file=null;
//		photo=null;
//		foodImageView=null;
//		picName=null;
		
	Log.d("addmywork", "onDestroy");
		super.onDestroy();
	}
	
	 private class UploadFilesTask extends AsyncTask<File, Integer, Long> {


	     protected void onPostExecute(Long result) {
	    	 
	    		Toast.makeText(Tab3AddWorkActivity.this, "分享成功",
						Toast.LENGTH_SHORT).show();
	    		onBackPressed();
	     }

		@Override
		protected Long doInBackground(File... params) {
			// TODO Auto-generated method stub
			compressBmpToFile(photo, params[0]);
			PostFile.uploadFile(params[0], "http://110.84.129.130:8080/Yuf/post/upload/picture");
			return null;
		}
		
	 }
	 private void compressBmpToFile(Bitmap bmp,File file)
	 {
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        int options = 80;//个人喜欢从80开始,  
	        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
	        while (baos.toByteArray().length / 1024 > 100) {   
	            baos.reset();  
	            options -= 10;  
	            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
	        }  
	        try {  
	            FileOutputStream fos = new FileOutputStream(file);  
	            fos.write(baos.toByteArray());  
	            fos.flush();  
	            fos.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	 }

private String timeString() {
	// TODO Auto-generated method stub
	long currentTime = System.currentTimeMillis();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date(currentTime);
	return formatter.format(date);
}
private void postImageFromAlbum() {
	destoryImage();
	doPickPhotoFromGallery(this);
}
/** 
 * 请求Gallery相册程序 
 * @param context 
 * @param iscrop 
 */  
private static void doPickPhotoFromGallery(Context context) {  
    try {  
        Intent intent = new Intent(Intent.ACTION_PICK);  
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");  
        ((Activity) context).startActivityForResult(intent,  
        		PHOTO_PICKED_WITH_DATA);  
    } catch (Exception e) {  
        Toast.makeText(context, "没有图片",  
                Toast.LENGTH_LONG).show();  
            e.printStackTrace();  
    }  
}
}
