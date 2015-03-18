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
 * 网络图片缓存管理
 * 
 * @author 魏艺荣
 * @version 1.0
 * */
public class MyNetImageCacheManager extends WeakHashMap<String, Bitmap> {
	private static final String CACHE_FILE = "/Reader/Cache";// 缓存文件夹
	private static MyNetImageCacheManager myNetImgCache = new MyNetImageCacheManager();

	public static MyNetImageCacheManager getInstance() {
		return myNetImgCache;
	}

	/**
	 * 判断图片是否存在：先判断缓存里是否由此图片，再判断本地缓存文件里是否由此图片
	 * 
	 * @param url
	 * @return
	 * */
	public boolean isBitmapExist(String url) {
		boolean isExist = containsKey(url);
		if (!isExist) {// 如果内存中不存在此图片，则判断本地文件缓存是否有此图片
			isExist = isBitmapExistLocal(url);
		}
		return isExist;
	}

	/**
	 * 判断本地缓存文件中是否存在此图片资源
	 * 
	 * @param url
	 * @return
	 * */
	private boolean isBitmapExistLocal(String url) {
		boolean isExistLocal = true;
		String fileName = UrlToFileName(url);// 因为文件名命名中不准出现非法的一些字符（URL存在这种字符）
		String path = isExistCachePath();// 是否存在缓存目录
		File file = new File(path, fileName);
		if (!file.exists()) {
			isExistLocal = false;
		} else {// 如果存在的话，把此资源缓存到内存
			isExistLocal = cacheBitmapToMemory(file, url);
		}
		return isExistLocal;
	}

	/**
	 * 将Bitmap图片缓存到到内存中,只是缓存到内存
	 * 
	 * @param file
	 *            到缓存到内存的文件资源
	 * @param url
	 *            此文件资源的URL地址
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
			this.put(url, bitmap, false);// ,只是缓存到内存
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 缓存图片
	 * 
	 * @param key
	 * @param bitmap
	 * @param isCacheToLocal
	 *            是否要缓存到本地
	 * */
	public Bitmap put(String key, Bitmap bitmap, boolean isCacheToLocal) {
		if (isCacheToLocal) {// 要缓存到本地，则调用子类的重写方法put缓存
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
		value.compress(CompressFormat.JPEG, 100, os);// 压缩流文件

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
	 * 是否存在本地缓存目录，有则返回此目录，无则新建此目录并返回
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
	 * URL转换成文件名
	 * 
	 * @param url
	 *            待转换URL
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
	 * 只缓存到本地
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
		value.compress(CompressFormat.JPEG, 100, os);// 压缩流文件

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
	 * 判断本地缓存文件夹中是否存在此图片资源
	 * 
	 * @param url
	 * @return
	 * */
	public boolean isBitmapExistInLocal(String url) {
		if(url==null){
			return false;
		}
		String fileName = UrlToFileName(url);// 因为文件名命名中不准出现非法的一些字符（URL存在这种字符）
		String path = isExistCachePath();// 是否存在缓存目录
		File file = new File(path, fileName);
		if (!file.exists()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 读取本地缓存文件
	 * 
	 * @param url
	 * @return
	 * */
	public Bitmap getBitmapFromLocal(String url) {
		FileInputStream fis = null;
		String fileName = UrlToFileName(url);// 因为文件名命名中不准出现非法的一些字符（URL存在这种字符）
		String path = isExistCachePath();// 是否存在缓存目录
		File file = new File(path, fileName);
		if (!file.exists()) {
			return null;
		} else {// 读取本地缓存的Bitmap资源
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
