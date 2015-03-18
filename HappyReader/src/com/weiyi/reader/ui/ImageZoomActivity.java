package com.weiyi.reader.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.weiyi.reader.common.ImageZoomDownloadAsyncTask;
import com.weiyi.reader.common.SimpleZoomListener;
import com.weiyi.reader.common.ZoomState;
import com.weiyi.reader.util.AppManager;
import com.weiyi.reader.util.FileUtil;
import com.weiyi.reader.view.ImageZoomView;

/**
 * 查看图片（可放大、缩放等）
 * 
 * @author wyr
 * */
public class ImageZoomActivity extends Activity {

	public ImageZoomView mZoomView;
	public ZoomState mZoomState;
	public Bitmap mBitmap;
	public SimpleZoomListener mZoomListener;
	public ProgressBar progressBar;
	public ImageButton download;
	public ImageButton close;
	public String bitmapUrl;
	String filePath;
	// 下载服务
	MyService myService;

	// MyBinder myBinder;
	// ServiceConnection serviceConnection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_zoom);
		mZoomView = (ImageZoomView) findViewById(R.id.zoomView);
		progressBar = (ProgressBar) findViewById(R.id.progress);
		download = (ImageButton) findViewById(R.id.download);
		close = (ImageButton) findViewById(R.id.close);
		progressBar.setVisibility(View.VISIBLE);
		// 开始加载图片
		bitmapUrl = getIntent().getStringExtra("iconUrl");
		filePath = getIntent().getStringExtra("file");
		String command = getIntent().getStringExtra("COMMAND");
		if ("NOT_DOWNLOAD".equals(command)) {
			download.setVisibility(ImageButton.GONE);
		}
		if (filePath != null) {
			BitmapFactory.Options options = new Options();
			options.inSampleSize = 2;
			mBitmap = BitmapFactory.decodeFile(filePath,options);
			mZoomView.setImage(mBitmap);
			progressBar.setVisibility(View.GONE);
			mZoomState = new ZoomState();
			mZoomView.setZoomState(mZoomState);
			mZoomListener = new SimpleZoomListener();
			mZoomListener.setZoomState(mZoomState);
			mZoomView.setOnTouchListener(mZoomListener);
			resetZoomState();
		} else {
			if (FileUtil.validUrl(bitmapUrl)) {
				BitmapFactory.Options options = new Options();
				options.inSampleSize = 2;
				mBitmap = BitmapFactory.decodeResource(getResources(),
						Integer.valueOf(bitmapUrl), options);
				mZoomView.setImage(mBitmap);
				progressBar.setVisibility(View.GONE);
				mZoomState = new ZoomState();
				mZoomView.setZoomState(mZoomState);
				mZoomListener = new SimpleZoomListener();
				mZoomListener.setZoomState(mZoomState);
				mZoomView.setOnTouchListener(mZoomListener);
				resetZoomState();
			} else {
				new ImageZoomDownloadAsyncTask(this, mZoomView)
						.execute(bitmapUrl);
			}
		}
		ZoomControls zoomCtrl = (ZoomControls) findViewById(R.id.zoomCtrl);
		zoomCtrl.setOnZoomInClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mZoomState == null) {
					Toast.makeText(ImageZoomActivity.this, R.string.zoom_info,
							Toast.LENGTH_SHORT).show();
					return;
				}
				float z = mZoomState.getZoom() + 0.25f;
				mZoomState.setZoom(z);
				mZoomState.notifyObservers();
			}
		});
		zoomCtrl.setOnZoomOutClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mZoomState == null) {
					Toast.makeText(ImageZoomActivity.this, R.string.zoom_info,
							Toast.LENGTH_SHORT).show();
					return;
				}
				float z = mZoomState.getZoom() - 0.25f;
				mZoomState.setZoom(z);
				mZoomState.notifyObservers();
			}
		});
		download.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Toast.makeText(ImageZoomActivity.this, "开始下载...",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(ImageZoomActivity.this, MyService.class);
				intent.putExtra("bitmapUrl", bitmapUrl);
				startService(intent);
				return false;
			}
		});
		close.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				finish();
				return false;
			}
		});
		// serviceConnection = new ServiceConnection() {
		//
		// @Override
		// public void onServiceDisconnected(ComponentName name) {
		// // TODO Auto-generated method stub
		// myService = null;
		// myBinder = null;
		// }
		//
		// @Override
		// public void onServiceConnected(ComponentName name, IBinder service) {
		// // TODO Auto-generated method stub
		// if (name.getShortClassName().endsWith("MyService")) {
		// try {
		// myBinder = (MyBinder) service;
		// myService = myBinder.getService();
		// } catch (Exception e) {
		//
		// }
		// }
		// }
		// };
		AppManager.context = this;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mBitmap != null)
			mBitmap.recycle();
		// mZoomView.setOnTouchListener(null);
		// mZoomState.deleteObservers();
	}

	public void resetZoomState() {
		mZoomState.setPanX(0.5f);
		mZoomState.setPanY(0.5f);
		mZoomState.setZoom(1f);
		mZoomState.notifyObservers();
	}
}