package com.weiyi.reader.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weiyi.reader.adpater.DownLoadThread;
import com.weiyi.reader.adpater.NiceGalleryAdapter;
import com.weiyi.reader.entity.NicePhoto;
import com.weiyi.reader.util.AppManager;
import com.weiyi.reader.view.MyGallery;

/**
 * 查看图片相册Activity
 * 
 * @author wyr
 * @version 1.1
 * @since 2012.8.7
 * */
public class ReadPhotoActivity extends Activity {
	// 主界面3D
	MyGallery myGallery;
	NiceGalleryAdapter galleryAdapter;
	List<String> urls = new ArrayList<String>();
	// 控制按钮
	ProgressBar progressBar;
	ImageView progressView;
	TextView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_gallery);

		progressBar = (ProgressBar) findViewById(R.id.toolbar_progress);
		progressView = (ImageView) findViewById(R.id.toolbar_back);
		titleView = (TextView) findViewById(R.id.it_category_title);

		NicePhoto photo = (NicePhoto) getIntent().getExtras().getSerializable(
				"photo");
		titleView.setText(photo.getTilte());
		if (photo.getPhotoUrls() != null) {
			urls = photo.getPhotoUrls();
		}
		myGallery = (MyGallery) findViewById(R.id.gallery);
		galleryAdapter = new NiceGalleryAdapter(urls, this, myGallery);
		myGallery.setAdapter(galleryAdapter);
		// 添加Gallery子项点击监听器
		myGallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				new DownLoadThread().start();
				String url = urls.get(position);
				Intent intent = new Intent(ReadPhotoActivity.this,
						ImageZoomActivity.class);
				intent.putExtra("iconUrl", url);
				startActivity(intent);
			}
		});
		progressView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		AppManager.context = this;
	}
}
