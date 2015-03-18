package com.weiyi.reader.view;


import com.weiyi.reader.util.ImageUtil;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 自定义BitmapDrawable类,内含Drawable对象变量，以实现能在异步线程下载图片更新Drawable的值
 * 
 * @author 魏艺荣
 * @version 1.0
 * */
public class MyBitmapDrawable extends BitmapDrawable {
	private Drawable drawable;
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(drawable!=null){
			canvas.drawBitmap(ImageUtil.drawableToBitmap(drawable), 2, 0, null);
		}
	}
	
	public Drawable getDrawbale() {
		return drawable;
	}
	public void setDrawbale(Drawable drawable) {
		this.drawable = drawable;
	}
   
}
