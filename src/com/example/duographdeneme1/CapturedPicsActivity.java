package com.example.duographdeneme1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CapturedPicsActivity extends Activity {

	private ImageView imgViewBack;
	private ImageView imgViewFront;
	 boolean isImageBackFitToScreen;
	 boolean isImageFrontFitToScreen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capturedpics);
		imgViewBack =(ImageView)findViewById(R.id.imageView);
		imgViewFront=(ImageView)findViewById(R.id.imageView2);
		Bundle extras = getIntent().getExtras();
		Bitmap bmpBack = (Bitmap) extras.getParcelable("imageBack");
		Bitmap bmpFront = (Bitmap) extras.getParcelable("imageFront");

		System.out.println("bundleeeeeee");
		
		imgViewBack.setImageBitmap(bmpBack );
		imgViewFront.setImageBitmap(bmpFront);
		imgViewBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isImageBackFitToScreen) {
	                    isImageBackFitToScreen=false;
	                    imgViewBack.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	                    imgViewBack.setAdjustViewBounds(true);
	                }else{
	                    isImageBackFitToScreen=true;
	                    imgViewBack.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
	                    imgViewBack.setScaleType(ImageView.ScaleType.FIT_XY);
	                }
				
			}
		});
		
		imgViewFront.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isImageFrontFitToScreen) {
                    isImageFrontFitToScreen=false;
                    imgViewFront.setScaleType(ImageView.ScaleType.FIT_XY);
                    imgViewFront.setLayoutParams(new LinearLayout.LayoutParams(400,200));
                    imgViewFront.setAdjustViewBounds(true);
                   
                }else{
                    isImageFrontFitToScreen=true;
                    imgViewFront.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imgViewFront.setScaleType(ImageView.ScaleType.FIT_XY);
                }
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capturedpics, menu);
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
}
