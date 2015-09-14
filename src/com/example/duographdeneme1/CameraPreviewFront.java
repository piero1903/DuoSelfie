package com.example.duographdeneme1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;


public class CameraPreviewFront extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder;
	Camera mCamera;
	private Context context;
	private Bitmap bmp=null;
	boolean isFinished=false;

	public CameraPreviewFront(Context context,Camera camera) {
		super(context);
		this.context=context;
		mCamera=camera;
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder=getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, now tell the camera where to draw the preview.
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.

		if (mHolder.getSurface() == null){
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e){
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here

		// start preview with new settings
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();

		} catch (Exception e){
			e.printStackTrace();
		}		
	}
	public void takePicture(){

		mCamera.takePicture(null, null, mPicture);
		System.out.println("take pictureeeee");
		
	//	return bmp;
	}
	
	private PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			
			String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root + "/saved_images");    
			myDir.mkdirs();
		
			String fname = "imageFront"+".jpg";
			File pictureFile = new File (myDir, fname);
			System.out.println("giriyor ??");
			if (pictureFile == null){
				System.out.println("Error creating media file, check storage permissions: ");
				return;
			}

			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();

			/*	BitmapFactory.Options scalingOptions = new BitmapFactory.Options();
				int imgWidth=((ImageView)findViewById(R.id.imageView)).getMeasuredWidth();
				scalingOptions.inSampleSize = mCamera.getParameters().getPictureSize().width /imgWidth ;
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, scalingOptions);
				System.out.println("denemeeeeeeeeeeeee "+bmp.getWidth());*/

			} catch (FileNotFoundException e) {
				System.out.println("File not found: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Error accessing file: " + e.getMessage());
			}
			mCamera.release();
		}
	};

}
