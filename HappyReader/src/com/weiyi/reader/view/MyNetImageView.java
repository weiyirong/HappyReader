package com.weiyi.reader.view;

import com.weiyi.reader.common.ImageDownloadAsyncTask;
import com.weiyi.reader.entity.Category;
import com.weiyi.reader.entity.ITBlog;
import com.weiyi.reader.entity.NicePhoto;
import com.weiyi.reader.ui.R;
import com.weiyi.reader.util.ImageUtil;
import com.weiyi.reader.util.MyNetImageCacheManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 自定义显示网络图片的ImageView
 * 
 * @author 魏艺荣
 * @version 1.0
 * */
public class MyNetImageView extends ImageView {
	private String imgUrl;// 图片地址
	private Category category;
	private NicePhoto photo;
	public boolean isCartoon;// 是否为漫画类别，设置缩略图

	public MyNetImageView(Context context) {
		super(context);
	}

	public MyNetImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyNetImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 设置图片地址
	 * 
	 * @param url
	 *            图片地址
	 * */
	public void setImgUrl(String url, boolean isCartoon) {
		this.imgUrl = url;
		this.isCartoon = isCartoon;
		loadImg(url);
	}

	/**
	 * 为ImageView加载图片
	 * 
	 * @param url
	 * */
	private void loadImg(String url) {
		if (MyNetImageCacheManager.getInstance().isBitmapExistInLocal(url)) {// 是否有缓存
			Log.v("loadImg in MyNetImageView form cache", url);
			Bitmap bitmap = MyNetImageCacheManager.getInstance()
					.getBitmapFromLocal(url);
			if (!isCartoon) {
				this.setImageBitmap(bitmap);
			} else {
				if (bitmap != null) {
					setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0,
							bitmap.getWidth(), bitmap.getHeight() / 3));
				} else {
					Bitmap fail = BitmapFactory.decodeResource(getResources(),
							R.drawable.download_fail_bg);
					setImageBitmap(Bitmap.createBitmap(fail, 0, 0,
							fail.getWidth(), fail.getHeight() / 3));
					if (fail != null && !fail.isRecycled()) {
						fail.recycle();
						fail = null;
					}
				}
			}
		} else {// 没有缓存，则从网络下载
			Log.v("loadImg in MyNetImageView form net", url);
			new ImageDownloadAsyncTask(this).execute(url);
		}
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public NicePhoto getPhoto() {
		return photo;
	}

	public void setPhoto(NicePhoto photo) {
		this.photo = photo;
	}

}
