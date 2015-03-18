package com.weiyi.reader.common;

import java.lang.ref.WeakReference;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.weiyi.reader.ui.WebReadActivity;
import com.weiyi.reader.util.BlogUtil;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ContentLoadAsyncTask extends AsyncTask<String, Void, String> {
	Context context;
	WebReadActivity fReadActivity;
	String url;
	String categoryUrl;
	private WeakReference<WebView> fWeakView;

	public ContentLoadAsyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String content = "";
		url = params[0];
		Element element = BlogUtil.getContentByURL(url);
		if (element != null) {
			fReadActivity = (WebReadActivity) context;
			fWeakView = new WeakReference<WebView>(fReadActivity.webView);
			content = element.html();
		}
		return content;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		if(fReadActivity!=null){
			fReadActivity.toolbarBack.setVisibility(ImageView.VISIBLE);
			fReadActivity.toolbarProgress.setVisibility(ProgressBar.GONE);
		}
		if (fWeakView != null) {
			if (fWeakView.get() != null) {
				fWeakView.get().getSettings()
						.setDefaultTextEncodingName("UTF-8");
				fWeakView.get().loadDataWithBaseURL(null, result, "text/html",
						"utf-8", null);

			}
		}
	}
}
