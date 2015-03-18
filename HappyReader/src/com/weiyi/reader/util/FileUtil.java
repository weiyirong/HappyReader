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
	public static final String CACHE_FILE = "/Reader/Cache";// �����ļ���
	public static final String LOCAL_FILE = "/Reader/download";// �����ļ�������ļ���

	/**
	 * �����ַ������ļ�
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
	 * �����ļ�����·��ȡ�û����ļ�
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
	 * URLת�����ļ���
	 * 
	 * @param url
	 *            ��ת��URL
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
	 * �Ƿ���ڱ��ػ���Ŀ¼�����򷵻ش�Ŀ¼�������½���Ŀ¼������
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
	 * �ж�sdcard������
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
	 * ��֤URL�Ƿ�Ϊ������Դͼ���ַ
	 * */
	public static boolean validUrl(String url) {
		// TODO Auto-generated method stub
		String patternStr = "^\\d+$";
		Pattern pattern = Pattern.compile(patternStr);
		return pattern.matcher(url).matches();
	}

	/**
	 * ����ͼƬ���ļ���
	 * */
	public static void saveBitmap(Bitmap bitmap, Context context,
			String bitmapUrl) {
		String rootPath = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			rootPath = Environment.getExternalStorageDirectory().toString();
		} else {
			// Toast.makeText(context,
			// "�ܱ�Ǹ������sdcard���޷��������!",Toast.LENGTH_SHORT).show();
			return;
		}
		FileOutputStream os = null;
		File file = new File(rootPath + LOCAL_FILE);
		if (!file.exists()) {
			// Toast.makeText(context, "Ŀ¼������",Toast.LENGTH_SHORT).show();
			file.mkdirs();
		}
		try {
			os = new FileOutputStream(file + File.separator
					+ urlToFileName(bitmapUrl));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(CompressFormat.JPEG, 100, os);// ѹ�����ļ�

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
	 * ��ȡ�����ļ�Ŀ¼���������ļ�Ŀ¼
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
	 * ɾ������
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
		if (file.exists()) { // �ж��ļ��Ƿ����
			if (file.isFile()) { // �ж��Ƿ����ļ�
				file.delete(); // delete()����
			} else if (file.isDirectory()) { // �����������һ��Ŀ¼
				File files[] = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
				for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
					deleteFile(files[i]); // ��ÿ���ļ� ������������е���
				}
			}
			file.delete();
		}
	}
}
