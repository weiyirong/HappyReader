package com.weiyi.reader.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.View;

public class Utils {
	public static  Bitmap getBitmap(Activity activity) {
		Bitmap bitmap;
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		bitmap = view.getDrawingCache();
//		Rect frame = new Rect();
//		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//		int toHeight = frame.top;
//		bitmap = Bitmap.createBitmap(bitmap, 0, 0 + 2 * toHeight, view.getWidth(), view.getHeight());
//		try {
//			FileOutputStream fout = new FileOutputStream("mnt/sdcard/test.png");
//			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch blockt
//			e.printStackTrace();
//		}
//		view.setDrawingCacheEnabled(false);
		return bitmap;
	}

}
