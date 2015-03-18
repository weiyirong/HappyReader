package com.weiyi.reader.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.weiyi.reader.common.ContentLoadAsyncTask;
import com.weiyi.reader.entity.ITBlog;
import com.weiyi.reader.util.AppManager;

/**
 * 以WebView方式阅读，文章图片数量不限，均可显示
 * 
 * @author wyr
 * @version 1.1
 * @since 2012.8.8
 * */
public class WebReadActivity extends Activity {
	public WebView webView;
	TextView title;
	TextView date;
	String blogUrl;
	private final static int LOADING = 0;
	private final static float MAX_X = 5.0f;
	public ITBlog itBlog;// 当前文章
	private int index;// 当前文章索引
	public int screenWidth, screenHeight;// 屏高和屏宽
	public ProgressBar toolbarProgress;
	public ImageView toolbarBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.web_scale_in, R.anim.web_scale_out);
		setContentView(R.layout.scrollview_layout);
		AppManager.context = this;
		// 获取数据
		Bundle bundle = getIntent().getExtras();
		itBlog = (ITBlog) bundle.getSerializable("ITBlog");
		// 获取屏高和屏宽screenWidth,screenHeight
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		screenHeight = displayMetrics.heightPixels;
		screenWidth = displayMetrics.widthPixels;
		toolbarBack = (ImageView) findViewById(R.id.toolbar_back);
		toolbarProgress = (ProgressBar) findViewById(R.id.toolbar_progress);
		title = (TextView) findViewById(R.id.title);
		date = (TextView) findViewById(R.id.date);
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setDefaultTextEncodingName("UTF-8");
		webView.getSettings().setTextSize(TextSize.LARGER);
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				webView.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				dismissDialog(LOADING);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				showDialog(LOADING);
			}

		});
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}

		});
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// 为返回ImageView添加监听
		toolbarBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WebReadActivity.this.finish();
			}
		});
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		title.setText(itBlog.getTilte());
		date.setText(itBlog.getDate());
		blogUrl = itBlog.getUrl();
		new ContentLoadAsyncTask(this).execute(itBlog.getUrl());

		// 设置工具条（toolBar状态）
		toolbarBack.setVisibility(ImageView.GONE);
		toolbarProgress.setVisibility(ProgressBar.VISIBLE);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == LOADING) {
			Dialog dialog = new Dialog(this, R.style.dialog);
			dialog.setContentView(R.layout.dialogview);
			ImageView imgView = (ImageView) dialog
					.findViewById(R.id.load_imgview);
			Animation animation = AnimationUtils.loadAnimation(this,
					R.anim.progress_animation);
			imgView.startAnimation(animation);
			return dialog;
		}
		return super.onCreateDialog(id);
	}
}
