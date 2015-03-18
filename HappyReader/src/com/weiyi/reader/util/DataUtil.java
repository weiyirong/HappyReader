package com.weiyi.reader.util;

import java.util.ArrayList;

import android.util.Log;

import com.weiyi.reader.common.Constant;
import com.weiyi.reader.entity.Category;
import com.weiyi.reader.ui.R;

public class DataUtil {
	private static String[] categoryTitles = {
			Constant.DISGRACE_CATEGORY_TITLE,
			Constant.SEDUCTIVE_CATEGORY_TITLE, Constant.STORY_CATEGORY_TITLE,
			Constant.PEOPLE_CATEGORY_TITLE, Constant.HOT_NEWS_CATEGORY_TITLE,
			Constant.CAR_CATEGORY_TITLE };
	private static String[] categoryURls = { Constant.DISGRACE_CATEGORY_URL,
			Constant.SEDUCTIVE_CATEGORY_URL, Constant.STORY_CATEGORY_URL,
			Constant.PEOPLE_CATEGORY_URL, Constant.HOT_NEWS_CATEGORY_URL,
			Constant.CAR_CATEGORY_URL };
	private static int[] categoryResID = { R.drawable.disgrace,
			R.drawable.seductive, R.drawable.story, R.drawable.people,
			R.drawable.hot_news, R.drawable.car };
	private static String[] categorySummary = {
			Constant.DISGRACE_CATEGORY_SUMMARY,
			Constant.SEDUCTIVE_CATEGORY_SUMMARY,
			Constant.STORY_CATEGORY_SUMMARY, Constant.PEOPLE_CATEGORY_SUMMARY,
			Constant.HOT_NEWS_CATEGORY_SUMMARY, Constant.CAR_CATEGORY_SUMMARY };

	/**
	 * 为主界面Gallery适配器赋值
	 * */
	public static ArrayList<Category> getDataForGallery() {
		ArrayList<Category> categories = new ArrayList<Category>();
		for (int i = 0; i < categoryTitles.length; ++i) {
			Category category = new Category(categoryTitles[i], categoryURls[i],categorySummary[i]);
			categories.add(category);
		}
		return categories;
	}

	/**
	 * 根据类型参数获取指定资源ID
	 * */
	public static int getResIdByType(String type) {
		int i = 0;
		for (i = 0; i < categoryTitles.length; ++i) {
			if (categoryTitles[i].equals(type)) {
				break;
			}
		}
		return categoryResID[i];
	}
}
