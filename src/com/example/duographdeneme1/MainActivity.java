package com.example.duographdeneme1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Camera mCameraBack;
	private Camera mCameraFront;
	private CameraPreviewBack mPreviewBack;
	private CameraPreviewFront mPreviewFront;
	private Button mButton;
	private static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{
		mCameraBack=getCameraInstance(0);
		mCameraFront=getCameraInstance(1);
		}catch(Exception ex){
			System.out.println("camera hatasi");
			ex.printStackTrace();
		}

		context=this;
		mButton=(Button)findViewById(R.id.button_capture);
		mPreviewBack=new CameraPreviewBack(this,mCameraBack );
		mPreviewFront=new CameraPreviewFront(context, mCameraFront);
		
		FrameLayout previewBack=(FrameLayout)findViewById(R.id.camera_preview_back);
		FrameLayout previewFront=(FrameLayout)findViewById(R.id.camera_preview_front);
	
		previewBack.addView(mPreviewBack);
		previewFront.addView(mPreviewFront);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	public void capture(View view){
		mPreviewBack.takePicture();
		mPreviewFront.takePicture();
		mPreviewBack.getHolder().removeCallback(mPreviewBack);
		mPreviewFront.getHolder().removeCallback(mPreviewFront);
		

		Intent intent =new Intent(this, CapturedPicsActivity.class);
		BitmapFactory.Options scalingOptions = new BitmapFactory.Options();
	
		scalingOptions.inSampleSize =6;
		scalingOptions.inScaled=false;
		Bitmap bmpBack =BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString()+"/saved_images/imageBack.jpg",scalingOptions);
		Bitmap bmpFront =BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString()+"/saved_images/imageFront.jpg",scalingOptions);
		System.out.println("bmppppppppppppppppp");
		System.out.println(bmpFront==null);
		
		
		Bundle extras = new Bundle();
		extras.putParcelable("imageBack", bmpBack);
		extras.putParcelable("imageFront", bmpFront);
		intent.putExtras(extras);
		startActivity(intent);

		System.out.println("denemeeeee");

	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(int cameraId){
		Camera c = null;
		try {
			c = Camera.open(cameraId); // attempt to get a Camera instance
		}
		catch (Exception e){
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}
	
}
