package com.yuf.app.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.yuf.app.MyApplication;
import com.yuf.app.Entity.UserInfo;
import com.yuf.app.http.extend.BitmapCache;
import com.yuf.app.http.extend.PostFile;

import android.R.integer;
import android.R.mipmap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tab3SetUserInfoActivity extends Activity {
private static final int PHOTO_PICKED_WITH_DATA = 0;
private ImageView backImageView;
private NetworkImageView profileImageView;
private ImageLoader mImageLoader;
private AlertDialog dlg;
private TextView nicknameTextView;
private TextView phoneTextView;
private Bitmap photo;
private File file;
private String saveDir;
private String picName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//
		//
		//
		super.onCreate(savedInstanceState);
		
		saveDir= Environment.getExternalStorageDirectory()
				.getPath() + "/yuf_image";
		picName=picName=UserInfo.getInstance().getUserid()+String.valueOf(System.currentTimeMillis())+".jpg";
		
		mImageLoader=new ImageLoader(MyApplication.requestQueue, new BitmapCache());
		setContentView(R.layout.tab3_setuserinfo_activity);
		profileImageView=(NetworkImageView)findViewById(R.id.tab3_setuserinfo_profile);
		profileImageView.setDefaultImageResId(R.drawable.no_pic);
		profileImageView.setErrorImageResId(R.drawable.no_pic);
		profileImageView.setImageUrl("http://110.84.129.130:8080/Yuf"+UserInfo.getInstance().userpic, mImageLoader);
		backImageView=(ImageView)findViewById(R.id.tab3_setUserInfo_back_imageview);
		backImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				// TODO Auto-generated method stub
				
			}
		});
		
		nicknameTextView=(TextView)findViewById(R.id.tab3_setuserinfo_nickname);
		nicknameTextView.setText(UserInfo.getInstance().username);
		
		
	}
	public void onClickProfile(View view)
	{
		new AlertDialog.Builder(this).setTitle("列表框").setItems(
			     new String[] { "从相册选择", "拍照上传" }, new DialogInterface.OnClickListener() {  
			    	    @Override  
			    	    public void onClick(DialogInterface dialog, int which) {  
			    	        // TODO Auto-generated method stub  
			    	        switch (which) {  
			    	        case 0:  
			    	        	postImageFromAlbum();
			    	            break;  
			    	        case 1:  
			    	        	postImageFromCamera();
			    	            break;  
			    	        }  
			    	    } } ).setNegativeButton(
			     "取消", null).show();
	}
	public void onClickNickName(View view){
		
		final EditText editText = new EditText(this); 
		new AlertDialog.Builder(this).setTitle("请输入").setIcon(
			     android.R.drawable.ic_dialog_info).setView(
			     editText).setPositiveButton("确定", 
			    		 new DialogInterface.OnClickListener() {                
			    	    @Override  
			    	    public void onClick(DialogInterface dialog, int which) {  
			    	        // TODO Auto-generated method stub  
			    	        Toast.makeText(Tab3SetUserInfoActivity.this, "您输入的内容是："+editText.getText(), Toast.LENGTH_SHORT).show();  
			    	        changeNickName(editText.getText().toString());
			    	        
			    	    }  
			     }
			    		 )
			    .setNegativeButton("取消", null).show();
	}
	public void onClickPhone(View view){
		final EditText editText = new EditText(this); 
		new AlertDialog.Builder(this).setTitle("请输入").setIcon(
			     android.R.drawable.ic_dialog_info).setView(
			     editText).setPositiveButton("确定", 
			    		 new DialogInterface.OnClickListener() {                
			    	    @Override  
			    	    public void onClick(DialogInterface dialog, int which) {  
			    	        // TODO Auto-generated method stub  
			    	        Toast.makeText(Tab3SetUserInfoActivity.this, "您输入的内容是："+editText.getText(), Toast.LENGTH_SHORT).show();  
			    	        changeNickName(editText.getText().toString());
			    	    }  
			     }
			    		 )
			    .setNegativeButton("取消", null).show();
	}
	public void onClickMyAddress(View view){
		Intent	intent=new Intent(Tab3SetUserInfoActivity.this,Tab2AddressActivity.class);
		startActivity(intent);
	}
	
	private void changeNickName(String newName) {
		nicknameTextView.setText(newName);
		UserInfo.getInstance().username=newName;
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("sessionId", UserInfo.getInstance().sessionid);
			jsonObject.put("userId",Integer.valueOf(UserInfo.getInstance().userid));
			jsonObject.put("nickName", newName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonObjectRequest request=new JsonObjectRequest(Method.POST, "http://110.84.129.130:8080/Yuf/user/updateUserNickName", jsonObject, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if (response.getString("updateUserNickName").equals("success")) {
						Toast.makeText(Tab3SetUserInfoActivity.this, "昵称修改成", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
		MyApplication.requestQueue.add(request);
		MyApplication.requestQueue.start();
	}
	private void changePhone(String newPhone)
	{
		
	}
	private void postImageFromAlbum() {
		destoryImage();
		doPickPhotoFromGallery(this);
	}
	private void postImageFromCamera()
	{

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
					Toast.makeText(this, "照片创建失败!",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
			Intent intent = new Intent(
					"android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(intent, 1);
		} else {
			Toast.makeText(this, "sdcard无效或没有插入!",
					Toast.LENGTH_SHORT).show();
		}
	
	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PHOTO_PICKED_WITH_DATA&& resultCode == RESULT_OK) {
			Uri originalUri = data.getData();        //获得图片的uri 
			ContentResolver resolver = getContentResolver();
            try {
            	photo=MediaStore.Images.Media.getBitmap(resolver, originalUri);
				profileImageView.setImageBitmap(photo);
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
				profileImageView.setImageBitmap(photo);
				UploadFilesTask task=new UploadFilesTask();
				task.execute(file);
			}
		}
		
		
		
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
    
	private void destoryImage() {
		if (photo != null) {
			photo.recycle();
			photo = null;
		}
	}

	private class UploadFilesTask extends AsyncTask<File, Integer, Long> {
	
	
	     protected void onPostExecute(Long result) {
	    	 
	    		Toast.makeText(Tab3SetUserInfoActivity.this, "修改头像成功",
						Toast.LENGTH_SHORT).show();
	     }
	
		@Override
		protected Long doInBackground(File... params) {
			// TODO Auto-generated method stub
			compressBmpToFile(photo, params[0]);
			PostFile.uploadFile(params[0], "http://110.84.129.130:8080/Yuf/user/upload/picture/"+UserInfo.getInstance().sessionid);
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
}
