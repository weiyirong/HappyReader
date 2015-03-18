package com.weiyi.reader.adpater;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import com.weiyi.reader.entity.Category;
import com.weiyi.reader.ui.ITActivity;
import com.weiyi.reader.ui.R;
import com.weiyi.reader.util.DataUtil;
import com.weiyi.reader.util.ImageUtil;
import com.weiyi.reader.util.ViewUtil;
import com.weiyi.reader.view.MyNetImageView;

public class GalleryAdapter extends BaseAdapter {
	List<Category> categorys;
	ITActivity itActivity;
	int width, height;

	public GalleryAdapter(List<Category> categorys, ITActivity itActivity) {
		this.categorys = categorys;
		this.itActivity = itActivity;
		ViewUtil.Screen screen = ViewUtil.getScreenSize(itActivity);
		width = screen.width;
		height = screen.height;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categorys.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return categorys.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		if (view == null) {
			Bitmap originalBitmap = getOriginalBitmapByType(categorys.get(position)
					.getTitle());
			Bitmap resultBitmap = ImageUtil
					.createWithReflectedImage(originalBitmap);
			MyNetImageView imageView = new MyNetImageView(itActivity);
			imageView.setImageBitmap(resultBitmap);
			imageView.setScaleType(MyNetImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(width * 1 / 2,
					height * 5 / 6));
			imageView.setBackgroundResource(R.drawable.bg);
			imageView.setCategory(categorys.get(position));
			view = imageView;
		}
		return view;
	}

	/**
	 * 根据传入的类型获取对应的源Bitmap，作为Gallery的每项背景
	 * 
	 * @param type
	 *            类别
	 * @return
	 * */
	public Bitmap getOriginalBitmapByType(String type) {
		return BitmapFactory.decodeResource(itActivity.getResources(),
				DataUtil.getResIdByType(type));
	}
}