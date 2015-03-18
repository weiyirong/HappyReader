package com.weiyi.reader.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.weiyi.reader.common.Constant;
import com.weiyi.reader.ui.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.NetworkInfo.DetailedState;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

/**
 * ���ܣ�ͼƬ��ȡ����������
 * 
 * @author κ����
 * @version 1.0
 * */
public class ImageUtil {
	public static Bitmap getHttpBitmap(String url, View view) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;// ͼƬ��߶�Ϊԭ���Ķ���֮һ����ͼƬΪԭ�����ķ�֮һ
			myFileURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			conn.setConnectTimeout(10000);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(new PatchInputStream(is), null,
					options);
			is.close();
		} catch (FileNotFoundException ffe) {
			return BitmapFactory.decodeResource(view.getResources(),
					R.drawable.file_not_find);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * ����URL��ȡ����ͼƬ����ת����Bitmap
	 * 
	 * @param url
	 *            ͼƬ��Դ��URL
	 * @return Bitmap ���ص�ͼƬ��Դλͼ
	 * */
	public static Bitmap getBitmapByUrl(String url) {
		Log.v("bitmap name", url);
		URL myUrl = null;
		Bitmap myBitmap = null;
		// �ַ���ת����URL
		try {
			myUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// ���������ж�ȡͼƬ��Դ
		try {
			HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
			con.setDoInput(true);// �Ժ�Ϳ���ʹ��conn.getInputStream().read() ;
			con.connect();
			InputStream is = con.getInputStream();
			myBitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myBitmap;
	}

	/**
	 * ��Drawableת����Bitmap
	 * 
	 * @param drawable
	 *            ͼƬ��ԴDrawable
	 * @return Bitmap ���ص�ͼƬ��Դλͼ
	 * */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;// ȡ��drawable��ɫ
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);// �½���Ӧ��Bitmap
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * ���Ŷ�Ӧ��DrawableͼƬ,����Ӧ��Ļ
	 * 
	 * @param drawable
	 *            ͼƬ��ԴDrawable
	 * @param screenWidth
	 *            ��Ļ���
	 * @param screenHeight
	 *            ��Ļ�߶�
	 * @param type
	 *            ���ź����;
	 * @return Drawable �������ź��ͼƬ
	 * */
	public static Drawable zoomDrawable(Drawable drawable, int screenWidth,
			int screenHeight, String type) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		// �������ű���
		float scaleWidth = 0, scaleHeight = 0;
		if (Constant.FOR_BLOG_ICON.equals(type)) {// Ϊ����Ӧ��Ļ��߶�����
			scaleHeight = (float) (screenHeight / 3 + 30) / height;
			scaleWidth = (float) (screenWidth - 50) / width;
		} else if (Constant.FOR_FOOTER_MENU_BG.equals(type)) {
			scaleHeight = (float) (screenHeight) / height;
			scaleWidth = (float) (scaleWidth) / width;
		}
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(newBitmap);// ��Bitmapת����Drawable���
	}

	/**
	 * ���ݴ����Bitmap���󹹽����е�Ӱ��Bitmap
	 * 
	 * @param originalBitmap
	 *            ͼƬλͼ
	 * @return Bitmap ���ش��е�Ӱ��λͼBitmap
	 * */
	public static Bitmap createWithReflectedImage(Bitmap originalBitmap) {
		int reflectionGap = 4;// ͼƬ�뵹Ӱ֮��ľ�����
		int width = originalBitmap.getWidth();
		int height = originalBitmap.getHeight();
		// �任�����Matrix,��� ͼƬ��ת�����ŵȿ���
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		// ��ȡ��ӰBitmap
		Bitmap reflectionBitmap = Bitmap.createBitmap(originalBitmap, 0,
				height / 2, width, height / 2, matrix, false);
		// ��ȡ����Ӱ��Bitmap.�������Ч��ͼλͼ����
		Bitmap withReflectionBitmap = Bitmap.createBitmap(width, height
				+ height / 2, Config.ARGB_8888);
		/** Bitmap����ʾ����Ҫ����Canvas����� */
		// �ɸ�λͼ���󴴽���ʼ����(�涨�˻����Ŀ��)
		Canvas canvas = new Canvas(withReflectionBitmap);
		canvas.drawBitmap(originalBitmap, 0, 0, null);
		// ���Ƴ�ԭͼ�뵹Ӱ֮��ļ�����þ��������
		Paint paint1 = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, paint1);
		// ���Ƴ���Ӱ��Bitmap
		canvas.drawBitmap(reflectionBitmap, 0, height + reflectionGap, paint1);
		// �������Խ������
		Paint paint2 = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalBitmap.getHeight(), 0, withReflectionBitmap.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		// �ѽ���Ч��Ӧ���ڻ�����
		paint2.setShader(shader);
		// ���õ�Ӱ����Ӱ�ȣ�ʹ����ԭ����ͼ����ɫ���������˴���ʾ�Ҷȣ��ᱻȾ������ĵײ���ԭͼƬ�ĵ�Ӱ��ɫ��ʵ�ֵ�Ӱ������
		paint2.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// �����úõ�paint2���ƴ˵�Ӱ
		canvas.drawRect(0, height, width, withReflectionBitmap.getHeight()
				+ reflectionGap, paint2);
		return withReflectionBitmap;
	}

	public static Bitmap getBitMap(Context c, String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			bitmap = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.download_fail_bg);
			return bitmap;
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			int length = (int) conn.getContentLength();
			if (length != -1) {
				byte[] imgData = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, imgData, destPos, readLen);
					destPos += readLen;
				}
				bitmap = BitmapFactory.decodeByteArray(imgData, 0,
						imgData.length);
			}
		} catch (IOException e) {
			bitmap = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.download_fail_bg);
			return bitmap;
		}
		return bitmap;
	}

}
