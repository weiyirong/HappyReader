package com.weiyi.reader.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpConnection;

import com.weiyi.reader.ui.ITActivity;
import com.weiyi.reader.util.ImageUtil;
import com.weiyi.reader.util.MyNetImageCacheManager;
import com.weiyi.reader.util.StreamUtil;
import com.weiyi.reader.view.GestureImageView;
import com.weiyi.reader.view.MyNetImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * 功能：异步下载图片Bitmap，其中入口参数为图片 URL，出口参数为Bitmap对象 传入参数:图片的ＵＲＬ地址 返回结果：图片的Bitmap格式
 * 
 * @author 魏艺荣
 * @version 1.0
 * */
public class ImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {
	private String picUrl;
	private MyNetImageView myNetImageView;
	private GestureImageView imageView;

	public ImageDownloadAsyncTask(MyNetImageView myNetImageView) {
		this.myNetImageView = myNetImageView;
	}

	public ImageDownloadAsyncTask(GestureImageView imageView) {
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		picUrl = params[0];
		// return ImageUtil.getBitmapByUrl(picUrl);
		if (imageView != null) {
			return ImageUtil.getHttpBitmap(picUrl, imageView);
		} else {
			return ImageUtil.getHttpBitmap(picUrl, myNetImageView);
		}

	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			if (myNetImageView != null) {
				MyNetImageCacheManager.getInstance().putLocal(picUrl, result);
				if (!myNetImageView.isCartoon) {
					myNetImageView.setImageBitmap(result);
				} else {
					myNetImageView.setImageBitmap(Bitmap.createBitmap(result,
							0, 0, result.getWidth(), result.getHeight() / 4));
				}
			}
			if (imageView != null) {
				MyNetImageCacheManager.getInstance().putLocal(picUrl, result);
				imageView.setImageBitmap(result);
				imageView.init();
			}
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
