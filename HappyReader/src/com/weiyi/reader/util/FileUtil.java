package com.weiyi.reader.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.weiyi.reader.ui.R;

public class FileUtil {
	public static final String CACHE_FILE = "/Reader/Cache";// 缓存文件夹
	public static final String LOCAL_FILE = "/Reader/download";// 下载文件保存的文件夹

	/**
	 * 保存字符串到文件
	 * */
	public static String saveString2Local(String filePath, String content) {
		File file = new File(filePath);
		FileWriter fw = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file);
			fw.write(content);
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return file.getAbsolutePath();
	}

	/**
	 * 根据文件绝对路径取得缓存文件
	 * */
	public static String getStringByPath(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		FileReader fr = null;
		BufferedReader br = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			while (br.ready()) {
				stringBuilder.append(br.readLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * URL转换成文件名
	 * 
	 * @param url
	 *            待转换URL
	 * @return String
	 * */
	public static String urlToFileName(String url) {
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
	 * 是否存在本地缓存目录，有则返回此目录，无则新建此目录并返回
	 * 
	 * @return String
	 * */
	public static String isExistCachePath() {
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
		if (!isAvaiableSpace(10)) {
			return null;
		}
		return cachePath;
	}

	/**
	 * 判断sdcard的容量
	 * */
	public static boolean isAvaiableSpace(int sizeMb) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String sdcard = Environment.getExternalStorageDirectory().getPath();
			StatFs statFs = new StatFs(sdcard);
			long blockSize = statFs.getBlockSize();
			long block = statFs.getAvailableBlocks();
			long availableSpace = (block * blockSize) / (1024 * 1024);
			System.out.println("sdcard size:" + availableSpace);
			if (sizeMb > availableSpace) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证URL是否为本地资源图库地址
	 * */
	public static boolean validUrl(String url) {
		// TODO Auto-generated method stub
		String patternStr = "^\\d+$";
		Pattern pattern = Pattern.compile(patternStr);
		return pattern.matcher(url).matches();
	}

	/**
	 * 保存图片到文件夹
	 * */
	public static void saveBitmap(Bitmap bitmap, Context context,
			String bitmapUrl) {
		String rootPath = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			rootPath = Environment.getExternalStorageDirectory().toString();
		} else {
			// Toast.makeText(context,
			// "很抱歉，您无sdcard，无法完成下载!",Toast.LENGTH_SHORT).show();
			return;
		}
		FileOutputStream os = null;
		File file = new File(rootPath + LOCAL_FILE);
		if (!file.exists()) {
			// Toast.makeText(context, "目录不存在",Toast.LENGTH_SHORT).show();
			file.mkdirs();
		}
		try {
			os = new FileOutputStream(file + File.separator
					+ urlToFileName(bitmapUrl));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(CompressFormat.JPEG, 100, os);// 压缩流文件

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
	}

	/**
	 * 获取下载文件目录区的所有文件目录
	 * */
	public static File[] getDownloadFiles() {
		File[] files = new File[] {};
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return files;
		}
		File localDir = new File(Environment.getExternalStorageDirectory()
				.toString() + LOCAL_FILE);
		if (localDir.exists()) {
			files = localDir.listFiles();
		}
		return files;
	}

	/**
	 * 删除缓存
	 * */
	public static void delCache() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File cacheDir = new File(Environment.getExternalStorageDirectory()
					.toString() + CACHE_FILE);
			if (cacheDir.exists()) {
				deleteFile(cacheDir);
			}
		}
	}

	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		}
	}
}
