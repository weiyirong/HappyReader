package com.weiyi.reader.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 功能：欢迎界面，处理一些程序载入时动画信息
 * 
 * @author 魏艺荣
 * @version 1.0
 * */
public class WelcomeActivity extends Activity {
	/** Called when the activity is first created. */
	int screenHeight, screenWidth;
	ImageView view;
	LinearLayout layout;
	ImageView left, right;
	Animation leftAnimation, rightAnimation;
	Bitmap bg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_in);
		view = (ImageView) findViewById(R.id.welcome_bg);

		Animation animation = AnimationUtils
				.loadAnimation(this, R.anim.welcome);
		view.setAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				setContentView();
			}
		});
	}

	private void setContentView() {
		view.setDrawingCacheEnabled(true);
		bg = Bitmap.createBitmap(view.getDrawingCache());
		setContentView(R.layout.welcome_out);
		layout = (LinearLayout) findViewById(R.id.welcomeIn);
		left = (ImageView) findViewById(R.id.left);
		right = (ImageView) findViewById(R.id.right);
		Bitmap leftBitmap = Bitmap.createBitmap(bg, 0, 0, bg.getWidth() / 2,
				bg.getHeight());
		Bitmap rightBitmap = Bitmap.createBitmap(bg, bg.getWidth() / 2, 0,
				bg.getWidth() / 2, bg.getHeight());
		left.setBackgroundDrawable(new BitmapDrawable(leftBitmap));
		right.setBackgroundDrawable(new BitmapDrawable(rightBitmap));

		leftAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_left);
		rightAnimation = AnimationUtils.loadAnimation(this,
				R.anim.welcome_right);
		left.startAnimation(leftAnimation);
		right.startAnimation(rightAnimation);
		leftAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,
						ITActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 在欢迎界面屏蔽BACK键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}

	public static Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();

		return bitmap;
	}
}