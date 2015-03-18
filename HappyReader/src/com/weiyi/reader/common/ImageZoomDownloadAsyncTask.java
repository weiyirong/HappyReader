package com.weiyi.reader.common;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.view.View;

import com.weiyi.reader.ui.ImageZoomActivity;
import com.weiyi.reader.util.ImageUtil;
import com.weiyi.reader.util.MyNetImageCacheManager;
import com.weiyi.reader.view.ImageZoomView;

public class ImageZoomDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {
	private final WeakReference<ImageZoomView>weakReference;
	private String url;
	private ImageZoomActivity activity;

	public ImageZoomDownloadAsyncTask(Context context,ImageZoomView myNetImageView) {
		weakReference = new WeakReference<ImageZoomView>(myNetImageView);
		this.activity = (ImageZoomActivity) context;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		url = params[0];
		Bitmap bitmap = ImageUtil.getBitmapByUrl(params[0]);
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		if(isCancelled()){
			result = null;
		}
		if(weakReference!=null){
			ImageZoomView myNetImageView = weakReference.get();
			if(myNetImageView!=null){
				MyNetImageCacheManager.getInstance().putLocal(url, result);
				myNetImageView.setImage(result);
				activity.mBitmap = result;
				activity.progressBar.setVisibility(View.GONE);
				activity.mZoomState = new ZoomState();
				activity.mZoomView.setZoomState(activity.mZoomState);
				activity.mZoomListener = new SimpleZoomListener();
				activity.mZoomListener.setZoomState(activity.mZoomState);
				activity.mZoomView.setOnTouchListener(activity.mZoomListener);
				activity.resetZoomState();
			}
		}
		super.onPostExecute(result);

	}

}
