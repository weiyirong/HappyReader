package com.weiyi.reader.ui;

import com.weiyi.reader.anim.ExitAnimation;
import com.weiyi.reader.view.PreferencesHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class BaseActivity extends Activity {
	public PreferencesHelper pre;
	SharedPreferences sp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pre = new PreferencesHelper(this, "ty");
	}
	@Override
	public void onBackPressed() {
		Dialog dialog = new AlertDialog.Builder(BaseActivity.this)
				.setTitle("提示").setMessage("确定退出吗?")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						sp = BaseActivity.this.getSharedPreferences("data", MODE_WORLD_READABLE);
						Editor editor = sp.edit();
						editor.putBoolean("Clicked", false);
						editor.commit();
						Intent startMain = new Intent(Intent.ACTION_MAIN);
						startMain.addCategory(Intent.CATEGORY_HOME);
						startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(startMain);
						
						Animation exitAnimation = new ExitAnimation();
						ImageView im = new ImageView(BaseActivity.this);
						// Bitmap bmp = Bitmap.createBitmap(320, 480, Config.ARGB_8888);
						Bitmap bmp = BitmapFactory.decodeResource(getResources(),
								R.drawable.default_novel_cover);
						View cv = getWindow().getDecorView();
						cv.setDrawingCacheEnabled(true);
						im.setScaleType(ScaleType.FIT_XY);
						im.setImageBitmap(bmp);
						setContentView(im);
						im.startAnimation(exitAnimation);
						finish();
						android.os.Process.killProcess(android.os.Process.myPid());
						System.exit(0);// 退出程序
					}

				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				}).create();
		dialog.show();
	}

}
