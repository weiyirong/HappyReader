package com.weiyi.reader.ui;

import com.weiyi.reader.common.Constant;
import com.weiyi.reader.util.AppManager;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setDefaultTextEncodingName("gbk");
//		webView.getSettings().set
		webView.loadUrl("file:///android_asset/about.htm");
		AppManager.context = this;
	}
}