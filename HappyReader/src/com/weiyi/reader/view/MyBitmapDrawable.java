package com.weiyi.reader.view;


import com.weiyi.reader.util.ImageUtil;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * �Զ���BitmapDrawable��,�ں�Drawable�����������ʵ�������첽�߳�����ͼƬ����Drawable��ֵ
 * 
 * @author κ����
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
