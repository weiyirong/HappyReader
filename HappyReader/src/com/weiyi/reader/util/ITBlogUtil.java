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
 * ���ܣ�ITBlog��ȡ���ߣ�ͨ��URL����html��ȡ�������¸�����Ϣ,��Ҫ�õ�html��������Jsoup
 * 
 * @author κ����
 * */
public class ITBlogUtil {
	/**
	 * ��ȡ�����б�
	 * 
	 * @param url
	 *            ����ģգң�
	 * @return List<ITBlog> IT�Ķ������б�
	 * */
	public static List<ITBlog> getITBlogList(String url) {
		List<ITBlog> itBlogs = new ArrayList<ITBlog>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements titles = doc.getElementsByClass(
					Constant.ITBLOG_TITLE_CLASS).tagName("a");// ��ȡ����class=link_title�ı�ǩԪ��
			Elements dates = doc.getElementsByClass(Constant.ITBlOG_DATE_CLASS);
			Elements urls = titles.select(Constant.HREF_SELECT);
			for (int i = 0; i < titles.size(); ++i) {
				String blogUrl = Constant.ITBLOG_URL
						+ urls.get(i).attributes().get("href");// ÿƪ���µ�URL
				String iconUrl = getIconUrlByBlogUrl(blogUrl);
				ITBlog itBlog = new ITBlog();
				if (iconUrl != null)
					itBlog.setIconUrl(iconUrl);// ����ÿƪ���µ�ͷͼ��URL
				itBlog.setTilte(titles.get(i).text());// ��ȡa��ǩ�ڵ��ı��������±���
				itBlog.setDate(dates.get(i).text());// ��ȡ���·�������
				itBlog.setUrl(blogUrl);// ��ȡ����������href��ֵ
				itBlogs.add(itBlog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itBlogs;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param url
	 *            ����ģգң�
	 * @return String IT�Ķ���������
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
	 * ��ȡ����ͼ��,�������µ�URL��ַ����img��ǩ��ȡsrc����ֵ
	 * 
	 * @param blogUrl
	 *            ��������£գң�
	 * @return String IT�Ķ�����ͼ��URL
	 * */
	public static String getIconUrlByBlogUrl(String blogUrl) {
		String iconUrl = null;
		try {
			Document doc = Jsoup.connect(blogUrl).get();
			Element contentElement = doc
					.getElementById(Constant.ITBlOG_CONTENT_ID);// ��ȡ������
			Elements imgElements = contentElement.getElementsByTag("img");
			if(imgElements.size()>0)
			   iconUrl = imgElements.get(0).attributes().get("src");// ��ȡUIRL,Ĭ��ȡ��һ��������img��URL
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iconUrl;
	}
}
