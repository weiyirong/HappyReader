package com.weiyi.reader.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.weiyi.reader.common.Constant;
import com.weiyi.reader.entity.ITBlog;

/**
 * 功能：ITBlog获取工具，通过URL解析html获取网络文章各项信息,主要用到html解析工具Jsoup
 * 
 * @author 魏艺荣
 * */
public class ITBlogUtil {
	/**
	 * 获取文章列表
	 * 
	 * @param url
	 *            请求的ＵＲＬ
	 * @return List<ITBlog> IT阅读文章列表
	 * */
	public static List<ITBlog> getITBlogList(String url) {
		List<ITBlog> itBlogs = new ArrayList<ITBlog>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements titles = doc.getElementsByClass(
					Constant.ITBLOG_TITLE_CLASS).tagName("a");// 获取所有class=link_title的标签元素
			Elements dates = doc.getElementsByClass(Constant.ITBlOG_DATE_CLASS);
			Elements urls = titles.select(Constant.HREF_SELECT);
			for (int i = 0; i < titles.size(); ++i) {
				String blogUrl = Constant.ITBLOG_URL
						+ urls.get(i).attributes().get("href");// 每篇文章的URL
				String iconUrl = getIconUrlByBlogUrl(blogUrl);
				ITBlog itBlog = new ITBlog();
				if (iconUrl != null)
					itBlog.setIconUrl(iconUrl);// 设置每篇文章的头图标URL
				itBlog.setTilte(titles.get(i).text());// 获取a标签内的文本，即文章标题
				itBlog.setDate(dates.get(i).text());// 获取文章发表日期
				itBlog.setUrl(blogUrl);// 获取超链接属性href的值
				itBlogs.add(itBlog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itBlogs;
	}

	/**
	 * 获取文章内容
	 * 
	 * @param url
	 *            请求的ＵＲＬ
	 * @return String IT阅读文章内容
	 * */
	public static String getContentByURL(String url) {
		String content = "";
		try {
			Document doc = Jsoup.connect(url).get();
			Element contentElement = doc
					.getElementById(Constant.ITBlOG_CONTENT_ID);
			content = contentElement.html();
		} catch (Exception e) {
			e.printStackTrace();
			return content;
		}
		return content;
	}

	/**
	 * 获取文章图标,根据文章的URL地址解析img标签获取src属性值
	 * 
	 * @param blogUrl
	 *            请求的文章ＵＲＬ
	 * @return String IT阅读文章图标URL
	 * */
	public static String getIconUrlByBlogUrl(String blogUrl) {
		String iconUrl = null;
		try {
			Document doc = Jsoup.connect(blogUrl).get();
			Element contentElement = doc
					.getElementById(Constant.ITBlOG_CONTENT_ID);// 获取内容区
			Elements imgElements = contentElement.getElementsByTag("img");
			if(imgElements.size()>0)
			   iconUrl = imgElements.get(0).attributes().get("src");// 获取UIRL,默认取第一个遇到的img的URL
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iconUrl;
	}
}
