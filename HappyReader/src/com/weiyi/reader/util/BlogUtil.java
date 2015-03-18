package com.weiyi.reader.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.weiyi.reader.common.Constant;
import com.weiyi.reader.entity.ITBlog;

import android.util.Log;

/**
 * 功能：Blog获取工具，通过URL解析html获取网络文章各项信息,主要用到html解析工具Jsoup
 * 
 * @author 魏艺荣
 * */
public class BlogUtil {
	/**
	 * 获取文章列表
	 * 
	 * @param url
	 *            请求的ＵＲＬ
	 * @return List<Blog> IT阅读文章列表
	 * */
	public static List<ITBlog> getBlogList(String url) {
		List<ITBlog> Blogs = new ArrayList<ITBlog>();
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements titles = doc.getElementsByClass(
					Constant.ITBLOG_TITLE_CLASS).tagName("a");// 获取所有class=link_title的标签元素
			Elements dates = doc.getElementsByClass(Constant.ITBlOG_DATE_CLASS);
			Elements urls = titles.select(Constant.HREF_SELECT);
			for (int i = 0; i < titles.size(); ++i) {
				String blogUrl = Constant.ITBLOG_URL
						+ urls.get(i).attributes().get("href");// 每篇文章的URL
				String iconUrl = getIconUrlByBlogUrl(blogUrl);
				ITBlog Blog = new ITBlog();
				if (iconUrl != null)
					Blog.setIconUrl(iconUrl);// 设置每篇文章的头图标URL
				Blog.setTilte(titles.get(i).text());// 获取a标签内的文本，即文章标题
				Blog.setDate(dates.get(i).text());// 获取文章发表日期
				Blog.setUrl(blogUrl);// 获取超链接属性href的值
				if (Constant.SEDUCTIVE_CATEGORY_URL.equals(url)) {// 如果是养眼美图
					getImageUrlListByBlogUrl(url);
				}
				Blogs.add(Blog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Blogs;
	}

	/**
	 * 获取文章内容
	 * 
	 * @param url
	 *            请求的ＵＲＬ
	 * @return String IT阅读文章内容(html)
	 * */
	public static Element getContentByURL(String url) {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Element contentElement = doc
					.getElementById(Constant.ITBlOG_CONTENT_ID);
			return contentElement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取文章图标URL,根据文章的URL地址解析img标签获取src属性值
	 * 
	 * @param blogUrl
	 *            请求的文章ＵＲＬ
	 * @return String IT阅读文章图标URL
	 * */
	public static String getIconUrlByBlogUrl(String blogUrl) {
		String iconUrl = null;
		Document doc;
		try {
			doc = Jsoup.connect(blogUrl).get();
			Element contentElement = doc
					.getElementById(Constant.ITBlOG_CONTENT_ID);// 获取内容区
			Elements imgElements = contentElement.getElementsByTag("img");
			if (imgElements.size() > 0)
				iconUrl = imgElements.get(0).attributes().get("src");// 获取UIR,默认取第一个遇到的img的URL
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iconUrl;
	}

	/**
	 * 根据所给的类别URL获取该类别下的第一篇（最新）博文信息 图标地址、简述description、博文地址
	 * */
	public static ITBlog getFirstBlogByCategoryUrl(String url) {
		ITBlog blog = new ITBlog();
		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(10 * 1000).get();
			Elements titleElements = doc.getElementsByClass(
					Constant.ITBLOG_TITLE_CLASS).tagName("a");
			Elements dates = doc.getElementsByClass(Constant.ITBlOG_DATE_CLASS);
			Elements urls = titleElements.select(Constant.HREF_SELECT);
			String blogUrl = Constant.ITBLOG_URL
					+ urls.get(0).attributes().get("href");// 每篇文章的URL
			String iconUrl = getIconUrlByBlogUrl(blogUrl);
			Elements descriptions = doc
					.getElementsByClass(Constant.BLOG_DESCRIPTION);
			String blogDescription = descriptions.get(0).text();

			blog.setBlogDescription(blogDescription);
			blog.setDate(dates.get(0).text());
			blog.setTilte(titleElements.get(0).text());
			blog.setIconUrl(iconUrl);
			blog.setUrl(blogUrl);

		} catch (IOException io) {
			io.printStackTrace();
		}
		return blog;
	}

	/**
	 * 根据所给的文章的URL获取此片文章所有的Image的URL地址，并返回List<String> 此方法一般针对的是"养眼"类别的文章
	 * */
	public static ArrayList<String> getImageUrlListByBlogUrl(String url) {
		ArrayList<String> urls = new ArrayList<String>();
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements elements = doc.getElementById(Constant.ITBlOG_CONTENT_ID)
					.getElementsByTag("img");
			for (int i = 0; i < elements.size(); ++i) {
				String imgUrl = elements.get(i).attributes().get("src");
				urls.add(imgUrl);
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
		return urls;
	}

	/**
	 * 根据所给文章的URL获取此文章中URL地址
	 * */
	public static String getCartoonUrl(String url) {
		Document doc;
		String imgUrl = null;
		try {
			doc = Jsoup.connect(url).timeout(1000 * 10).get();
			Elements elements = doc.getElementById(Constant.ITBlOG_CONTENT_ID)
					.getElementsByTag("a");
			imgUrl = elements.text();
			Log.e("getCartoonUrl()+漫画地址。。。。", imgUrl);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return imgUrl;
	}
}
