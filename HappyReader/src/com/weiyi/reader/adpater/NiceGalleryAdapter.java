package com.weiyi.reader.adpater;

import java.util.List;
import java.util.regex.Pattern;

import com.weiyi.reader.ui.R;
import com.weiyi.reader.util.FileUtil;
import com.weiyi.reader.view.MyGallery;
import com.weiyi.reader.view.MyNetImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.Toast;

public class NiceGalleryAdapter extends BaseAdapter {
	List<String> urls;
	Context context;
	MyGallery myGallery;

	public NiceGalleryAdapter(List<String> urls, Context context,
			MyGallery myGallery) {
		super();
		this.urls = urls;
		this.context = context;
		this.myGallery = myGallery;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return urls.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return urls.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyNetImageView imageView = null;
		String url = urls.get(position);
		if (convertView == null) {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.loadimg_bg);
			imageView = new MyNetImageView(context);
			imageView.setImageDrawable(new BitmapDrawable(bitmap));
			// 获取手机屏幕大小
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			imageView.setScaleType(MyNetImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(
					dm.widthPixels * 2 / 3, dm.heightPixels * 3 / 4));
			if (FileUtil.validUrl(url)) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;
				Bitmap bm = BitmapFactory.decodeResource(
						context.getResources(), Integer.valueOf(url), options);
				imageView.setImageBitmap(bm);
			} else {
				imageView.setImgUrl(urls.get(position),false);
			}
			convertView = imageView;
			convertView.setTag(imageView);
		} else {
			imageView = (MyNetImageView) convertView.getTag();
		}
		return imageView;
	}

}
