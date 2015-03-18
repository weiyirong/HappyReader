package com.weiyi.reader.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.WeakHashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * ����ͼƬ�������
 * 
 * @author κ����
 * @version 1.0
 * */
public class MyNetImageCacheManager extends WeakHashMap<String, Bitmap> {
	private static final String CACHE_FILE = "/Reader/Cache";// �����ļ���
	private static MyNetImageCacheManager myNetImgCache = new MyNetImageCacheManager();

	public static MyNetImageCacheManager getInstance() {
		return myNetImgCache;
	}

	/**
	 * �ж�ͼƬ�Ƿ���ڣ����жϻ������Ƿ��ɴ�ͼƬ�����жϱ��ػ����ļ����Ƿ��ɴ�ͼƬ
	 * 
	 * @param url
	 * @return
	 * */
	public boolean isBitmapExist(String url) {
		boolean isExist = containsKey(url);
		if (!isExist) {// ����ڴ��в����ڴ�ͼƬ�����жϱ����ļ������Ƿ��д�ͼƬ
			isExist = isBitmapExistLocal(url);
		}
		return isExist;
	}

	/**
	 * �жϱ��ػ����ļ����Ƿ���ڴ�ͼƬ��Դ
	 * 
	 * @param url
	 * @return
	 * */
	private boolean isBitmapExistLocal(String url) {
		boolean isExistLocal = true;
		String fileName = UrlToFileName(url);// ��Ϊ�ļ��������в�׼���ַǷ���һЩ�ַ���URL���������ַ���
		String path = isExistCachePath();// �Ƿ���ڻ���Ŀ¼
		File file = new File(path, fileName);
		if (!file.exists()) {
			isExistLocal = false;
		} else {// ������ڵĻ����Ѵ���Դ���浽�ڴ�
			isExistLocal = cacheBitmapToMemory(file, url);
		}
		return isExistLocal;
	}

	/**
	 * ��BitmapͼƬ���浽���ڴ���,ֻ�ǻ��浽�ڴ�
	 * 
	 * @param file
	 *            �����浽�ڴ���ļ���Դ
	 * @param url
	 *            ���ļ���Դ��URL��ַ
	 * @return
	 * */
	private boolean cacheBitmapToMemory(File file, String url) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		byte[] b = StreamUtil.getByteByStream(is);
		Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
		if (bitmap != null) {
			this.put(url, bitmap, false);// ,ֻ�ǻ��浽�ڴ�
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ����ͼƬ
	 * 
	 * @param key
	 * @param bitmap
	 * @param isCacheToLocal
	 *            �Ƿ�Ҫ���浽����
	 * */
	public Bitmap put(String key, Bitmap bitmap, boolean isCacheToLocal) {
		if (isCacheToLocal) {// Ҫ���浽���أ�������������д����put����
			return this.put(key, bitmap);
		} else {
			return super.put(key, bitmap);
		}
	}

	@Override
	public Bitmap put(String key, Bitmap value) {
		OutputStream os = null;
		String fileName = UrlToFileName(key);
		String path = isExistCachePath();
		File file = new File(path, fileName);
		try {
			os = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		value.compress(CompressFormat.JPEG, 100, os);// ѹ�����ļ�

		try {
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		os = null;
		return super.put(key, value);
	}

	/**
	 * �Ƿ���ڱ��ػ���Ŀ¼�����򷵻ش�Ŀ¼�������½���Ŀ¼������
	 * 
	 * @return String
	 * */
	public String isExistCachePath() {
		String cachePath, rootPath = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			rootPath = Environment.getExternalStorageDirectory().toString();
		} else {
			return null;
		}
		cachePath = rootPath + CACHE_FILE;
		File file = new File(cachePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		Log.v("cachePath", cachePath);
		return cachePath;
	}

	/**
	 * URLת�����ļ���
	 * 
	 * @param url
	 *            ��ת��URL
	 * @return String
	 * */
	private String UrlToFileName(String url) {
		String replacement = "_";
		String fileName = url.replace("//", replacement);
		fileName = fileName.replace("/", replacement);
		fileName = fileName.replace(":", replacement);
		fileName = fileName.replace("=", replacement);
		fileName = fileName.replace("&", replacement);
		fileName = fileName.replace("?", replacement);
		return fileName;
	}

	/**
	 * ֻ���浽����
	 * */
	public void putLocal(String key, Bitmap value) {
		OutputStream os = null;
		String fileName = UrlToFileName(key);
		String path = isExistCachePath();
		File file = new File(path, fileName);
		Log.e("putLocal..........",file.getAbsolutePath()+"::"+file.exists());
		try {
			os = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		value.compress(CompressFormat.JPEG, 100, os);// ѹ�����ļ�

		try {
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �жϱ��ػ����ļ������Ƿ���ڴ�ͼƬ��Դ
	 * 
	 * @param url
	 * @return
	 * */
	public boolean isBitmapExistInLocal(String url) {
		if(url==null){
			return false;
		}
		String fileName = UrlToFileName(url);// ��Ϊ�ļ��������в�׼���ַǷ���һЩ�ַ���URL���������ַ���
		String path = isExistCachePath();// �Ƿ���ڻ���Ŀ¼
		File file = new File(path, fileName);
		if (!file.exists()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ��ȡ���ػ����ļ�
	 * 
	 * @param url
	 * @return
	 * */
	public Bitmap getBitmapFromLocal(String url) {
		FileInputStream fis = null;
		String fileName = UrlToFileName(url);// ��Ϊ�ļ��������в�׼���ַǷ���һЩ�ַ���URL���������ַ���
		String path = isExistCachePath();// �Ƿ���ڻ���Ŀ¼
		File file = new File(path, fileName);
		if (!file.exists()) {
			return null;
		} else {// ��ȡ���ػ����Bitmap��Դ
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return BitmapFactory.decodeStream(fis);
		}
	}
}
