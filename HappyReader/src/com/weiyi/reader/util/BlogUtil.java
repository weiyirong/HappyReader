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
 * ���ܣ�Blog��ȡ���ߣ�ͨ��URL����html��ȡ�������¸�����Ϣ,��Ҫ�õ�html��������Jsoup
 * 
 * @author κ����
 * */
public class BlogUtil {
	/**
	 * ��ȡ�����б�
	 * 
	 * @param url
	 *            ����ģգң�
	 * @return List<Blog> IT�Ķ������б�
	 * */
	public static List<ITBlog> getBlogList(String url) {
		List<ITBlog> Blogs = new ArrayList<ITBlog>();
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements titles = doc.getElementsByClass(
					Constant.ITBLOG_TITLE_CLASS).tagName("a");// ��ȡ����class=link_title�ı�ǩԪ��
			Elements dates = doc.getElementsByClass(Constant.ITBlOG_DATE_CLASS);
			Elements urls = titles.select(Constant.HREF_SELECT);
			for (int i = 0; i < titles.size(); ++i) {
				String blogUrl = Constant.ITBLOG_URL
						+ urls.get(i).attributes().get("href");// ÿƪ���µ�URL
				String iconUrl = getIconUrlByBlogUrl(blogUrl);
				ITBlog Blog = new ITBlog();
				if (iconUrl != null)
					Blog.setIconUrl(iconUrl);// ����ÿƪ���µ�ͷͼ��URL
				Blog.setTilte(titles.get(i).text());// ��ȡa��ǩ�ڵ��ı��������±���
				Blog.setDate(dates.get(i).text());// ��ȡ���·�������
				Blog.setUrl(blogUrl);// ��ȡ����������href��ֵ
				if (Constant.SEDUCTIVE_CATEGORY_URL.equals(url)) {// �����������ͼ
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
	 * ��ȡ��������
	 * 
	 * @param url
	 *            ����ģգң�
	 * @return String IT�Ķ���������(html)
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
	 * ��ȡ����ͼ��URL,�������µ�URL��ַ����img��ǩ��ȡsrc����ֵ
	 * 
	 * @param blogUrl
	 *            ��������£գң�
	 * @return String IT�Ķ�����ͼ��URL
	 * */
	public static String getIconUrlByBlogUrl(String blogUrl) {
		String iconUrl = null;
		Document doc;
		try {
			doc = Jsoup.connect(blogUrl).get();
			Element contentElement = doc
					.getElementById(Constant.ITBlOG_CONTENT_ID);// ��ȡ������
			Elements imgElements = contentElement.getElementsByTag("img");
			if (imgElements.size() > 0)
				iconUrl = imgElements.get(0).attributes().get("src");// ��ȡUIR,Ĭ��ȡ��һ��������img��URL
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iconUrl;
	}

	/**
	 * �������������URL��ȡ������µĵ�һƪ�����£�������Ϣ ͼ���ַ������description�����ĵ�ַ
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
					+ urls.get(0).attributes().get("href");// ÿƪ���µ�URL
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
	 * �������������µ�URL��ȡ��Ƭ�������е�Image��URL��ַ��������List<String> �˷���һ����Ե���"����"��������
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
	 * �����������µ�URL��ȡ��������URL��ַ
	 * */
	public static String getCartoonUrl(String url) {
		Document doc;
		String imgUrl = null;
		try {
			doc = Jsoup.connect(url).timeout(1000 * 10).get();
			Elements elements = doc.getElementById(Constant.ITBlOG_CONTENT_ID)
					.getElementsByTag("a");
			imgUrl = elements.text();
			Log.e("getCartoonUrl()+������ַ��������", imgUrl);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return imgUrl;
	}
}
