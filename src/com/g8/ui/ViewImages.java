package com.g8.ui;

import com.g8.libs.TouchImageView;
import com.g8.libs.TouchImageView.OnTouchImageViewListener;
import com.g8.prestige.CustomFragmentActivity;
import com.g8.prestige.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ViewImages extends Activity implements OnTouchListener {
	private TouchImageView image;
	boolean isZoomed ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_image);
		image = (TouchImageView) findViewById(R.id.img);
		Intent intent = getIntent();
		String message = intent.getStringExtra("A");
		CustomFragmentActivity.imageLoader2.displayImage(message, image,
				CustomFragmentActivity.options);
		 image.setOnTouchListener(this);
		image.setOnTouchImageViewListener(new OnTouchImageViewListener() {

			@Override
			public void onMove() {
				PointF point = image.getScrollPosition();
				RectF rect = image.getZoomedRect();
				float currentZoom = image.getCurrentZoom();
//				if (currentZoom == 0.75) {
//					finish();
//				}
				 isZoomed = image.isZoomed();
			}
		});
	}

	private static final int ACTION_TYPE_DEFAULT = 0;
	private static final int ACTION_TYPE_UP = 1;
	private static final int ACTION_TYPE_RIGHT = 2;
	private static final int ACTION_TYPE_DOWN = 3;
	private static final int ACTION_TYPE_LEFT = 4;
	private static final int SLIDE_RANGE = 100;
	private float mTouchStartPointX;
	private float mTouchStartPointY;
	private int mActionType = ACTION_TYPE_DEFAULT;

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int x = (int) event.getRawX();
		int y = (int) event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTouchStartPointX = event.getRawX();
			mTouchStartPointY = event.getRawY();
			Log.v("HAI", "ac down");

			if (mActionType == ACTION_TYPE_UP) {
				slideUp();
			} else if (mActionType == ACTION_TYPE_RIGHT) {
				slideToRight();
			} else if (mActionType == ACTION_TYPE_DOWN) {
				slideDown();
			} else if (mActionType == ACTION_TYPE_LEFT) {
				slideToLeft();
			} else {
				// finish();
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (mTouchStartPointX - x > SLIDE_RANGE) {
				mActionType = ACTION_TYPE_LEFT;
			} else if (x - mTouchStartPointX > SLIDE_RANGE) {
				mActionType = ACTION_TYPE_RIGHT;
			} else if (mTouchStartPointY - y > SLIDE_RANGE) {
				mActionType = ACTION_TYPE_UP;
			} else if (y - mTouchStartPointY > SLIDE_RANGE) {
				mActionType = ACTION_TYPE_DOWN;
			}
			break;
		// case MotionEvent.ACTION_UP:
		// if (mActionType == ACTION_TYPE_UP) {
		// slideUp();
		// } else if (mActionType == ACTION_TYPE_RIGHT) {
		// slideToRight();
		// } else if (mActionType == ACTION_TYPE_DOWN) {
		// slideDown();
		// } else if (mActionType == ACTION_TYPE_LEFT) {
		// slideToLeft();
		// } else {
		// slide();
		// }
		// break;
		default:
			break;
		}
		return true;
	}

	protected void slideToLeft() {
		// Log.d("HAI", "slideToLeft() was called.");
		if(!isZoomed){
			finish();
		}
		
	}

	protected void slideToRight() {
		if(!isZoomed){
			finish();
		}
		
	}

	protected void slideUp() {
//		Log.v("HAI", "ac slideUp");
//		finish();
	}

	protected void slideDown() {
//		Log.v("HAI", "ac slideDown");
//		finish();

	}
}
